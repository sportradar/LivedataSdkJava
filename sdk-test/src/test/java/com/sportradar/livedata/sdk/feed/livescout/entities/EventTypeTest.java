package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.livescout.enums.EventType;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class EventTypeTest {


    @Test
    void MultipleUnkownEventsTest() {
        EventType unknown1 = EventType.getEventTypeFromLiteralValue("unknown1");
        EventType unknown2 = EventType.getEventTypeFromLiteralValue("unknown2");

        assertSame(unknown1, EventType.UNKNOWN);
        assertSame(unknown2, EventType.UNKNOWN);
    }
}
