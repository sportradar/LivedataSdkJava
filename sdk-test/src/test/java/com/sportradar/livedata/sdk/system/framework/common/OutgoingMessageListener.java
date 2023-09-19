package com.sportradar.livedata.sdk.system.framework.common;

import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;

public interface OutgoingMessageListener {

    public <T extends OutgoingMessage> void messageSendToServer(T msg) throws Exception;
}
