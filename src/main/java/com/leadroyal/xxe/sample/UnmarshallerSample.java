package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import com.leadroyal.xxe.model.Person;
import org.xml.sax.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

public class UnmarshallerSample {
    public void safe() throws JAXBException {
        // 默认禁用dtd，可以挡回显xxe和blind-xxe
        Class tClass = Person.class;
        JAXBContext context = JAXBContext.newInstance(tClass);
        Unmarshaller um = context.createUnmarshaller();
        Object o = um.unmarshal(ResourceUtils.getPoc2());
        tClass.cast(o);
    }

    public void safe2() throws SAXException, ParserConfigurationException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true); // 开启可以挡回显xxe和blind-xxe
        sax.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // 开启可以挡回显xxe和blind-xxe
        sax.setFeature("http://xml.org/sax/features/external-general-entities", false); // 未测试，因为没有回显成功
        sax.setFeature("http://xml.org/sax/features/external-parameter-entities", false); // 开启可以挡blind-xxe
        sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); // 无效
        sax.setNamespaceAware(false); // 无效
        XMLReader xmlReader = sax.newSAXParser().getXMLReader();
        xmlReader.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true); // 无效
        xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // 开启可以挡回显xxe和blind-xxe
        xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); // 无效
        xmlReader.setFeature("http://xml.org/sax/features/external-general-entities", false); // 未测试，因为没有回显成功
        xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false); // 开启可以挡blind-xxe
        Source source = new SAXSource(xmlReader, new InputSource(ResourceUtils.getPoc2()));
        Object obj = unmarshaller.unmarshal(source);
        System.out.println(((Person) obj).age);
        System.out.println(((Person) obj).name);
    }

    public static void test() {
        try {
            new UnmarshallerSample().safe();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void test2() {
        try {
            new UnmarshallerSample().safe2();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
