package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLInputFactorySample {
    public void safe() throws XMLStreamException {
        // 2019年7月18日11:28:27
        // 测试环境8u172
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false); // 开启可挡回显xxe和blind-xxe
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false); // 开启可挡回显xxe和blind-xxe
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(ResourceUtils.getPoc1());
    }

    public static void test() {
        try {
            new XMLInputFactorySample().safe();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
