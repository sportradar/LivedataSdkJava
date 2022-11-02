package com.sportradar.livedata.sdk.test.system.framework.common;

import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;

public interface SdkDataListener {

    public void sdkEntitySend(OutgoingMessage entity);
}
