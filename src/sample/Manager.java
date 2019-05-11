package sample;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Manager {

    boolean addNewBooks(String ISBN, String title, String publicationYear,
                        String category, String publisher, int threshold, float price, int quantity, String author){


        //publication year in the format "yyyy-[m]m-[d]d"
        //first the category id

        int categoryId =0,publisherId=0;
        Statement stmt=null;
        try {
            stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery(
                    "Select CATEGORY_ID From CATEGORY Where CATEGORY_NAME = '" + category+"' ;");
            if(rs.next()) {
                categoryId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //second the publisher id
        Statement stmt1 = null;
        try {
            stmt1 = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs2= stmt1.executeQuery(
                    "Select PUBLISHER_ID From PUBLISHER Where PUBLISHER_NAME = '" + publisher+"\r' ;");
            if(rs2.next()) {
                publisherId = rs2.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            PreparedStatement addNewBook=SQLConnection.getInstance().getConnection().prepareStatement(
                    "INSERT INTO online_book_store.book VALUES (?,?,?,?,?,?,?,?);");
            addNewBook.setString(1, ISBN);
            addNewBook.setString(2, title);
            addNewBook.setString(3, publicationYear);// todo this should be a year
            addNewBook.setInt(4, categoryId);
            addNewBook.setInt(5, publisherId);
            addNewBook.setInt(6, threshold);
            addNewBook.setFloat(7, price);
            addNewBook.setInt(8, quantity);
            if(addNewBook.execute()){
                return false;
            }
            int authorId = 1;
            ResultSet resultSet = SQLConnection.getInstance().getData(
                    "SELECT AUTHOR_ID FROM AUTHOR WHERE AUTHOR_NAME = '"+author+"\r';");
            if(resultSet.next()){
                authorId = resultSet.getInt(1);
            }
            PreparedStatement addBookAuthor = SQLConnection.getInstance().getConnection().prepareStatement(
                    "INSERT INTO BOOK_AUTHOR VALUES (?,?)");
            addBookAuthor.setInt(1,authorId);
            addBookAuthor.setString(2,ISBN);

            if(addBookAuthor.execute()){
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // The user(Manager) must first search for the book to modify it
    // The search returns a book object
    // The Manager only updates quantity
    // if false then the new quantity is negative

    boolean modifyExisting(String ISBN, int soldQuantity){
        try {
            PreparedStatement addBook=SQLConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE BOOK SET QUANTITY = Quantity - " +soldQuantity+" WHERE ISBN = '"+ISBN+"' ;");
            if(addBook.execute()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    /*
    `ID` VARCHAR(29) NOT NULL,
    `ISBN` VARCHAR(29) NOT NULL,
    `QUANTITY` INT NOT NULL,
    `CHECKOUT_TIME` TIMESTAMP NOT NULL,
    */

    // send book ISBN

    boolean placeOrder(String ISBN, int quantity){
        int  ordered=0;

        try {
            Statement stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery("Select QUANTITY From BOOK_ORDER Where ISBN = '" +ISBN+"' ;");
            if(rs.next()) {
                ordered = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            try {
                Date date = new Date();
                java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
                if(ordered>0) {
                    PreparedStatement addOrder = SQLConnection.getInstance().getConnection().prepareStatement(
                            "UPDATE BOOK_ORDER SET QUANTITY = ?,CHECKOUT_TIME = ? WHERE ISBN = ?");
                    addOrder.setString(3, ISBN);
                    addOrder.setInt(1, quantity + ordered);
                    addOrder.setTimestamp(2, ts);
                    if (addOrder.execute()) {
                        return false;
                    }
                }
                else{
                    PreparedStatement addOrder = SQLConnection.getInstance().getConnection().prepareStatement(
                            "INSERT INTO BOOK_ORDER VALUES (?,?,?,?)");
                    addOrder.setString(1, ISBN);
                    addOrder.setString(2, ISBN);
                    addOrder.setInt(3, quantity + ordered);
                    addOrder.setTimestamp(4, ts);
                    if (addOrder.execute()) {
                        return false;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return true;
    }

    //gets order, if found add quantity to book quantity, delete order (accepted)

    boolean confirmOrder(String orderId){
        String ISBN = "";
        int quantity =0;
        try {
            Statement stmt = SQLConnection.getInstance().getConnection().createStatement();
            ResultSet rs= stmt.executeQuery("Select ISBN,QUANTITY From BOOK_ORDER Where ID = '" +orderId+"' ;");
            if(rs.next()) {
                ISBN = rs.getString(1);
                quantity = rs.getInt(2);
            }
            PreparedStatement updateBookQuantity=SQLConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE BOOK SET QUANTITY = Quantity + " + quantity+" WHERE ISBN = '"+ISBN+"' ;");
            if(updateBookQuantity.execute()){
                return false;
            }
            PreparedStatement deleteBookOrder=SQLConnection.getInstance().getConnection().prepareStatement(
                    "DELETE FROM BOOK_ORDER WHERE ID = '" + orderId +"' ;");
            if(deleteBookOrder.execute()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    boolean promoteUser(int userId){
        try {
            PreparedStatement promote = SQLConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE User SET IS_MANAGER = 1 WHERE USER_ID = "+userId+" ;");
            if(promote.execute()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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

    ResultSet viewBookOrder(String ISBN){
        ResultSet rs = null;
        try {
            Statement getAllOrders = SQLConnection.getInstance().getConnection().createStatement();
            rs = getAllOrders.executeQuery("SELECT * FROM BOOK_ORDER WHERE ISBN = '"+ ISBN +"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

   /* void createReports() {
        JasperReport jasperReport;

        {
            try {
                jasperReport = JasperCompileManager.compileReport("Total Sales For Books in Previous Month.jrxml");
                JRDataSource jrDataSource = new JREmptyDataSource();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrDataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, "Total Sales For Books in Previous Month.pdf");
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }*/



}

