package com.sportradar.livedata.sdk.common.classes;

import com.sportradar.livedata.sdk.common.interfaces.EntityEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityEnumHelperTest {

    @DisplayName("getValueFromLiteralValue() test -> if parameters are ok")
    @Test
    void getValueFromLiteralValue_ok_test() {
        TestEnumValid result = EntityEnumHelper.getValueFromLiteralValue(TestEnumValid.values(), "THREE");

        assertThat(result).isEqualTo(TestEnumValid.THREE);
    }

    @DisplayName("getValueFromLiteralValue() test -> if literal is null or empty")
    @Test
    void getValueFromLiteralValue_literalIsNullOrEmpty_test() {
        assertNull(EntityEnumHelper.getValueFromLiteralValue(TestEnumValid.values(), null));
        assertNull(EntityEnumHelper.getValueFromLiteralValue(TestEnumValid.values(), ""));
    }

    @DisplayName("getValueFromLiteralValue() test -> if literal does not match any enum entry")
    @Test
    void getValueFromLiteralValue_literalNotMatchWithAny_test() {
        assertNull(EntityEnumHelper.getValueFromLiteralValue(TestEnumInvalid.values(), "THREE"));
    }

    enum TestEnumValid implements EntityEnum {
        ONE, TWO, THREE, FOUR, FIVE;

        @Override
        public boolean isLiteralValueEqual(String value) {
            return this.equals(TestEnumValid.THREE);
        }
    }

    enum TestEnumInvalid implements EntityEnum {
        ONE, TWO, THREE, FOUR, FIVE;

        @Override
        public boolean isLiteralValueEqual(String value) {
            return false;
        }
    }
}