/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import ch.qos.logback.core.spi.FilterReply;
import com.sportradar.sdk.common.classes.SdkLoggerCfg;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class MatchingFilterTest {

    private SdkLoggerCfg.MatchingFilter matchingFilter;

    @Before
    public void setUp() {
        matchingFilter = new SdkLoggerCfg.MatchingFilter();
    }

    @Test
    public void testNeutralFilterReply() {
        Marker marker = null;
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.NEUTRAL));
    }

    @Test
    public void testNonWhitelistWithDenyFilterReply() {
        String markerString = "test deny";
        matchingFilter.setMarker(markerString);
        Marker marker = MarkerFactory.getMarker(markerString);
        matchingFilter.start();
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.DENY));
    }

    @Test
    public void testNonWhitelistWithAcceptFilterReply() {
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
    public void testWhitelistWithAcceptFilterReply() {
        String markerString = "denyMarker";
        matchingFilter.setMarker(markerString);
        matchingFilter.start();
        matchingFilter.setIsWhitelist(true);
        Marker marker = MarkerFactory.getMarker(markerString);
        FilterReply filterReply = matchingFilter.decide(marker, null, null, null, null, null);
        assertThat(filterReply, equalTo(FilterReply.ACCEPT));
    }

    @Test
    public void testWhitelistWithDenyFilterReply() {
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
