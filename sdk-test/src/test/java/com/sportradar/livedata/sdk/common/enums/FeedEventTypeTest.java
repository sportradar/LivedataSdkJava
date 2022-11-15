package com.sportradar.livedata.sdk.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedEventTypeTest {

    @DisplayName("All enum values are not null test")
    @Test
    void enumValuesIsNotNull_test() {
        List<String> enums = List.of("CONNECTED", "AUTHENTICATED", "DISCONNECTED", "PARSE_ERROR", "DISPATCHER_FULL");
        enums.forEach((e) -> assertDoesNotThrow(() -> FeedEventType.valueOf(e)));
    }
}