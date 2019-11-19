package com.services.api.onlinestore.model;

import lombok.Data;

@Data
public class Order {
    private String userid;
    private String ordernumber;
    private String itemId;
    private Integer price;
    private Integer quantity;
    private String deliveryDate;
    private String shippingAddress;
    //private String itemUrl;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String name;
    private String cardNumber;
    private String orderStatus;

    public Order() {

    }

    public Order(String userid, String ordernumber, String itemId, Integer price, Integer quantity, String deliveryDate,
                 String shippingAddress, String address, String city, String state, String zip, String country, String name, String cardNumber) {
        super();
        this.userid = userid;
        this.ordernumber = ordernumber;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
        this.shippingAddress = shippingAddress;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.name = name;
        this.cardNumber = cardNumber;
        //log.d("Test in Order class  "+userid +" "+ ordernumber+ " "+itemId+" "+ price+" "+ quantity+" "+ deliveryDate+" "+ shippingAddress+" "+ address+" "+ city+" "+ state+" "+ zip+" "+ country+" "+ name+" "+ cardNumber);
    }

}
