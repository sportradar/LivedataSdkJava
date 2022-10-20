/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;


import org.xml.sax.SAXException;

import javax.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * Represents a factory used to create jaxb based {@link Marshaller} and {@link Unmarshaller} instances.
 */
public class JaxbFactory implements JaxbBuilder {

    public static final String ENCODING = "UTF-8";

    /**
     * The {@link JAXBContext} used to create marshaller and un-marshaller instances
     */
    private final JAXBContext context;

    /**
     * The {@link SchemaFactory} used to create {@link Schema} which is used to validate the data before un-marshalling it
     */
    private final SchemaFactory schemaFactory;

    /**
     * The {@link Source} containing the schema definition.
     */
    private final Source schemaSource;

    /**
     * The {@link Schema} used for validation.
     */
    private Schema schema;

    /**
     * Initializes a new instance of the {@link JaxbFactory} class.
     *
     * @param context The {@link JAXBContext} used to create marshallers and un-marshallers.
     * @throws IllegalArgumentException The {@code context} is a null reference.
     */
    @Inject
    public JaxbFactory(JAXBContext context) {
        checkNotNull(context, "The context cannot be a null reference");

        this.context = context;
        this.schemaFactory = null;
        this.schemaSource = null;
    }

    /**
     * Initializes a new instance of the {@link JaxbFactory} class.
     *
     * @param context       The {@link JAXBContext} used to create marshallers and un-marshallers.
     * @param schemaFactory The {@link SchemaFactory} used to create {@link Schema} which is used to validate the data before un-marshalling it or a null reference in no validation is required.
     * @param schemaSource  The {@link Source} containing schema definition. This argument is only required when the {@code schemaFactory} is not a null reference. Otherwise this argument is ignored.
     * @throws IllegalArgumentException The {@code context} is a null reference or {@code schemaFactory} is a null reference or {@code schemaSource} is a null reference.
     */
    public JaxbFactory(JAXBContext context, SchemaFactory schemaFactory, Source schemaSource) {
        checkNotNull(context, "The context cannot be a null reference");
        checkNotNull(schemaFactory, "The schemaFactory cannot be a null reference");
        checkNotNull(schemaSource, "The schemaSource cannot be a null reference");

        this.context = context;
        this.schemaFactory = schemaFactory;
        this.schemaSource = schemaSource;
    }

    /**
     * Creates and returns a {@link Marshaller} implementation. Note that each time this method is called new instance of the marshaller is created.
     *
     * @return The created {@link Marshaller} instance.
     * @throws JAXBException
     */
    @Override
    public Marshaller buildMarshaller() throws ProtocolException {
        try {
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
            marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
            return marshaller;
        } catch (JAXBException e) {
            throw new ProtocolException("The marshaller could not be build", e);
        }
    }

    /**
     * Creates and returns a {@link Unmarshaller} implementation. Note that each time this method is called new instance of the un-marshaller is created.
     *
     * @return The created {@link Unmarshaller} instance.
     * @throws JAXBException
     */
    @Override
    public Unmarshaller buildUnmarshaller() throws ProtocolException {
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new ProtocolException("The unmarshaller could not be build", e);
        }
        unmarshaller.setSchema(getSchema());
        return unmarshaller;
    }

    /**
     * Gets the configured character encoding for the builder.
     *
     * @return - a string, i.e. UTF-8
     */
    @Override
    public String getEncoding() {
        return ENCODING;
    }

    /**
     * Creates and returns the {@link Schema} used for validation. The instance is created only once and every next call returns
     * the schema constructed at first call.
     *
     * @return The {@link Schema} used for validation or a null reference.
     */
    private Schema getSchema() throws ProtocolException {
        if (schema == null && schemaFactory != null) {
            try {
                schema = schemaFactory.newSchema(schemaSource);
            } catch (SAXException e) {
                throw new ProtocolException("The schema used for message validation could not be build", e);
            }
        }
        return schema;
    }
}
