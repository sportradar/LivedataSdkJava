/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.proto.dto.MessageBase;
import org.apache.commons.io.IOUtils;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link MessageParser} implementation which uses JAXB to un-marshal the messages
 */
public class JaxbMessageParser<T extends MessageBase> extends MessageParserBase<T> {

    /**
     * The builder used to build {@link Unmarshaller} instances.
     */
    private final JaxbBuilder builder;


    /**
     * Sdk logger for logging various events
     */
    private final SdkLogger sdkLogger;

    /**
     * Initializes a new instance of the {@link JaxbMessageParser} class.
     *
     * @param builder   The {@link JaxbBuilder} implementation used to construct the {@link Unmarshaller} instances.
     * @param tokenizer The {@link MessageTokenizer} implementation used to tokenize received data, or a null reference.
     * @throws IllegalArgumentException the {@code builder} is a null reference or {@code bufferSize} is smaller than or equal to 0.
     */
    public JaxbMessageParser(JaxbBuilder builder, MessageTokenizer tokenizer, SdkLogger sdkLogger) {
        super(tokenizer);

        checkNotNull(builder, "The builder cannot be a null reference");
        checkNotNull(sdkLogger);
        this.builder = builder;
        this.sdkLogger = sdkLogger;
    }

    /**
     * When overridden in derived class it un-marshals the {@code data}.
     *
     * @param stream A {@link InputStream} instance containing message data.
     * @throws ProtocolException the exception was thrown while un-marshaling the {@code data}.
     */
    @SuppressWarnings("unchecked")
    protected void unMarshallData(InputStream stream) throws SdkException {

        T message;
        try {
            long before = System.currentTimeMillis();
            Unmarshaller unmarshaller = builder.buildUnmarshaller();
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            XMLStreamReader xsr = xif.createXMLStreamReader(stream, getEncoding());

            message = (T) unmarshaller.unmarshal(xsr);
            long duration = System.currentTimeMillis() - before;

            if (duration > 1000) {
                sdkLogger.logAlert(Level.WARN, String.format("Parsing message took %s ms (msg %s)", duration, message));
            }
        } catch (JAXBException e) {
            if (stream instanceof ByteArrayInputStream) {
                try {
                    stream.reset();
                    String invalidMsg = new String(IOUtils.toByteArray(stream));
                    sdkLogger.logInvalidMessage(Level.ERROR, "Parse failed : " + invalidMsg);
                } catch (IOException ioe) {
                    sdkLogger.logAlert(Level.WARN, "Stream rewinding failed", ioe);
                }
            } else {
                sdkLogger.logAlert(Level.WARN, "Unknown stream so logging of invalid message is disabled");
            }
            throw new ProtocolException("The message could not be de-serialized", e);
        } catch (ClassCastException e) {
            throw new ProtocolException("Unsupported type");
        } catch (XMLStreamException e) {
            throw new ProtocolException("XML stream exception", e);
        }

        super.notifyMessageReady(message);
    }

    /**
     * Processes the received data.
     *
     * @param stream A {@link InputStream} implementation containing the received data.
     */
    @Override
    protected void onDataReady(InputStream stream) throws SdkException {
        checkNotNull(stream, "stream cannot be a null reference");
        unMarshallData(stream);
    }

    /**
     * Gets the configured character encoding for the parser.
     *
     * @return - a string, i.e. UTF-8
     */
    @Override
    public String getEncoding() {
        return builder.getEncoding();
    }
}
