package com.sportradar.livedata.sdk.proto.livescout.auth;

import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class AuthMessageProviderTest {

    private final Mockery context = new JUnit5Mockery() {{
        setImposteriser(ByteBuddyClassImposteriser.INSTANCE); // Enable mocking of concrete classes if needed
    }};

    private LiveScoutOutgoingMessageFactory factory;
    private AuthSettings authSettings;
    private OutgoingMessage outgoingMessage;
    private AuthMessageProvider provider;

    @BeforeEach
    void setUp() {
        factory = context.mock(LiveScoutOutgoingMessageFactory.class);
        authSettings = context.mock(AuthSettings.class);
        outgoingMessage = context.mock(OutgoingMessage.class);

        // Test stub subclass
        provider = new AuthMessageProvider(factory, authSettings) {
            @Override
            public OutgoingMessage buildLoginRequest() {
                return null; // Not needed for this test
            }
        };
    }

    @Test
    void testBuildLogOutRequest() {
        context.checking(new Expectations() {{
            oneOf(factory).buildLogOutRequest();
            will(returnValue(outgoingMessage));
        }});

        OutgoingMessage result = provider.buildLogOutRequest();
        assertSame(outgoingMessage, result, "Expected the same OutgoingMessage from factory");
    }
}
