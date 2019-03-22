package fun.oop.framework.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class XmlUtil {


    @SuppressWarnings("unchecked")
    public static <T> T xml2Object(Class<T> clazz, String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller un = context.createUnmarshaller();
            return (T) un.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String object2Xml(T xmlObject) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jc = JAXBContext.newInstance(xmlObject.getClass());
            Marshaller m = jc.createMarshaller();
            m.marshal(xmlObject, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    public static <T> String object2Xml(T xmlObject, Map<String, Object> properties) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jc = JAXBContext.newInstance(xmlObject.getClass());
            Marshaller m = jc.createMarshaller();
            m.marshal(xmlObject, sw);
            for (String s : properties.keySet()) {
                m.setProperty(s, properties.get(s));
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }


}
