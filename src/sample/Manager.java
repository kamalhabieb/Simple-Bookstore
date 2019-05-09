package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Manager {

    boolean addNewBooks(String ISBN,String title,String publicationYear,
                        String category, String publisher,int threshold,float price, int quantity){

        //publication year in the format "yyyy-[m]m-[d]d"
        //first the category id

        int categoryId =0,publisherId=0;
        Statement stmt=null;
        try {
            stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery(
                    "Select CATEGORY_ID From CATEGORY Where CATEGORY_NAME=" + category+";");
            if(rs.next()) {
                categoryId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //second the publisher id
        try {
            stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery(
                    "Select PUBLISHER_ID From PUBLISHER Where PUBLISHER_NAME=" + publisher+";");
            if(rs.next()) {
                publisherId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement addBook=SQLConnection.getInstance().getConnection().prepareStatement(
                    "INSERT INTO BOOK VALUES (?,?,?,?,?,?,?,?)");
            addBook.setString(1, ISBN);
            addBook.setString(2, title);
            addBook.setString(3, publicationYear);// todo this should be a year
            addBook.setInt(4, categoryId);
            addBook.setInt(5, publisherId);
            addBook.setInt(6, threshold);
            addBook.setFloat(7, price);
            addBook.setInt(8, quantity);
            if(addBook.execute()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // The user(Manager) must first search for the book to modify it
    // The search returns a book object
    // The Manager only updates quantity
    // if false then the new quantity is negative

    boolean modifyExisting(Book book,int soldQuantity){
        String ISBN = book.getIsbn();
        try {
            PreparedStatement addBook=SQLConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE BOOK SET QUANTITY = Quantity - " +soldQuantity+" WHERE ISBN = "+ISBN+" ;");
            if(addBook.execute()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
    `ID` VARCHAR(29) NOT NULL,
    `ISBN` VARCHAR(29) NOT NULL,
    `QUANTITY` INT NOT NULL,
    `CHECKOUT_TIME` TIMESTAMP NOT NULL,
    */

    // send book ISBN

    boolean placeOrder(String ISBN){
        int quantity =0, threshold =0, ordered=0;
        //search for an order for this book if any get the ordered quantity
        try {
            Statement stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery("Select THRESHOLD,QUANTITY From BOOK Where ISBN = " +ISBN+";");
            if(rs.next()) {
                threshold = rs.getInt(1);
                quantity = rs.getInt(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery("Select QUANTITY From BOOK_ORDER Where ISBN = " +ISBN+";");
            if(rs.next()) {
                ordered = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(threshold-ordered-quantity>0) {
            try {
                Date date = new Date();
                java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
                PreparedStatement addOrder = SQLConnection.getInstance().getConnection().prepareStatement(
                        "INSERT INTO BOOK_ORDER (ISBN,QUANTITY,CHECKOUT_TIME) VALUES (?,?,?)");
                addOrder.setString(1, ISBN);
                addOrder.setInt(2, threshold-ordered-quantity);
                addOrder.setTimestamp(3, ts);
                if (addOrder.execute()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            // no need for order
            return false;
        }
        return false;
    }

    //gets order, if found add quantity to book quantity, delete order (accepted)

    boolean confirmOrder(String orderId){
        String ISBN = "";
        int quantity =0;
        try {
            Statement stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery("Select ISBN,QUANTITY From BOOK_ORDER Where ID = " +orderId+";");
            if(rs.next()) {
                ISBN = rs.getString(1);
                quantity = rs.getInt(2);
            }
            PreparedStatement updateBookQuantity=SQLConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE BOOK SET QUANTITY = Quantity + " + quantity+" WHERE ISBN = "+ISBN+" ;");
            if(!updateBookQuantity.execute()){
                return false;
            }
            PreparedStatement deleteBookOrder=SQLConnection.getInstance().getConnection().prepareStatement(
                    "DELETE FROM BOOK_ORDER WHERE ID = " + orderId +" ;");
            if(deleteBookOrder.execute()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean promoteUser(User user){
        try {
            PreparedStatement promote = SQLConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE User SET IS_MANAGER = 1 WHERE USER_ID = "+user.getUserID()+" ;");
            if(promote.execute()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // view all orders

    ResultSet viewBookOrders(){
        ResultSet rs = null;
        try {
            Statement getAllOrders = SQLConnection.getInstance().getConnection().createStatement();
            rs = getAllOrders.executeQuery("SELECT * FROM BOOK_ORDER ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // view all orders of a certain book

    ResultSet viewBookOrders(String title){
        ResultSet rs = null;
        try {
            String ISBN = "";
            Statement getISBN = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs1 = getISBN.executeQuery("SELECT ISBN FROM BOOK WHERE TITLE = "+ title +";");
            if(rs1.next()){
                ISBN = rs1.getString(1);
            }
            Statement getAllOrders = SQLConnection.getInstance().getConnection().createStatement();
            rs = getAllOrders.executeQuery("SELECT * FROM BOOK_ORDER WHERE ISBN = "+ ISBN +";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

}

