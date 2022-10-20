package com.sportradar.sdk.di;

import com.google.inject.Provider;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import io.github.classgraph.*;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Provider} implementation used to construct {@link JAXBContext} instances
 */
public class JaxbContextProvider implements Provider<JAXBContext> {

    /**
     * The name of the root incoming package - package containing all "incoming" messages
     */
    private static final String INCOMING_PACKAGE = "com.sportradar.sdk.proto.dto.incoming";

    /**
     * The name of the root outgoing package - package containing all "outgoing" messages
     */
    private static final String OUTGOING_PACKAGE = "com.sportradar.sdk.proto.dto.outgoing";

    /**
     * Constructs and return a new instance of the {@link JAXBContext} class.
     *
     * @return a new instance of the {@link JAXBContext} class.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JAXBContext get() {
        List<ClassInfo> all;
        try (ScanResult scanResult = new ClassGraph().acceptPackages(INCOMING_PACKAGE).scan()) {
            all = new ArrayList(scanResult.getSubclasses(IncomingMessage.class));
        }
        try (ScanResult scanResult =new ClassGraph().acceptPackages(OUTGOING_PACKAGE).scan()) {
            all.addAll(scanResult.getSubclasses(OutgoingMessage.class));
        }

       List<Class> classArray = new ArrayList<>();
        for(ClassInfo classInfo : all){
            classArray.add(classInfo.getClass());
        }
        try {
            return JAXBContext.newInstance(classArray.toArray(new Class[classArray.size()]));
        } catch (JAXBException e) {
        }
        return null;
    }
}
