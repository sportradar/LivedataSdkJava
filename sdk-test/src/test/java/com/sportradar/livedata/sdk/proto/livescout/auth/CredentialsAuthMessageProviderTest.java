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

class CredentialsAuthMessageProviderTest {

    private final Mockery context = new JUnit5Mockery() {{
        setImposteriser(ByteBuddyClassImposteriser.INSTANCE); // 👈 Allow mocking concrete classes
    }};

    private LiveScoutOutgoingMessageFactory factory;
    private AuthSettings authSettings;
    private OutgoingMessage outgoingMessage;
    private CredentialsAuthMessageProvider provider;

    @BeforeEach
    void setUp() {
        factory = context.mock(LiveScoutOutgoingMessageFactory.class);
        authSettings = context.mock(AuthSettings.class);
        outgoingMessage = context.mock(OutgoingMessage.class);

        provider = new CredentialsAuthMessageProvider(factory, authSettings);
    }

    @Test
    void testBuildLoginRequest() {
        context.checking(new Expectations() {{ // there should not be missing creds, it has to be checked by settings loader
            oneOf(authSettings).getUsername(); will(returnValue("testUser"));
            oneOf(authSettings).getPassword(); will(returnValue("testPass"));
            oneOf(factory).buildLoginRequest("testUser", "testPass", null); will(returnValue(outgoingMessage));
        }});

        OutgoingMessage result = provider.buildLoginRequest();
        assertSame(outgoingMessage, result);
    }
}