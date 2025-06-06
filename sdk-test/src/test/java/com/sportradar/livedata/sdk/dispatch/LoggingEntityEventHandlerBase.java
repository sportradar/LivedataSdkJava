package com.sportradar.livedata.sdk.dispatch;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.livedata.sdk.common.classes.FileSdkLogger;
import com.sportradar.livedata.sdk.common.classes.SdkLoggerProvider;
import com.sportradar.livedata.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.common.settings.LoggerSettings;
import com.sportradar.livedata.sdk.common.timer.PeriodicTimer;
import com.sportradar.livedata.sdk.common.timer.Timer;
import com.sportradar.livedata.sdk.common.FileSdkLoggerBaseTest;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoggingEntityEventHandlerBase {

    private DummyLBAppender listAppender;
    private final Pattern elapsedPattern = Pattern.compile("\\[(Elapsed )(?<elapsed>\\d+)]");
    protected Logger logger;
    protected FileSdkLogger sdkLogger;


    public void setUp() throws Exception {

        LoggerSettings loggerSettings = LoggerSettings.builder().build();
        Timer timer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        sdkLogger = new FileSdkLogger(loggerSettings, timer);

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SdkLogger.class).toInstance(sdkLogger);
            }
        });
        injector.getInstance(SdkLoggerProvider.class);

        logger = FileSdkLoggerBaseTest.getLogger(sdkLogger, FileSdkLogger.ROOT_NS + ".logger." + SdkLogAppenderType.CLIENT_INTERACTION.getFileName());
        LoggerContext loggerContext = logger.getLoggerContext();

        listAppender = new DummyLBAppender();
        listAppender.setContext(loggerContext);
        logger.addAppender(listAppender);
        listAppender.start();
    }

    protected void assertEqualMessage(Level level, String message) {
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.size(), equalTo(1));
        ILoggingEvent event = list.get(0);
        String eventMessage = event.getFormattedMessage();
        Matcher matcher = elapsedPattern.matcher(message);
        if (matcher.find()) {
            String elapsedString = matcher.group("elapsed");
            float elapsed = Float.parseFloat(elapsedString);
            int startPos = matcher.start(2);
            int endPos = matcher.end(2);

            float ELAPSED_MAX = 1.234f;
            if (elapsed < ELAPSED_MAX) {
                String replacedMessage = eventMessage.substring(0, startPos)
                        + "0" + eventMessage.substring(endPos);
                assertThat(message, equalTo(replacedMessage));
            } else {
                assertThat(elapsed, lessThan(ELAPSED_MAX));
            }
        }
        assertThat(event.getLevel(), equalTo(level));
    }
}
