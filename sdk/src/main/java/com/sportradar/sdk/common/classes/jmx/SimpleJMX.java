package com.sportradar.sdk.common.classes.jmx;

import com.j256.simplejmx.server.JmxServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleJMX {

    private boolean enabled;
    private String jmxHost;
    private int jmxPort;
    private JMXConnectorServer connector = null;
    private ArrayList list;
    private JmxServer jmxServer;
    private String passwordFile;
    private String accessFile;

    private boolean started = false;

    /**
     * The {@link Logger} implementation used for logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(SimpleJMX.class);

    /**
     * @param enabled enables jmx
     * @param jmxHost Host of jmx eg. "localhost"
     * @param jmxPort Port of jmx
     * @param passwordFile password file
     * @param accessFile access file
     */
    public SimpleJMX(boolean enabled, String jmxHost, int jmxPort, String passwordFile, String accessFile) {
        this.enabled = enabled;
        this.list = new ArrayList();
        this.jmxHost = jmxHost;
        this.jmxPort = jmxPort;
        this.passwordFile = passwordFile;
        this.accessFile = accessFile;
    }

    /**
     * Start MBean server and register objects
     */
    public void start() {
        if (!enabled || started) return;

        int registryPort = jmxPort;
        int serverPort = registryPort + 1;

        String urlString = "service:jmx:rmi://" + this.jmxHost + ":" + serverPort + "/jndi/rmi://:" + registryPort + "/jmxrmi";
        try {
            LocateRegistry.createRegistry(registryPort);
            //createRegistry can be called just once
            started = true;
            JMXServiceURL url = new JMXServiceURL(urlString);

            Map<String, String> env = new HashMap<>();
            env.put("jmx.remote.x.password.file", passwordFile);
            env.put("jmx.remote.x.access.file", accessFile);
            connector = JMXConnectorServerFactory.newJMXConnectorServer(url, env, ManagementFactory.getPlatformMBeanServer());
            connector.start();
            MBeanServer mbeanServer = connector.getMBeanServer();

            jmxServer = new JmxServer(mbeanServer);

            for (Object object : list) {
                try {
                    jmxServer.register(object);
                } catch (JMException e) {
                    logger.error("JMX cannot register object:" + object.getClass().getName());
                }
            }

        } catch (IOException e) {
            logger.error("JMX cannot be started (port " + registryPort + " already in use)");
        }
    }

    /**
     * Add object to be added to JMX port at start
     * Note that you add just one instance of specific class
     *
     * @param object Simple JMX MBean object
     */
    public void add(Object object) {
        this.list.add(object);
        //If JMX server already started then register here
        if (jmxServer != null) {
            try {
                jmxServer.register(object);
            } catch (JMException e) {
                logger.error("JMX cannot register object:" + object.getClass().getName());
            }
        }
    }

    /**
     * Stop JMX port if enabled and unregister objects
     */
    public void stop() {
        // stop our server
        try {
            if (jmxServer != null) {
                // unregister is not necessary if we are stopping the server
                for (Object counter : list) {
                    jmxServer.unregister(counter);
                }
            }
            if (connector != null) {
                connector.stop();
            }
        } catch (IOException e) {
            logger.error("JMX stop error");
        }
        started = false;
    }
}
