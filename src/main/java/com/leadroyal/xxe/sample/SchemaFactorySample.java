package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class SchemaFactorySample {
    public void safe() throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        StreamSource source = new StreamSource(ResourceUtils.getPoc1());
        Schema schema = factory.newSchema(source);
    }

    public static void test() {
        try {
            new SchemaFactorySample().safe();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
