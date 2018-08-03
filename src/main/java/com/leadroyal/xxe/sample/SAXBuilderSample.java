package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;

public class SAXBuilderSample {
    public void safe() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        builder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        builder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        Document doc = builder.build(ResourceUtils.getPoc1());
    }

    public void safe2() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder(true);
        Document doc = builder.build(ResourceUtils.getPoc1());
    }

    public static void test() {
        try {
            new SAXBuilderSample().safe();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test2() {
        try {
            new SAXBuilderSample().safe2();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
