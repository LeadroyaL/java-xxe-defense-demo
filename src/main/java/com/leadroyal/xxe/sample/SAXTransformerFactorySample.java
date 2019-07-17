package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;

public class SAXTransformerFactorySample {
    public void safe() throws TransformerConfigurationException {
        // 2019年7月18日00:42:28
        // 测试环境8u191
        SAXTransformerFactory sf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        sf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // 开启可挡回显xxe和blind-xxe
        sf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); // 疑似无影响
        StreamSource source = new StreamSource(ResourceUtils.getPoc3());
        sf.newXMLFilter(source);
    }

    public static void test() {
        try {
            new SAXTransformerFactorySample().safe();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
}
