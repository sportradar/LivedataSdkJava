package com.sportradar.sdk.test.feed.dispatch;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sportradar.sdk.common.classes.FileSdkLogger;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LoggerSettings;
import com.sportradar.sdk.common.timer.PeriodicTimer;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.test.common.FileSdkLoggerTestBase;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class LoggingEntityEventHandlerBase {

    private DummyLBAppender listAppender;
    private LoggerContext loggerContext;
    private Pattern elapsedPattern = Pattern.compile("\\[(Elapsed )(?<elapsed>\\d+)\\]");
    private final float ELAPSED_MAX = 1.234f;
    protected Logger logger;
    private LoggerSettings loggerSettings;
    protected SdkLogger sdkLogger;
    private Timer timer;


    public void setUp() throws Exception {
        loggerSettings = DefaultSettingsBuilderHelper.getLiveScout().getLoggerSettingsBuilder().build();
        timer = new PeriodicTimer(Executors.newScheduledThreadPool(1));
        sdkLogger = new FileSdkLogger(loggerSettings, timer);

        logger = FileSdkLoggerTestBase.getLogger(sdkLogger, FileSdkLogger.ROOT_NS + ".logger." + SdkLogAppenderType.CLIENT_INTERACTION.getFileName());
        loggerContext = logger.getLoggerContext();

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
