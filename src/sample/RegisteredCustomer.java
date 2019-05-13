package sample;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegisteredCustomer {
    private ArrayList<CartItem> shoppingCart = new ArrayList<>();

    boolean editPersonalInfo(User user) throws SQLException {
        Connection conc = SQLConnection.getInstance().getConnection();
        try {
            conc.setAutoCommit(false);
            conc.commit();
            PreparedStatement pstmt = conc.prepareStatement(
                    "UPDATE USER SET USER_NAME = ?, EMAIL = ?, FNAME = ?, LNAME = ?, " +
                            "PASSWORD = ?, PHONE_NUMBER = ?, SHIPPING_ADDRESS = ?, IS_MANAGER = ? WHERE USER_ID = ?;");
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getfName());
            pstmt.setString(4, user.getlName());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getPhoneNumber());
            pstmt.setString(7, user.getShippingAddress());
            pstmt.setString(8, user.isManager() ? "1" : "0");
            pstmt.setString(9, Integer.toString(user.getUserID()));
            if (pstmt.executeUpdate() == 1)
                return true;
        } catch(Exception e){
            System.out.println(e);
            if (conc != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conc.rollback();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            conc.setAutoCommit(true);

        }
        return false;
    }

    String getRandomOrderID() throws SQLException {
        String query ="select substr(concat(md5(rand()),md5(rand())),1,29);";
        ResultSet rs = SQLConnection.getInstance().getData(query);
        rs.next();
        return rs.getString(1);
    }
    boolean addToShoppingCart(String isbn, int quantity, User user, String orderID)throws SQLException{
        String query = "SELECT PRICE, QUANTITY FROM BOOK" +
                " WHERE ISBN ='" + isbn + "';";
        ResultSet rs = SQLConnection.getInstance().getData(query);
        if (rs.next()){
            int quantity1 = 0;
            int index = -1;
            float price = rs.getFloat("PRICE");
            for (int i = 0; i < shoppingCart.size(); i++) {
                if (shoppingCart.get(i).getIsbn().matches(isbn) &&
                        (shoppingCart.get(i).getUserID() == user.getUserID())){
                    quantity1 += shoppingCart.get(i).getQuantity();
                    index = i;
                    break;
                }
            }
            if (rs.getInt("QUANTITY") - quantity1 - quantity >= 0){
                if (index != -1){
                    shoppingCart.get(index).setQuantity(quantity + quantity1);
                } else {
                    CartItem cartItem = new CartItem(orderID, user.getUserID(), isbn, quantity, price);
                    shoppingCart.add(cartItem);

                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    ArrayList<CartItem> viewItemsInCart(){
        return shoppingCart;
    }

    float viewindividualPriceInCart(Book book){
        float price = 0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (shoppingCart.get(i).getIsbn().matches(book.getIsbn())){
                return shoppingCart.get(i).getPrice();
            }
        }
        return price;
    }

    float viewTotalPriceInCart(){
        float price = 0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            int quantity = shoppingCart.get(i).getQuantity();
            price += quantity * shoppingCart.get(i).getPrice();
        }
        return price;
    }

    boolean removeItemsFromCart(Book book){
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (shoppingCart.get(i).getIsbn().matches(book.getIsbn())){
                shoppingCart.remove(i);
                return true;
            }
        }
        return false;
    }

    void logout(User user){
        shoppingCart.clear();
    }

    boolean checkOutCart()throws SQLException{
        Connection conc = SQLConnection.getInstance().getConnection();
        try {
            conc.setAutoCommit(false);
            Statement stat = conc.createStatement();
            LocalDate currentDate = LocalDate.now();
            if (shoppingCart.size() == 0)
                return false;
            for (int i = 0; i < shoppingCart.size(); i++) {
                String isbn = shoppingCart.get(i).getIsbn();
                int quantity = shoppingCart.get(i).getQuantity();
                String query = "UPDATE BOOK SET QUANTITY = QUANTITY -" + String.valueOf(quantity) +
                        " WHERE ISBN ='" + isbn + "';";
                stat.executeUpdate(query);
                String testQuery = "SELECT USER_ID, ISBN FROM SHOPPING_CART NATURAL JOIN CART_ITEMS WHERE USER_ID =" +
                        String.valueOf(shoppingCart.get(i).getUserID()) +" AND ORDER_ID = '" + shoppingCart.get(i).getOrderID()
                        + "' AND ISBN = '" + isbn + "';";
                if (stat.executeQuery(testQuery).next()){
                    String query1 = "UPDATE CART_ITEMS SET QUANTITY = QUANTITY +" + String.valueOf(quantity) +
                            " WHERE ORDER_ID = '" + shoppingCart.get(i).getOrderID() + "' AND ISBN = '" + isbn + "';";
                    stat.executeUpdate(query1);
                } else {
                    String query1 = "INSERT INTO SHOPPING_CART VALUES ('" + shoppingCart.get(i).getOrderID() + "'," +
                            shoppingCart.get(i).getUserID() + ",'" + currentDate + "');";
                    String query2 = "INSERT INTO CART_ITEMS VALUES ('" + shoppingCart.get(i).getOrderID() + "','" + isbn + "',"
                            + String.valueOf(quantity) + "," + shoppingCart.get(i).getPrice() + ");";
                    stat.executeUpdate(query1);
                    stat.executeUpdate(query2);
                }
            }
            conc.commit();
            shoppingCart.clear();
            return true;
        } catch(Exception e){
            System.out.println(e);
            if (conc != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conc.rollback();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            conc.setAutoCommit(true);

        }
        return false;
    }

    //todo credit card info
    boolean checkCreditCard(String number,String expiryDate,String pin){
        if(number.length()==16 && number.charAt(0)=='4'){ // accepting visa only
            SimpleDateFormat textFormat = new SimpleDateFormat("dd-MM-yyyy");
            String paramDateAsString = expiryDate;
            java.util.Date myDate = null;
            java.util.Date now = new java.util.Date();

            try {
                myDate = textFormat.parse(paramDateAsString);
                if(myDate.before(now)){  // not expired
                    if(pin.length()==4){
                        return true;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
