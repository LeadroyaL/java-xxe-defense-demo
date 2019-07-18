package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLInputFactorySample {
    public void safe() throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false); // 开启可挡回显xxe和blink-xxe
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false); // 开启可挡回显xxe和blink-xxe
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(ResourceUtils.getPoc1());
    }
    public static void test(){
        try {
            new XMLInputFactorySample().safe();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
