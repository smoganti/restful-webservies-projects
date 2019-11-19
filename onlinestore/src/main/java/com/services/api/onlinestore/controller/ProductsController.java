package com.services.api.onlinestore.controller;


import com.services.api.onlinestore.dao.MySQLDataStoreUtilities;
import com.services.api.onlinestore.dao.SAXParserForProducts;
import com.services.api.onlinestore.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/products")
public class ProductsController {

    @Autowired
    MySQLDataStoreUtilities mySQLDataStoreUtilities;

    @Autowired
    SAXParserForProducts saxParserForProducts;

    @GetMapping(path = "/all")
    public HashMap<String, Product> retrieveAllProducts() {
        return mySQLDataStoreUtilities.getProductDetails();
    }

    @GetMapping(path = "/product/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return (mySQLDataStoreUtilities.getProductDetails() != null) ? mySQLDataStoreUtilities.getProductDetails().get(Integer.toString(id)) : null;
    }

    @GetMapping(path = "/product/category/{category}")
    public HashMap<String, Product> getProductById(@PathVariable String category) {
        HashMap<String, Product> pMap = mySQLDataStoreUtilities.getProductDetails();
        HashMap<String, Product> collect = new HashMap<String, Product>();
        if (pMap != null) {
            for (Map.Entry<String, Product> entry : pMap.entrySet()) {
                if (!StringUtils.isEmpty(category) && category.equalsIgnoreCase(entry.getValue().getDisplay_under().replaceAll("\\s", ""))) {
                    collect.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return collect;
    }


    @PostMapping(value = "/product")
//    public ResponseEntity<Product> savePost(@RequestBody Product product) {
    public int savePost(@RequestBody Product product) {
        int responseCode = 1;

        log.info("ProductController:: saveProduct():: saving product : " + product.toString());
        HashMap<String, Product> pMap = new HashMap<String, Product>();
        pMap.put(product.getId(), product);
        int savedProduct = mySQLDataStoreUtilities.insertProducts(pMap);

        if (savedProduct != -1) {
            log.error("Error Saving product");
            responseCode = savedProduct;
        }

        return responseCode;
    }

    @PostConstruct
    void init() {
        saxParserForProducts.loadProductMap();
        log.info("Products Loaded !");
    }

}
