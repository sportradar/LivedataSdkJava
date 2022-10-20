package com.sportradar.sdk.test.proto;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.proto.common.IncrementalMessageTokenizer;
import com.sportradar.sdk.proto.common.MessageTokenizerListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class IncrementalMessageTokenizerTest {

    private final Synchroniser synchroniser = new Synchroniser();
    private final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};
    private States state;
    private Sequence sequence;
    private SdkLogger sdkLogger;
    private final int bufferSize = 1000;
    private IncrementalMessageTokenizer incrementalMessageTokenizerCrLfCrLf;
    private InputStream inputStream;
    private MessageTokenizerListener messageTokenizerListener;

    @Before
    public void setUp() {
        sequence = context.sequence("sequence");
        state = context.states("state");
        sdkLogger = context.mock(SdkLogger.class);
        incrementalMessageTokenizerCrLfCrLf = new IncrementalMessageTokenizer(sdkLogger, bufferSize);
        messageTokenizerListener = context.mock(MessageTokenizerListener.class);
        incrementalMessageTokenizerCrLfCrLf.setListener(messageTokenizerListener);
    }

    @Test
    public void testEmptyMessage_Without_CRLF_CRLF() throws Exception {
        String message = "";
        inputStream = new ByteArrayInputStream(message.getBytes());
        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
    }

    @Test
    public void testEmptyTagValue_Without_CRLF_CRLF() throws Exception {
        String message = "<tag></tag>";
        inputStream = new ByteArrayInputStream(message.getBytes());
        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
    }

    @Test
    public void testEmptyBetradarTagValue_Without_CRLF_CRLF() throws Exception {
        String message = "<BetradarLiveOdds></BetradarLiveOdds>";
        inputStream = new ByteArrayInputStream(message.getBytes());
        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
    }

    @Test
    public void test_Junk_Beetween_Two_Messages() throws Exception {
        final String message = "<BetradarLiveOdds></BetradarLiveOdds>\r\n\r\nTHISISJUNK!09<BetradarLiveOdds></BetradarLiveOdds>\r\n\r\n";
        context.checking(new Expectations() {{
            exactly(2).of(messageTokenizerListener).onMessageReady(with(any(InputStream.class)));
            then(state.is("message_sent"));
            exactly(2).of(sdkLogger).logTraffic(with(false), with("<BetradarLiveOdds></BetradarLiveOdds>"));
            oneOf(sdkLogger).logInvalidMessage(with(Level.WARN), with("Discarded data before '<' character: 'THISISJUNK!09'. Source: " + message));
        }});

        inputStream = new ByteArrayInputStream(message.getBytes());
        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
    }

    @Test
    public void test_Too_Long_Message() throws Exception {
        final String message = new String(new char[bufferSize - 1]).replace('\0', 'X');
        context.checking(new Expectations() {{
            oneOf(sdkLogger).logInvalidMessage(with(Level.ERROR),
                    with(String.format("Discarding message as is too long. Increase buffer size. Received msg: %s",
                            message + message)));
        }});

        inputStream = new ByteArrayInputStream(message.getBytes());
        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
        inputStream.reset();
        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
    }

    @Test
    public void testEmptyBetradarTagValue_With_CRLF_CRLF() throws Exception {
        final String message = "<BetradarLiveOdds></BetradarLiveOdds>\r\n\r\n";
        inputStream = new ByteArrayInputStream(message.getBytes());

        context.checking(new Expectations() {{
            oneOf(messageTokenizerListener).onMessageReady(with(any(InputStream.class)));
            then(state.is("message_sent"));
            inSequence(sequence);
            oneOf(sdkLogger).logTraffic(with(false), with(message.replaceAll("\r\n\r\n", "")));
        }});

        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
        synchroniser.waitUntil(state.is("message_sent"));
        context.assertIsSatisfied();
    }

    @Test
    public void testLoginOK_With_CRLF_CRLF() throws Exception {
        final String message = "<BetradarLiveOdds xmlns=\"http://www.betradar.com/BetradarLiveOdds\" serverversion=\"2012.5\" status=\"loginok\" timestamp=\"1389696329303\"/>\r\n\r\n";
        inputStream = new ByteArrayInputStream(message.getBytes());

        context.checking(new Expectations() {{
            oneOf(messageTokenizerListener).onMessageReady(with(any(InputStream.class)));
            then(state.is("message_sent"));
            inSequence(sequence);
            oneOf(sdkLogger).logTraffic(with(false), with(message.replaceAll("\r\n\r\n", "")));
        }});

        incrementalMessageTokenizerCrLfCrLf.processData(inputStream);
        synchroniser.waitUntil(state.is("message_sent"));
        context.assertIsSatisfied();
    }
}
