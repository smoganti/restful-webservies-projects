package com.services.api.onlinestore.dao;

import com.services.api.onlinestore.model.Accessory;
import com.services.api.onlinestore.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SAXParserForProducts extends DefaultHandler {
    Product product;
    Accessory accessory;
    static Map<String, Product> products = new HashMap<String, Product>();
    @Value("${init.productXmlFileName}")
    String productXmlFileName;
    String elementValueRead;
    @Autowired
    MySQLDataStoreUtilities mySQLDataStoreUtilities;

    public void loadProductMap() {
        products = mySQLDataStoreUtilities.getProductDetails();

        if (products == null || products.size() == 0) {
            parseDocument();
            //log.info("products "+products);
            if (products != null)
                mySQLDataStoreUtilities.insertProducts(products);
        }

    }

    public void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            log.info("Parsing products " + productXmlFileName + " ");
            SAXParser parser = factory.newSAXParser();
            parser.parse(productXmlFileName, this);
        } catch (ParserConfigurationException e) {
            log.error("ParserConfig error");
        } catch (SAXException e) {
            log.error("SAXException : xml not well formed");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO error");
        }
    }


//    public Map<String, Product> getProducts() {
//
//        return products;
//    }


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
