package com.services.api.onlinestore.dao;

import com.services.api.onlinestore.model.Accessory;
import com.services.api.onlinestore.model.Order;
import com.services.api.onlinestore.model.Product;
import com.services.api.onlinestore.model.User;

import java.sql.*;
import java.util.*;

public class MySQLDataStoreUtilities {

    public static Connection conn = null;

    public static int getConnection() {
        int i = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/smartportables", "root", "root");
            if (conn != null) {
                System.out.println("Connected to the database!");
                i = 1;
            } else {
                System.out.println("Failed to make connection!");
                i = 0;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            // conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int insertProducts(Map<String, Product> p) {
        try {
            if (getConnection() == 1) {
                Iterator it = p.entrySet().iterator();
                PreparedStatement pst = null;
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    Product product = (Product) pair.getValue();

                    String ProductsQuery = "INSERT INTO products(name,id,quantity,description,display_under,imageUrl,price,discountAmt,rebateAmt) "
                            + "VALUES (?,?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(ProductsQuery);
                    pst.setString(1, product.getName());
                    pst.setString(2, product.getId());
                    pst.setInt(3, product.getQuantity());
                    pst.setString(4, product.getDescription());
                    pst.setString(5, product.getDisplay_under());
                    pst.setString(6, product.getImageUrl());
                    pst.setInt(7, product.getPrice());
                    pst.setString(8, Double.toString(product.getDiscounAmt()));
                    pst.setString(9, Double.toString(product.getRebateAmt()));

                    pst.executeUpdate();

                    Iterator ita = product.getAccessories().entrySet().iterator();
                    while (ita.hasNext()) {
                        Map.Entry paira = (Map.Entry) ita.next();

                        Accessory accessory = (Accessory) paira.getValue();

                        String insertAccesoryDetails = "INSERT INTO accessorys(a_id,p_id, a_image, a_name, a_price, a_description) "
                                + "VALUES (?,?,?,?,?,?)";
                        pst = conn.prepareStatement(insertAccesoryDetails);
                        pst.setString(1, accessory.getId());
                        pst.setString(2, product.getId());
                        pst.setString(3, accessory.getImageUrl());
                        pst.setString(4, accessory.getName());
                        pst.setInt(5, accessory.getPrice());
                        pst.setString(6, accessory.getDescription());
                        pst.executeUpdate();
                        pst.close();
                    }
                }
                pst.close();
                conn.close();

                // return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public static HashMap<String, Product> getProductDetails() {
        try {
            if (getConnection() == 1) {
                HashMap<String, Product> ProductDetailsMap = new HashMap<String, Product>();
                String selectProductDetailsQuery = "select * from products";
                PreparedStatement pst = conn.prepareStatement(selectProductDetailsQuery);
                ResultSet rs = pst.executeQuery();
                Product product;
                while (rs.next()) {
                    if (!ProductDetailsMap.containsKey(rs.getString("id"))) {

                        product = new Product();
                        product.setId(rs.getString("id"));
                        product.setImageUrl(rs.getString("imageUrl"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getInt("price"));
                        product.setDescription(rs.getString("description"));
                        product.setDisplay_under(rs.getString("display_under"));
                        product.setDiscounAmt(rs.getDouble("discountAmt"));
                        product.setRebateAmt(rs.getDouble("rebateAmt"));
                        product.setQuantity(rs.getInt("quantity"));

                        String selectAccessoryDetailsQuery = "select * from accessorys where p_id='"
                                + rs.getString("id") + "'";
                        PreparedStatement pst1 = conn.prepareStatement(selectAccessoryDetailsQuery);
                        ResultSet rs1 = pst1.executeQuery();
                        Accessory accessory;
                        while (rs1.next()) {
                            // if(!AccessoryDetailsMap.containsKey(rs1.getString("a_id"))){

                            accessory = new Accessory();
                            accessory.setId(rs1.getString("a_id"));
                            accessory.setImageUrl(rs1.getString("a_image"));
                            accessory.setName(rs1.getString("a_name"));
                            accessory.setPrice(rs1.getInt("a_price"));
                            accessory.setDescription(rs1.getString("a_description"));

                            product.getAccessories().put(rs1.getString("a_id"), accessory); // set the respective
                            // accessorys of that
                            // product

                        }

                        ProductDetailsMap.put(rs.getString("id"), product);

                    }
                }

                return ProductDetailsMap;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertUser(User user) {
        try {
            if (getConnection() == 1) {
                String insertUser = "INSERT INTO users(firstName,lastName,userId,password,repassword,userType) "
                        + "VALUES (?,?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(insertUser);
                pst.setString(1, user.getFirstName());
                pst.setString(2, user.getLastName());
                pst.setString(3, user.getUserId());
                pst.setString(4, user.getPassword());
                pst.setString(5, user.getrePassword());
                pst.setString(6, user.getUserType());
                pst.executeUpdate();

                pst.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, User> getUsers() {
        try {
            if (getConnection() == 1) {
                HashMap<String, User> users = new HashMap<String, User>();
                String selectUserQuery = "select * from users";
                PreparedStatement pst = conn.prepareStatement(selectUserQuery);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setUserId(rs.getString("userId"));
                    user.setPassword(rs.getString("password"));
                    user.setrePassword(rs.getString("repassword"));
                    user.setUserType(rs.getString("userType"));

                    users.put(user.getUserId(), user);
                }

                pst.close();
                conn.close();
                return users;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int insertOrderDetails(Order order) {
        try {
            if (getConnection() == 1) {

                String insertOrderDetailsQuery = "INSERT INTO Orders(O_Id, u_Id, O_Ddate, O_Status, item, price, itemCount, f_name, address, city, state, zipcode, card_number) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(insertOrderDetailsQuery);
                pst.setString(1, order.getOrdernumber());
                pst.setString(2, order.getUserid());
                pst.setString(3, order.getDeliveryDate());
                pst.setString(4, "Pending");
                pst.setString(5, order.getItemId());
                pst.setInt(6, order.getPrice());
                pst.setInt(7, order.getQuantity());
                pst.setString(8, order.getName());
                pst.setString(9, order.getAddress());
                pst.setString(10, order.getCity());
                pst.setString(11, order.getState());
                pst.setString(12, order.getZip());
                pst.setString(13, order.getCardNumber());
                pst.executeUpdate();

                pst.close();
                conn.close();
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static HashMap<String, ArrayList<Order>> getOrderDetails() {
        try {
            if (getConnection() == 1) {
                HashMap<String, ArrayList<Order>> orderDetailsMap = new HashMap<String, ArrayList<Order>>();
                String selectOrderDetailsQuery = "select * from Orders";
                PreparedStatement pst = conn.prepareStatement(selectOrderDetailsQuery);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    if (!orderDetailsMap.containsKey(rs.getString("u_Id"))) {
                        ArrayList<Order> orderDetailsInfoArr = new ArrayList<Order>();
                        orderDetailsMap.put(rs.getString("u_Id"), orderDetailsInfoArr);
                    }
                    ArrayList<Order> orderDetailsInfoArrList = orderDetailsMap.get(rs.getString("u_Id"));

                    Order orderDetailsInfo = new Order();
                    orderDetailsInfo.setName(rs.getString("f_name"));
                    orderDetailsInfo.setAddress(rs.getString("address"));
                    orderDetailsInfo.setCity(rs.getString("city"));
                    orderDetailsInfo.setState(rs.getString("state"));
                    orderDetailsInfo.setZip(rs.getString("zipcode"));
                    orderDetailsInfo.setPrice(rs.getInt("price"));
                    // orderDetailsInfo.setPhone(rs.getString("phone"));
                    // orderDetailsInfo.setCard_name(rs.getString("card_name"));
                    orderDetailsInfo.setCardNumber(rs.getString("card_number"));
                    // orderDetailsInfo.setMonth(rs.getString("month"));
                    // orderDetailsInfo.setYear(rs.getString("year"));
                    // orderDetailsInfo.setSecurityCode(rs.getString("securityCode"));
                    orderDetailsInfo.setOrdernumber(rs.getString("O_Id"));
                    orderDetailsInfo.setDeliveryDate(rs.getString("O_Ddate"));
                    orderDetailsInfo.setOrderStatus("confirmed");
                    orderDetailsInfo.setItemId(rs.getString("item"));
                    orderDetailsInfo.setQuantity(rs.getInt("itemCount"));
                    orderDetailsInfo.setUserid(rs.getString("u_Id"));

                    orderDetailsInfoArrList.add(orderDetailsInfo);

                }

                return orderDetailsMap;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void cancelOrder(String order_id) {
        try {
            if (getConnection() == 1) {

                String deleteOrderQuery = "delete from Orders Where O_Id='" + order_id + "'";
                PreparedStatement pst1 = conn.prepareStatement(deleteOrderQuery);
                pst1.executeUpdate();
                pst1.close();
                conn.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Returns the Sales per day and date
    public static HashMap<Object, ArrayList<Integer>> getsalesperday() {

        // ArrayList<Object> a=new ArrayList<Object>();
        HashMap<Object, ArrayList<Integer>> data = new HashMap<Object, ArrayList<Integer>>();
        try {
            if (getConnection() == 1) {

                String selectUserQuery = "select COUNT(*) as sales,sum(price) as price, DATE_SUB(STR_TO_DATE(O_Ddate,'%c/%e/%Y') ,INTERVAL 14 DAY) as date from Orders  GROUP BY O_Ddate;";
                PreparedStatement pst = conn.prepareStatement(selectUserQuery);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    ArrayList<Integer> a = new ArrayList<Integer>();
                    a.add(rs.getInt("sales"));
                    a.add(rs.getInt("price"));

                    // a.add(rs.getTimestamp("date"));

                    data.put((rs.getTimestamp("date").toString()).substring(0, 10), a);

                }
                pst.close();
                conn.close();
                return data;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //onSale Products
    public static HashMap<String, List<Integer>> getOnsaleProducts() {
        HashMap<String, List<Integer>> data = new HashMap<String, List<Integer>>();
        try {
            if (getConnection() == 1) {

                String selectUserQuery = "select * from Products where discountAmt>0;";
                PreparedStatement pst = conn.prepareStatement(selectUserQuery);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    List<Integer> a = new ArrayList<Integer>();
                    a.add(rs.getInt("price"));

                    a.add(Integer.valueOf(rs.getString("discountAmt")));
                    data.put(rs.getString("name"), a);

                }
                pst.close();
                conn.close();
                return data;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Sales Report
    public static HashMap<String, List<Integer>> getsalesreport() {

        HashMap<String, List<Integer>> data = new HashMap<String, List<Integer>>();
        try {
            if (getConnection() == 1) {

                String selectUserQuery = "select item, price,sum(itemCount) as quantity from orders GROUP by item;";
                PreparedStatement pst = conn.prepareStatement(selectUserQuery);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    List<Integer> b = new ArrayList<Integer>();
                    b.add(rs.getInt("quantity"));

                    b.add(Integer.valueOf(rs.getString("price")));
                    data.put(rs.getString("item"), b);

                }
                pst.close();
                conn.close();
                return data;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Update Product Quantity in the Table
    public static void updateProducts(String name, int quant) {

        try {
            if (getConnection() == 1) {
                HashMap<String, Product> pMap = getProductDetails();

                for (HashMap.Entry<String, Product> entry : pMap.entrySet()) {
                    Product product = entry.getValue();
                    if (product.getName().equalsIgnoreCase(name)) {
                        quant = product.getQuantity() - quant;
                        String selectOrderDetailsQuery = "UPDATE products SET quantity=" + quant + " WHERE name=\""
                                + name + "\"";
                        PreparedStatement pst = conn.prepareStatement(selectOrderDetailsQuery);
                        pst.executeUpdate();

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, List<Integer>> getRebatedata() {

        HashMap<String, List<Integer>> data = new HashMap<String, List<Integer>>();
        try {
            if (getConnection() == 1) {

                String selectUserQuery = "select * from Products where rebateAmt>0;";
                PreparedStatement pst = conn.prepareStatement(selectUserQuery);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    List<Integer> a = new ArrayList<Integer>();
                    a.add(rs.getInt("price"));

                    a.add(Integer.valueOf(rs.getString("rebateAmt")));
                    data.put(rs.getString("name"), a);

                }
                pst.close();
                conn.close();
                return data;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
