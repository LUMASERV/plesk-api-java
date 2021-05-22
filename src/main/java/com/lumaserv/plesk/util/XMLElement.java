package com.lumaserv.plesk.util;

import com.lumaserv.plesk.PleskAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class XMLElement {

    private final Document document;
    private Element element;

    public XMLElement(String tag) {
        this(newDoc());
        document.setXmlStandalone(true);
        element = document.createElement(tag);
        document.appendChild(element);
    }

    public XMLElement(Document document) {
        this(document, document.getDocumentElement());
    }

    public XMLElement(Document document, Element element) {
        this.document = document;
        this.element = element;
    }

    public XMLElement(Document document, String tag, boolean append) {
        this.document = document;
        this.element = document.createElement(tag);
        if (append)
            document.appendChild(element);
    }

    public XMLElement(Document document, String tag) {
        this(document, tag, false);
    }

    public XMLElement(Document document, String ns, String tag, boolean append) {
        this.document = document;
        this.element = document.createElementNS(ns, tag);
        if (append)
            document.appendChild(element);
    }

    public XMLElement(Document document, String ns, String tag) {
        this(document, ns, tag, false);
    }

    public XMLElement add(String tag, Consumer<XMLElement> consumer) {
        XMLElement child = new XMLElement(document, tag);
        if(consumer != null)
            consumer.accept(child);
        element.appendChild(child.getElement());
        return this;
    }

    public XMLElement add(String ns, String tag, Consumer<XMLElement> consumer) {
        XMLElement child = new XMLElement(document, ns, tag);
        if(consumer != null)
            consumer.accept(child);
        element.appendChild(child.getElement());
        return this;
    }

    public XMLElement add(String tag, String text) {
        if (text == null)
            return this;

        element.appendChild(new XMLElement(document, tag).text(text).getElement());
        return this;
    }

    public XMLElement add(String tag) {
        element.appendChild(new XMLElement(document, tag).getElement());
        return this;
    }

    public XMLElement attr(String key, String value) {
        element.setAttribute(key, value);
        return this;
    }

    public XMLElement text(String text) {
        element.setTextContent(text);
        return this;
    }

    public String text() {
        return element.getTextContent();
    }

    public String tag() {
        return element.getTagName();
    }

    public XMLElement ns(String ns) {
        element.setAttribute("xmlns", ns);
        return this;
    }

    public XMLElement ns(String name, String ns) {
        element.setAttribute("xmlns:"+name, ns);
        return this;
    }

    public List<XMLElement> find(String tag) {
        List<XMLElement> list = new ArrayList<>();
        NodeList nodes = element.getElementsByTagName(tag);
        for(int i=0; i<nodes.getLength(); i++)
            list.add(new XMLElement(document, (Element) nodes.item(i)));
        return list;
    }

    public XMLElement findFirst(String tag) {
        NodeList nodes = element.getElementsByTagName(tag);
        if(nodes.getLength() == 0)
            return null;
        return new XMLElement(document, (Element) nodes.item(0));
    }

    public String findTextOf(String tag) {
        XMLElement e = findFirst(tag);
        return e == null ? null : e.text();
    }

    public Date findDateOf(String tag) {
        String text = findTextOf(tag);
        try {
            return text == null ? null : new SimpleDateFormat("yyyy.MM.dd").parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public Integer findIntOf(String tag) {
        String text = findTextOf(tag);
        try {
            return text == null ? null : Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Long findLongOf(String tag) {
        String text = findTextOf(tag);
        try {
            return text == null ? null : Long.parseLong(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public UUID findUUIDOf(String tag) {
        String text = findTextOf(tag);
        return (text == null || text.length() == 0) ? null : UUID.fromString(text);
    }

    public List<XMLElement> children() {
        List<XMLElement> list = new ArrayList<>();
        NodeList nodes = element.getChildNodes();
        for(int i=0; i<nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if(node instanceof Element)
                list.add(new XMLElement(document, (Element) node));
        }
        return list;
    }

    public String attr(String key) {
        return element.getAttribute(key);
    }

    public Element getElement() {
        return element;
    }

    public Document getDocument() {
        return document;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean omitXmlDeclaration) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("omit-xml-declaration", omitXmlDeclaration ? "yes" : "no");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(document), new StreamResult(baos));
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } catch (TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static XMLElement fromString(String xml) {
        try {
            return new XMLElement(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Document newDoc() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            return null;
        }
    }

}
