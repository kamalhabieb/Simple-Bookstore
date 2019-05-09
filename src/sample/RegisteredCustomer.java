package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisteredCustomer {
   private ArrayList<CartItem> shoppingCart = new ArrayList<>();

    boolean editPersonalInfo(User user){
        try {
            PreparedStatement pstmt = SQLConnection.getInstance().getConnection().prepareStatement(
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
        }
        return false;
    }

    boolean addToShoppingCart(String isbn, int quantity, User user, String orderID)throws SQLException{
        String query = "SELECT PRICE, QUANTITY FROM BOOK" +
                "WHERE ISBN ='" + isbn + "';";
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

    boolean checkOutCart(){

        return false;
    }
}
