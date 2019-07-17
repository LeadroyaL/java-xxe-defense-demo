package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParserFactorySample {
    public void safe() throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // 开启可挡回显xxe和blink-xxe
        spf.setFeature("http://xml.org/sax/features/external-general-entities", false); // 可能挡回显xxe，未测试，因为在这个case里，Handler完全是自己写
        spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false); // 开启可挡blink-xxe
        spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); // 无效
        SAXParser parser = spf.newSAXParser();
        parser.parse(ResourceUtils.getPoc1(), (HandlerBase) null);
    }

    public static void test() {
        try {
            new SAXParserFactorySample().safe();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
