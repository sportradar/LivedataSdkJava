/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.proto.common;

import com.sportradar.livedata.sdk.common.exceptions.SdkException;

import java.io.InputStream;

/**
 * A listener used to observe {@code MessageTokenizer} instance.
 */
public interface MessageTokenizerListener {

    /**
     * Invoked when the observed {@link MessageTokenizer} parsed data which represented a whole message.
     *
     * @param stream A {@link InputStream} implementation containing tokenized message data.
     * @throws SdkException thrown when an un-expected situation is encountered.
     */
    void onMessageReady(InputStream stream) throws SdkException;
}
