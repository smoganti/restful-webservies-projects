package com.services.api.onlinestore.dao;

import com.services.api.onlinestore.model.Accessory;
import com.services.api.onlinestore.model.Product;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SAXParserForProducts extends DefaultHandler {
    Product product;
    Accessory accessory;
    Map<String, Product> products;
    String prodcutXmlFileName;
    String elementValueRead;


    public SAXParserForProducts(String prodcutXmlFileName) {
        this.prodcutXmlFileName = prodcutXmlFileName;
        products = new HashMap<>();
        parseDocument();
    }

    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            // String TOMCAT_HOME = System.getProperty("catalina.home");
            parser.parse("/Users/smoganti/eclipse-workspace/A20392260_Eclipse/src/ProductCatalog.xml", this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }


    public Map<String, Product> getProducts() {

        return products;
    }


    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("product")) {
            product = new Product();
            product.setId(attributes.getValue("id"));
        }

        if (elementName.equalsIgnoreCase("accessory")) {
            accessory = new Accessory();
        }

    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {

        if (element.equals("product")) {
            products.put(product.getId(), product);
            return;
        }
        if (element.equalsIgnoreCase("imageUrl")) {
            product.setImageUrl(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("name")) {
            product.setName(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("price")) {
            product.setPrice(Integer.parseInt(elementValueRead));
            return;
        }
        if (element.equalsIgnoreCase("quantity")) {
            product.setQuantity(Integer.parseInt(elementValueRead));
            return;
        }
        if (element.equalsIgnoreCase("description")) {
            product.setDescription(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("display_under")) {
            product.setDisplay_under(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("discounAmt")) {
            product.setDiscounAmt(Double.parseDouble(elementValueRead));
            return;
        }
        if (element.equalsIgnoreCase("rebateAmt")) {
            product.setRebateAmt(Double.parseDouble(elementValueRead));
            return;
        }


        if (element.equalsIgnoreCase("aname")) {
            accessory.setName(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("aId")) {
            accessory.setId(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("aprice")) {
            accessory.setPrice(Integer.parseInt(elementValueRead));
            return;
        }
        if (element.equalsIgnoreCase("adescription")) {
            accessory.setDescription(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("aimageUrl")) {
            accessory.setImageUrl(elementValueRead);
            return;
        }


        if (element.equalsIgnoreCase("accessory")) {
            product.getAccessories().put(accessory.getId(), accessory);
            return;
        }


    }

    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }
}
