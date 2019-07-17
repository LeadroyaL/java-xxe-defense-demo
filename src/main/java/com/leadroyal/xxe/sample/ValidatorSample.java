package com.leadroyal.xxe.sample;

import com.leadroyal.xxe.ResourceUtils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class ValidatorSample {
    public void safe() throws SAXException, IOException {
        // 2019年7月18日00:42:28
        // 测试环境8u191
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = factory.newSchema();
        Validator validator = schema.newValidator();
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // 开启可挡回显xxe和blind-xxe
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // 疑似无影响
        StreamSource source = new StreamSource(ResourceUtils.getPoc1());
        validator.validate(source);
    }

    public static void test() {
        try {
            new ValidatorSample().safe();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
