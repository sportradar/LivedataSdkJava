package com.sportradar.sdk.common.classes;

import com.sportradar.sdk.common.interfaces.SdkLogger;

import java.io.InputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A factory used to construct {@link LoggingInputStream} instances.
 */
public class LoggingInputStreamFactory {

    /**
     * A {@link SdkLogger} used by the constructed {@link LoggingInputStream} instances.
     */
    private final SdkLogger sdkLogger;

    /**
     * Initializes a new instance of the {@link LoggingInputStreamFactory} class.
     *
     * @param sdkLogger A {@link SdkLogger} used by the constructed {@link LoggingInputStream} instances.
     */
    public LoggingInputStreamFactory(SdkLogger sdkLogger) {
        checkNotNull(sdkLogger, "sdkLogger cannot be a null reference");
        this.sdkLogger = sdkLogger;
    }

    /**
     * Constructs and returns a new {@link LoggingInputStream} instance.
     *
     * @param actualStream The wraped {@link InputStream}
     * @param bufferSize   The buffer size of the constructed {@link LoggingInputStream} instance.
     * @param readOnClose  Value specifying whether the underlying {@link InputStream} should be read to the end when the stream is closed.
     * @return a new {@link LoggingInputStream} instance.
     */
    public LoggingInputStream buildInputStream(InputStream actualStream, int bufferSize, boolean readOnClose) {
        return new LoggingInputStream(actualStream, sdkLogger, bufferSize, readOnClose);
    }
}
