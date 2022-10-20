package com.sportradar.sdk.test.system.framework.common;

import com.sportradar.sdk.proto.dto.OutgoingMessage;

public interface SdkDataListener {

    public void sdkEntitySend(OutgoingMessage entity);
}
