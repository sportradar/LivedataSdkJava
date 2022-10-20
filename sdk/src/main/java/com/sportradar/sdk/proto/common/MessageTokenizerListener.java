/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import com.sportradar.sdk.common.exceptions.SdkException;

import java.io.InputStream;

/**
 * A listener used to observe {@code MessageTokenizer} instance.
 */
public interface MessageTokenizerListener {

    /**
     * Invoked when the observed {@link MessageTokenizer} parsed data which represented a whole message.
     *
     * @param stream A {@link InputStream} implementation containing tokenized message data.
     */
    void onMessageReady(InputStream stream) throws SdkException;
}
