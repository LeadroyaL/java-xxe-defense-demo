package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class SAXReaderSample {

    public void safe() throws SAXException, DocumentException {
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        saxReader.read(ResourceUtils.getPoc1());
    }

    public static void test(){
        try {
            new SAXReaderSample().safe();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
