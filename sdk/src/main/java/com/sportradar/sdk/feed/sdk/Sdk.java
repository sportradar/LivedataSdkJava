package com.sportradar.sdk.feed.sdk;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sportradar.sdk.common.classes.jmx.SimpleJMX;
import com.sportradar.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.sdk.common.exceptions.MissingPropertyException;
import com.sportradar.sdk.common.exceptions.MissingPropertyFileException;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.interfaces.Version;
import com.sportradar.sdk.common.settings.PropertyFileSettingsLoader;
import com.sportradar.sdk.common.settings.SettingsLoader;
import com.sportradar.sdk.di.GeneralInjectionModule;
import com.sportradar.sdk.di.LiveScoutInjectionModule;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * Represents a top-level access point used by user application to access the sdk functionality.
 */
public class Sdk implements AutoCloseable {

    /**
     * A time-interval in milliseconds specifying the amount of time to wait for a executor to terminate
     */
    private static final long EXECUTOR_TERMINATION_TIMEOUT = 2000;
    /**
     * The {@link Object} used to synchronize access to shared variables.
     */
    private static final Object lock = new Object();
    /**
     * The {@link Logger} implementation used for logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(Sdk.class);
    /**
     * The {@link com.google.inject.Injector} used to build the IoC container;
     */
    private static Injector injector;
    /**
     * The {@link SimpleJMX} implementation used to JMX expose.
     */
    private final SimpleJMX jmxManager;
    /**
     * The {@link LiveScoutFeed} implementation used to manage the live-scout feed.
     */
    private final LiveScoutFeed liveScout;

    private final Thread.UncaughtExceptionHandler oldHandler;

    private final Version sdkVersion;

    /**
     * Initializes a new instance of the {@link Sdk} instance.
     *
     * @param liveScout The {@link LiveScoutFeed} implementation used to manage the live-scout feed.
     */
    @Inject
    Sdk(
            @Nullable LiveScoutFeed liveScout,
            SimpleJMX jmxManager,
            Version sdkVersion
    ) {

        checkNotNull(jmxManager, "jmxManager cannot be null.");
        checkNotNull(sdkVersion, "sdkVersion cannot be null.");

        if (liveScout == null) {
            logger.error("No feeds are enabled");
        }
        this.sdkVersion = sdkVersion;
        this.oldHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(oldHandler == null ?
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("Uncaught exception in SDK logged", e);
                    }
                }
                :
                new
                        Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread t, Throwable e) {
                                logger.error("Uncaught exception in SDK logged", e);
                                oldHandler.uncaughtException(t, e);
                            }
                        }
        );
        this.liveScout = liveScout;
        this.jmxManager = jmxManager;

        this.jmxManager.start();
    }

    /**
     * Closes the {@link Sdk} instance and disposes all resources associated with it.
     */
    public void close() {
        synchronized (lock) {
            if (injector == null) {
                return;
            }
            disposeFeed(liveScout);
            this.jmxManager.stop();


            shutDownExecutorService(injector.getInstance(ScheduledExecutorService.class), "scheduled executor");
            shutDownExecutorService(injector.getInstance(ExecutorService.class), "executor");

            injector = null;
            Thread.setDefaultUncaughtExceptionHandler(oldHandler);
        }
    }

    /**
     * Disposes the passed {@link LiveScoutFeed}. If passed feed is a null reference it does nothing.
     *
     * @param feed The {@link LiveScoutFeed} instance to be disposed, or a null reference.
     */
    private void disposeFeed(LiveScoutFeed feed) {
        if (feed == null) {
            return;
        }
        feed.dispose();
    }

    /**
     * Attempts to gracefully shut-down passed {@link ExecutorService}. If graceful shutdown fails, the
     * service is forcefully shutdown.
     *
     * @param service The {@link ExecutorService} to be shutdown.
     */
    private void shutDownExecutorService(ExecutorService service, String executorName) {
        logger.debug(String.format("Shutting down %s service", executorName));
        service.shutdown();
        try {
            int i = 0;
            while (!service.awaitTermination(EXECUTOR_TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS)) {
                logger.warn(String.format("Allocated time elapsed while waiting for the %s to terminate", executorName));
                if (i >= 3) {
                    logger.warn(String.format("Waiting too long for the %s to terminate. Forcefully terminating the schedule executor", executorName));
                    service.shutdownNow();
                    break;
                }
                i++;
            }
            logger.info(String.format("%s shut down.", executorName));
        } catch (InterruptedException e) {
            logger.warn(String.format("Interruption occurred while waiting for %s to terminate. Forcing termination. Exception: %s", executorName, e));
            service.shutdownNow();
        }
    }

    /**
     * Gets the singleton {@link Sdk} instance.
     *
     * @return the singleton {@link Sdk} instance.
     * @throws MissingPropertyException A required property was not found in the configuration file
     * @throws InvalidPropertyException The value(format) of the property was incorrect.
     * @throws MissingPropertyFileException                                  If property file is not found
     */
    public static Sdk getInstance() throws SdkException {
        synchronized (lock) {
            if (injector == null) {
                initializeInjector(null);
            }
            return injector.getInstance(Sdk.class);
        }
    }

    /**
     * Initializes the {@link Injector} used to build and retrieve the {@link Sdk} instance.
     *
     * @throws MissingPropertyException     A required property was not found in the configuration file
     * @throws InvalidPropertyException     The value(format) of the property was incorrect.
     * @throws MissingPropertyFileException If property file is not found
     */
    private static void initializeInjector(Properties properties) throws SdkException {
        logger.info("Initializing SDK dependency injector");

        final SettingsLoader loader = properties == null ? new PropertyFileSettingsLoader() : new PropertyFileSettingsLoader(properties);

        injector = Guice.createInjector(
                new GeneralInjectionModule(loader.getJmxSettings()),
                new LiveScoutInjectionModule(loader.getLiveScoutSettings())
        );
    }

    /**
     * Gets the {@link LiveScoutFeed} implementation used to manage the live-scout feed.
     *
     * @return the {@link LiveScoutFeed} implementation used to manage the live-scout feed or a null reference if live-scout
     * related functionality is disabled in the configuration file.
     */
    public LiveScoutFeed getLiveScout() {
        return liveScout;
    }

    /**
     * Gets SDK version
     *
     * @return SDK version
     */
    public String getVersion() {
        return sdkVersion.getVersion();
    }

    /**
     * Initialize SDK with passed {@link Properties} instance
     * Call to this method is optional. If you want initialize SDK
     * with properties read from properties file call {@link Sdk#getInstance()} directly
     *
     * @param properties SDK properties
     * @throws SdkException          if there was an error in SDK initialization
     * @throws IllegalStateException if SDK is already initialized
     */
    public static void init(Properties properties) throws SdkException {
        synchronized (lock) {
            if (injector != null) {
                throw new IllegalStateException("SDK is already initialized");
            }
            initializeInjector(properties);
        }
    }
}
