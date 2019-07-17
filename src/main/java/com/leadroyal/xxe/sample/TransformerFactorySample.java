package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

public class TransformerFactorySample {
    public void safe() throws TransformerException {
        // 2019年7月18日00:42:28
        // 测试环境8u191
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // 开启可挡回显xxe和blind-xxe
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); // 疑似无影响
        StreamSource source = new StreamSource(ResourceUtils.getPoc1());
        tf.newTransformer().transform(source, new DOMResult());
    }

    public static void test() {
        try {
            new TransformerFactorySample().safe();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
