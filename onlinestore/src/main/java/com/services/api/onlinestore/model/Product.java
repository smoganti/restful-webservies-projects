package com.services.api.onlinestore.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Product {
    String name;
    String id;
    String description;
    String display_under;
    String imageUrl;
    int price;
    Double discounAmt;
    Double rebateAmt;
    int quantity;
    Map<String, Accessory> accessories;

    public Product() {
        accessories = new HashMap<String, Accessory>();
    }


}
