package com.sportradar.sdk.test.feed.feed;

import com.sportradar.sdk.feed.common.entities.TypeValueTuple;

public class MockedTypeValueTuple extends TypeValueTuple {

    public MockedTypeValueTuple(String type, String value) {
        super(type, value);
    }

    public String getType() {
        return super.getType();
    }
}
