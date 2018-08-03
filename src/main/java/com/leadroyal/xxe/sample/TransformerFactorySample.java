package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

public class TransformerFactorySample {
    public void safe() throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
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
