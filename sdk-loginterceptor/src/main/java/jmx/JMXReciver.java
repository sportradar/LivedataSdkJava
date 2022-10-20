package jmx;

import com.j256.simplejmx.client.JmxClient;

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;
import java.util.Set;

public class JMXReciver {

    public JMXReciver() {
    }

    public static void main(String[] args) throws Exception {

        int somePortNumber = 8000;
        JmxClient client = new JmxClient(JmxClient.generalJmxUrlForHostNamePort("localhost", somePortNumber), "monitorRole", "corelite");

        String[] domains = client.getBeanDomains();
        for (String domain : domains) {
            if (domain.toLowerCase().contains("sportradar")) {
                Set<ObjectName> beans = client.getBeanNames(domain);
                for (ObjectName bean : beans) {
                    MBeanAttributeInfo[] mBeanAttributeInfo = client.getAttributesInfo(bean);
                    for (MBeanAttributeInfo aMBeanAttributeInfo : mBeanAttributeInfo) {
                        Object attribute = client.getAttribute(bean, aMBeanAttributeInfo.getName());
                        System.out.println(bean.getCanonicalName() + "." + aMBeanAttributeInfo.getName() + "=" + attribute);
                    }
                }
            }
        }
        client.close();
    }
}
