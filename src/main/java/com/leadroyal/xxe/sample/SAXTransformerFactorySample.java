package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;

public class SAXTransformerFactorySample {
    public void safe() throws TransformerConfigurationException {
        SAXTransformerFactory sf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        sf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        sf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        StreamSource source = new StreamSource(ResourceUtils.getPoc1());
        sf.newTransformerHandler(source);
    }

    public static void test() {
        try {
            new SAXTransformerFactorySample().safe();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
}
