package com.sportradar.livedata.sdk.di;

import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Contains unit tests for the
 */
public class JaxbContextProviderTest {

    private final JaxbContextProvider provider = new JaxbContextProvider();

    @Test
    public void jaxbContextIsConstructed() {
        JAXBContext context = provider.get();
        assertThat(context, notNullValue());
    }
}
