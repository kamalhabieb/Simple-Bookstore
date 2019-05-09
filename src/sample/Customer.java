package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

    User login(String email ,String password){ // returns a User Object if null then there is no user
        User user  = null;
        Statement stmt= null;
        try {
            stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("Select * From USER Where email="+email+"AND password="+password+";");
            if(rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5), rs.getString(6)
                        , rs.getString(7), rs.getString(8), rs.getBoolean(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    boolean register(String userName, String email, String fName,
                     String lName, String password, String phoneNumber, String shippingAddress){
        PreparedStatement registration;
        Statement stmt=null;
        try {
            stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery("Select * From USER Where email=" + email+";");
            if(rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            registration = SQLConnection.getInstance().getConnection().prepareStatement("INSERT INTO USER" +
                    " (USER_NAME,EMAIL,FNAME,LNAME,PASSWORD,PHONE_NUMBER,SHIPPING_ADDRESS,IS_MANAGER)" +
                    " VALUES (?,?,?,?,?,?,?,?);");
            registration.setString(1, userName);
            registration.setString(2, email);
            registration.setString(3, fName);
            registration.setString(4, lName);
            registration.setString(5, password);
            registration.setString(6, phoneNumber);
            registration.setString(7, shippingAddress);
            registration.setString(8, "0");
            if(registration.execute()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
