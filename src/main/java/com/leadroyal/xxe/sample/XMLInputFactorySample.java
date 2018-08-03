package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLInputFactorySample {
    public void safe() throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        XMLStreamReader xsr = xmlInputFactory.createXMLStreamReader(ResourceUtils.getPoc1());
    }
    public static void test(){
        try {
            new XMLInputFactorySample().safe();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
