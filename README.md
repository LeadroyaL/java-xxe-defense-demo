# java-xxe-defense-demo

2018年8月，在微信支付SDK的XXE影响下，写了百分百可以防御住的demo，并且经过了配置，有些配置是冗余的。

2019年7月，重新测试了每个设置对XXE的影响，之前为了保险建议全部都添加，现在筛选出了起作用的和不起作用的设置。

测试方式是使用两种poc，看是否可以读取到文件，一种是使用通用实体(external-general-entity)，将文件内容打到xml的内容里，一种是使用参数实体(external-parameter-entity)，将文件内容外带。

配合攻击demo食用更佳：

### 有效的防御方式的列表

| FEATURE | String |
| --- | --- |
| A | http://javax.xml.XMLConstants/feature/secure-processing |
| B | http://apache.org/xml/features/disallow-doctype-decl |
| C | http://xml.org/sax/features/external-parameter-entities |
| D | http://xml.org/sax/features/external-general-entities |
| E | http://javax.xml.XMLConstants/property/accessExternalDTD |
| F | http://javax.xml.XMLConstants/property/accessExternalStylesheet |
| G | http://javax.xml.XMLConstants/property/accessExternalSchema |
| H | javax.xml.stream.supportDTD |
| I | javax.xml.stream.isSupportingExternalEntities |


| 组件 | FEATURE | 作用 |
|------|--------|-----|
| DocumentBuilderFactory | A | 禁用DTD |
| DocumentBuilderFactory | B | 禁用DTD |
| DocumentBuilderFactory | C | 可挡blind-xxe |
| DocumentBuilderFactory | D | 可挡回显xxe |
| SAXBuilder | A | 禁用DTD |
| SAXBuilder | C | 可挡blind-xxe |
| SAXParserFactory | B | 禁用DTD |
| SAXParserFactory | C | 可挡blind-xxe |
| SAXParserFactory | D | 不确定，因为默认无回显 | 
| SAXReader | B | 禁用DTD |
| SAXReader | C | 可挡blind-xxe |
| SAXReader | D | 可挡回显xxe |
| SAXTransformerFactory | E | 禁用DTD |
| SAXTransformerFactory | F | 不确定，引入额外stylesheet没有攻击成功 |
| SchemaFactory | E | 禁用DTD |
| SchemaFactory | G | 不确定，引入额外schema没有攻击成功 |
| TransformerFactory | E | 禁用DTD |
| TransformerFactory | F | 不确定，引入额外stylesheet没有攻击成功 |
| Unmarshaller | - | 默认禁用DTD |
| Validator | E | 禁用DTD |
| Validator | G | 不确定，引入额外schema没有攻击成功 |
| XMLInputFactory | H | 禁用DTD |
| XMLInputFactory | I | 禁用DTD |
| XMLReaderFactory | B | 禁用DTD |
| XMLReaderFactory | C | 可挡blind-xxe |
| XMLReaderFactory | D | 不确定，因为默认无回显 |

### 输出全部支持的feature和properties:

`com.sun.org.apache.xerces.internal.impl.Constants.Main(String[] args)`
 

### 推荐的最小化配置（出锅了别怪我）

一般来说，禁用了DTD就万事大吉了，禁用了通用实体就不会被回显，禁用了参数实体就不会被oob。

### DocumentBuilderFactory_v1

```java
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    String FEATURE = null;
    FEATURE = XMLConstants.FEATURE_SECURE_PROCESSING;
    dbf.setFeature(FEATURE, true);
    DocumentBuilder builder = dbf.newDocumentBuilder();
```

### DocumentBuilderFactory_v2

```java
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    String FEATURE = null;
    FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    dbf.setFeature(FEATURE, true);
    DocumentBuilder builder = dbf.newDocumentBuilder();
```

### DocumentBuilderFactory_v3

```java
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    FEATURE = "http://xml.org/sax/features/external-parameter-entities";
    dbf.setFeature(FEATURE, false);
    FEATURE = "http://xml.org/sax/features/external-general-entities";
    dbf.setFeature(FEATURE, false);
    DocumentBuilder builder = dbf.newDocumentBuilder();
```

### SAXBuilder

```java
    SAXBuilder builder = new SAXBuilder();
    builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    Document doc = builder.build(ResourceUtils.getPoc1());
```

### SAXParserFactory

```java
    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    SAXParser parser = spf.newSAXParser();
    parser.parse(ResourceUtils.getPoc1(), (HandlerBase) null);
```

### SAXReader_v1

```java
    SAXReader saxReader = new SAXReader();
    saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    org.dom4j.Document doc = saxReader.read(ResourceUtils.getPoc1());
```

### SAXReader_v2

```java
    SAXReader saxReader = new SAXReader();
    saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
    saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    org.dom4j.Document doc = saxReader.read(ResourceUtils.getPoc1());
```

### SAXTransformerFactory

```java
    SAXTransformerFactory sf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
    sf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    StreamSource source = new StreamSource(ResourceUtils.getPoc3());
    sf.newXMLFilter(source);
```

### SchemaFactory

```java
    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    StreamSource source = new StreamSource(ResourceUtils.getPoc1());
    Schema schema = factory.newSchema(source);
```

### TransformerFactory

```java
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    StreamSource source = new StreamSource(ResourceUtils.getPoc1());
    tf.newTransformer().transform(source, new DOMResult());
```

### Unmarshaller

```java
    Class tClass = Person.class;
    JAXBContext context = JAXBContext.newInstance(tClass);
    Unmarshaller um = context.createUnmarshaller();
    Object o = um.unmarshal(ResourceUtils.getPoc2());
    tClass.cast(o);
```

### Validator

```java
    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    Schema schema = factory.newSchema();
    Validator validator = schema.newValidator();
    validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    StreamSource source = new StreamSource(ResourceUtils.getPoc1());
    validator.validate(source);
```

### XMLInputFactory_v1

```java
    XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(ResourceUtils.getPoc1());
```

### XMLInputFactory_v2

```java
    XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(ResourceUtils.getPoc1());
```

### XMLReader

```java
    XMLReader reader = XMLReaderFactory.createXMLReader();
    reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    reader.parse(new InputSource(ResourceUtils.getPoc1()));
```