import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Appender;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.feed.sdk.Sdk;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LogInterceptor {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(LogInterceptor.class);

    public static void main(String[] args) throws SdkException {

        final Sdk sdk = Sdk.getInstance();
        final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();

        final LiveScoutFeedListener scoutFeedListener = new LiveScoutListenerImpl();
        if (liveScoutFeed != null) {
            liveScoutFeed.open(scoutFeedListener);
        }


        List<Logger> alertLoggers = new ArrayList<>();
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        for (ch.qos.logback.classic.Logger log : lc.getLoggerList()) {
            if (log.getName().toLowerCase().contains("traffic")) {
                System.out.println(log.getName());
//                alertLoggers.add((Logger) LoggerFactory.getLogger(log.getName()));
            }
        }

        for (Logger alertLogger : alertLoggers) {
            alertLogger.addAppender(createSmtpAppender(alertLogger));
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        logger.info("The sdk is running. Hit any key to exit");
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Closing the sdk");
        sdk.close();
        logger.info("Sdk successfully closed. Main thread will now exit");
    }

    private static Appender createSmtpAppender(Logger alertLogger) {
        Properties properties = readPropertiesFromFile("sdk-loginterceptor.properties");

        TimeSmtpAppender appender = new TimeSmtpAppender();

        appender.setName(alertLogger.getName() + " mail");
        appender.setCharsetEncoding("UTF-8");

        appender.setDelay(Integer.parseInt(properties.getProperty("sdk.loginterceptor.interval", "60000")));

        appender.setSmtpHost(properties.getProperty("sdk.loginterceptor.smtpHost", "smtp.gmail.com"));
        appender.setSmtpPort(Integer.parseInt(properties.getProperty("sdk.loginterceptor.smtpPort", "587")));
        appender.setUsername(properties.getProperty("sdk.loginterceptor.username", "godfather@klika.si"));
        appender.setPassword(properties.getProperty("sdk.loginterceptor.password", "c0r3l1t3"));

        appender.setFrom(properties.getProperty("sdk.loginterceptor.from", "nobody@klika.si"));
        appender.addTo(properties.getProperty("sdk.loginterceptor.to", "sdk-alerts@klika.si"), "SDK Java");
        appender.setSubject(properties.getProperty("sdk.loginterceptor.subject", "[SDK Java] Log report"));

        appender.setContext(alertLogger.getLoggerContext());

        appender.start();

        return appender;
    }

    private static Properties readPropertiesFromFile(String filePath) {
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(LogInterceptor.class.getClassLoader().getResourceAsStream(filePath));

        } catch (Exception ex) {
            System.err.println("Error reading properties file of LogInterceptor. Loading defaults...");
        }
        return prop;
    }

}
