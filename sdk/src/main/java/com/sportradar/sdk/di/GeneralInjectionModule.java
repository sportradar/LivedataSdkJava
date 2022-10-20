package com.sportradar.sdk.di;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.sportradar.sdk.common.classes.DaemonThreadFactory;
import com.sportradar.sdk.common.classes.SdkVersion;
import com.sportradar.sdk.common.classes.jmx.SimpleJMX;
import com.sportradar.sdk.common.interfaces.Version;
import com.sportradar.sdk.common.settings.JmxSettings;
import com.sportradar.sdk.common.thread.SRScheduledThreadPoolExecutor;
import com.sportradar.sdk.common.thread.SRThreadPoolExecutor;
import com.sportradar.sdk.feed.sdk.Sdk;
import org.apache.commons.net.DefaultSocketFactory;

import javax.inject.Singleton;
import javax.net.ssl.SSLSocketFactory;
import java.util.concurrent.*;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link Module} implementation used by Guice to set-up general (used by many classes in the application) dependencies.
 */
public class GeneralInjectionModule implements Module {

    private static final int NUM_SCHEDULED_EXECUTOR_THREADS = 5;

    private JmxSettings jmxSettings;

    public GeneralInjectionModule(JmxSettings jmxSettings) {
        checkNotNull(jmxSettings);
        this.jmxSettings = jmxSettings;
    }

    /**
     * Contributes bindings and other configurations for this module to {@code binder}.
     * <p/>
     * <p><strong>Do not invoke this method directly</strong> to install submodules. Instead use
     * {@link com.google.inject.Binder#install(com.google.inject.Module)}, which ensures that {@link com.google.inject.Provides provider methods} are
     * discovered.
     */
    @Override
    public void configure(Binder binder) {
        // Threads have daemon set to true, so the app dies also if they are still running
        binder.bind(ThreadFactory.class)
                .toInstance(new DaemonThreadFactory("livedata-sdk"));

        binder.bind(DefaultSocketFactory.class)
                .in(Singleton.class);

        binder.bind(SSLSocketFactory.class)
                .toInstance((SSLSocketFactory) SSLSocketFactory.getDefault());

        //SDK have to be singleton that just once call Inject constructor that also start JMX
        binder.bind(Sdk.class)
                .in(Singleton.class);

    }


    @Provides
    @Singleton
    protected Version provideVersion() {
        return new SdkVersion();
    }

    @Provides
    @Singleton
    protected SimpleJMX provideSimpleJMX() {
        //construct and return SimpleJmx instance

        return new SimpleJMX(
                jmxSettings.isEnabled(),
                jmxSettings.getJmxHost(),
                jmxSettings.getJmxPort(),
                jmxSettings.getPasswordFile(),
                jmxSettings.getAccessFile());
    }

    @Provides
    @Singleton
    protected ExecutorService provideExecutorService(ThreadFactory factory) {
        // Same result as Executors.newCachedThreadPool() except that our derived ThreadPoolExecutor is used
        return new SRThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), factory);
    }

    @Provides
    @Singleton
    protected ScheduledExecutorService provideScheduledExecutorService(ThreadFactory factory) {
        // Same result as Executors.newScheduledThreadPool() except that our our derived class is used (for logging purposes)
        return new SRScheduledThreadPoolExecutor(NUM_SCHEDULED_EXECUTOR_THREADS, factory);
    }

}
