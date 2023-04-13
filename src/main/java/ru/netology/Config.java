package ru.netology;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Config {
    protected Document doc;

    public Config(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.doc = builder.parse(file);
    }

    public String readConfig(String name, String atribute) {
        Element element = this.doc.getDocumentElement();
        Element setting = (Element) element.getElementsByTagName(name).item(0);
        Element value = (Element) setting.getElementsByTagName(atribute).item(0);
        return value.getTextContent();
    }

}