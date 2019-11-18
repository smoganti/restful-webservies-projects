package com.services.api.onlinestore.model;

import java.util.HashMap;
import java.util.Map;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay_under() {
        return display_under;
    }

    public void setDisplay_under(String display_under) {
        this.display_under = display_under;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<String, Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(Map<String, Accessory> accessories) {
        this.accessories = accessories;
    }

    public Double getDiscounAmt() {
        return discounAmt;
    }

    public void setDiscounAmt(Double discounAmt) {
        this.discounAmt = discounAmt;
    }

    public Double getRebateAmt() {
        return rebateAmt;
    }

    public void setRebateAmt(Double rebateAmt) {
        this.rebateAmt = rebateAmt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
