package com.sportradar.sdk.test.feed.feed.di;

import com.sportradar.sdk.di.JaxbContextProvider;
import org.junit.Test;

import jakarta.xml.bind.JAXBContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

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
