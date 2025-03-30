package com.sportradar.livedata.sdk.system.framework.common;


import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.proto.common.*;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.MessageBase;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

public class SystemTestMessageParser {

    MessageWriter<IncomingMessage> writer;
    JaxbMessageParser parser;

    @SuppressWarnings("unchecked")
    public SystemTestMessageParser(
            final OutgoingMessageListener outgoingMessageListener,
            final String incomingPackageName,
            final String outgoingPackageName) throws JAXBException {
        checkNotNull(outgoingMessageListener);

        JAXBContext jaxbContextIn = JAXBContext.newInstance(incomingPackageName);
        JaxbBuilder jaxbBuilderIn = new JaxbFactory(jaxbContextIn);

        JAXBContext jaxbContextOut = JAXBContext.newInstance(outgoingPackageName);
        JaxbBuilder jaxbBuilderOut = new JaxbFactory(jaxbContextOut);

        writer = new JaxbMessageWriter<>(jaxbBuilderIn);
        parser = new JaxbMessageParser<>(jaxbBuilderOut, null);
        parser.setListener(new MessageParserListener() {
            @Override
            public void onMessageReady(MessageBase message) {
                try {
                    outgoingMessageListener.messageSendToServer((OutgoingMessage) message);
                } catch (Exception e) {
                    //TODO: FAIL
                }
            }
        });
    }


    public void parseData(byte[] data) throws SdkException {
        parser.processData(new ByteArrayInputStream(data));
    }

    public byte[] toByteArray(IncomingMessage entity) throws MessageException, ProtocolException {
        return writer.write(entity);
    }
}
