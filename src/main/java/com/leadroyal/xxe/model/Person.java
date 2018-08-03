package com.leadroyal.xxe.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
    @XmlElement(name = "age")
    public int age;
    @XmlElement(name = "person")
    public String name;
}
