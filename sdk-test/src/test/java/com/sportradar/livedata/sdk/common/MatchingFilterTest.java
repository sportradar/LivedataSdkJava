/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common;

import ch.qos.logback.core.spi.FilterReply;
import com.sportradar.livedata.sdk.common.classes.SdkLoggerCfg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatchingFilterTest {

    private SdkLoggerCfg.MatchingFilter matchingFilter;

    @BeforeEach
    public void setUp() {
        matchingFilter = new SdkLoggerCfg.MatchingFilter();
    }

    @Test
    void testNeutralFilterReply() {
        Marker marker = null;
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.NEUTRAL));
    }

    @Test
    void testNonWhitelistWithDenyFilterReply() {
        String markerString = "test deny";
        matchingFilter.setMarker(markerString);
        Marker marker = MarkerFactory.getMarker(markerString);
        matchingFilter.start();
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.DENY));
    }

    @Test
    void testNonWhitelistWithAcceptFilterReply() {
        String markerString = "acceptMarker";
        matchingFilter.setMarker(markerString);
        matchingFilter.start();
        markerString = "test";
        matchingFilter.setMarker(markerString);
        Marker marker = MarkerFactory.getMarker(markerString);
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.ACCEPT));
    }

    @Test
    void testWhitelistWithAcceptFilterReply() {
        String markerString = "denyMarker";
        matchingFilter.setMarker(markerString);
        matchingFilter.start();
        matchingFilter.setIsWhitelist(true);
        Marker marker = MarkerFactory.getMarker(markerString);
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.ACCEPT));
    }

    @Test
    void testWhitelistWithDenyFilterReply() {
        String markerString = "acceptMarker";
        matchingFilter.setMarker(markerString);
        matchingFilter.start();
        matchingFilter.setIsWhitelist(true);
        markerString = "accept";
        matchingFilter.setMarker(markerString);
        Marker marker = MarkerFactory.getMarker(markerString);
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.DENY));
    }
}
