package com.services.api.onlinestore.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class cart {


    HashMap<String, List<Integer>> cartItems;
    HashMap<String, String> cartItemsUrl;

    public cart() {
        cartItems = new HashMap<String, List<Integer>>();
        cartItemsUrl = new HashMap<String, String>();

    }

    public HashMap getCartItems() {
        return cartItems;
    }

    public HashMap getCartItemsUrl() {
        return cartItemsUrl;
    }

    public void addToCartUrl(String itemId, String url) {

        cartItemsUrl.put(itemId, url);
    }

    public void addToCart(String itemId, int price, int quantity) {
        List<Integer> a = new ArrayList<Integer>();
        a.add(price);
        a.add(quantity);
        cartItems.put(itemId, a);
    }

    public int numberofitems() {
        return cartItems.size();
    }

    public void deleteFromCartUrl(String itemId) {
        cartItemsUrl.remove(itemId);
    }

    public void deleteFromCart(String itemId) {
        cartItems.remove(itemId);
    }

    public int cartCount() {
        return cartItems.size();
    }

}
