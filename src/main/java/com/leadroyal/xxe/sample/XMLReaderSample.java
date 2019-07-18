package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class XMLReaderSample {
    public void safe() throws SAXException, IOException {
        // 2019年7月18日11:28:27
        // 测试环境8u172
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // 开启可挡回显xxe和blind-xxe
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); // 未测试，因为默认没有回显
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false); // 未测试，因为默认没有回显
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false); // 开启可挡blind-xxe
        reader.parse(new InputSource(ResourceUtils.getPoc1()));
    }

    public static void test() {
        try {
            new XMLReaderSample().safe();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
