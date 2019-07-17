package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class SAXReaderSample {

    public void safe() throws SAXException, DocumentException {
        // 2019年7月18日00:42:28
        // 测试环境8u191
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // 开启可挡回显xxe和blink-xxe
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false); // 开启可挡回显xxe
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false); // 开启可挡blink-xxe
        org.dom4j.Document doc = saxReader.read(ResourceUtils.getPoc1());

    }

    public static void test() {
        try {
            new SAXReaderSample().safe();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
