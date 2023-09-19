package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.livescout.enums.EventType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventTypeTest {


    @Test
    public void MultipleUnkownEventsTest() {
        EventType unknown1 = EventType.getEventTypeFromLiteralValue("unknown1");
        EventType unknown2 = EventType.getEventTypeFromLiteralValue("unknown2");


        assertTrue(unknown1 == EventType.UNKNOWN);
        assertTrue(unknown2 == EventType.UNKNOWN);
    }
}
