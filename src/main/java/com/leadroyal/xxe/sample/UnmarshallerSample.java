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
        Class tClass = Person.class;
        JAXBContext context = JAXBContext.newInstance(tClass);
        Unmarshaller um = context.createUnmarshaller();
        Object o = um.unmarshal(ResourceUtils.getPoc1());
        tClass.cast(o);
    }

    public void safe2() throws SAXException, ParserConfigurationException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        sax.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        sax.setFeature("http://xml.org/sax/features/external-general-entities", false);
        sax.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        sax.setNamespaceAware(false);
        XMLReader xmlReader = sax.newSAXParser().getXMLReader();
        xmlReader.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        xmlReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        Source source = new SAXSource(xmlReader, new InputSource(ResourceUtils.getPoc1()));
        unmarshaller.unmarshal(source);

    }

    public static void test() {
        try {
            new UnmarshallerSample().safe();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
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
