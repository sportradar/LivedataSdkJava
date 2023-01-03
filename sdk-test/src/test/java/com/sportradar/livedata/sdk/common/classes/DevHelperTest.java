package com.sportradar.livedata.sdk.common.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class DevHelperTest {

    @DisplayName("ofType() test")
    @Test
    void ofType_test() {
        List<Child> list = List.of(new Child(), new Child(), new Child());

        List<Child> result = DevHelper.ofType(list, Child.class);

        result.forEach(Assertions::assertNotNull);
    }
}

class Parent {}
class Child extends Parent {}