package com.sportradar.livedata.sdk.system.framework.livescout;

import com.google.inject.Provides;
import com.sportradar.livedata.sdk.common.networking.Gateway;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.di.LiveScoutInjectionModule;

import javax.net.ssl.SSLSocketFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;


public class LiveScoutInjectionModuleMock extends LiveScoutInjectionModule {

    Gateway mockedGateway;

    /**
     * Initializes a new instance of the {@link com.sportradar.livedata.sdk.di.LiveScoutInjectionModule} class.
     *
     * @param settings       The {@link LiveScoutSettings} implementation containing live-scout configurable values.
     * @throws IllegalArgumentException The {@code settings} is a null reference.<
     */
    public LiveScoutInjectionModuleMock(
            LiveScoutSettings settings,
            Gateway mockedGateway) {
        super(settings);
        this.mockedGateway = mockedGateway;
    }

    @Provides
    protected Gateway provideGateway(
            ExecutorService executorService,
            ScheduledExecutorService scheduledExecutorService,
            SSLSocketFactory sslSocketFactory) {

        return mockedGateway;
    }
}
