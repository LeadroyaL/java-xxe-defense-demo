package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;

public class SAXBuilderSample {
    public void safe() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        // 2019年7月17日20:24:45
        // 测试环境8u172
        // 2019年7月18日11:52:21, jdom2与jdom1表现一致
        builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // 开启可挡回显xxe和blind-xxe
        builder.setFeature("http://xml.org/sax/features/external-general-entities", false); // 无效
        builder.setFeature("http://xml.org/sax/features/external-parameter-entities", false); // 开启可挡blind-xxe
        Document doc = builder.build(ResourceUtils.getPoc1());
    }

    public void unsafe2() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder(true); // 对blind-xxe无效，好像可以挡回显xxe，
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
            new SAXBuilderSample().unsafe2();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
