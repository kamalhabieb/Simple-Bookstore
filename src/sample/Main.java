package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Book Store");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
        FindBooks fb = new FindBooks();


        /*long startTime = System.currentTimeMillis();
        Book book= fb.findByTitle("book10");
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(book.toString());
//        for (int i = 0; i < books.size(); i++) {
//            System.out.println(books.get(i).toString());
//        }
        System.out.println(elapsedTime);
        */
        Manager mn = new Manager();
        Customer customer = new Customer();
        //    customer.register("Kamal","Kamal@yahoo.com","kamal","habieb","123456",
        //            "01234567892","Sand Station");
        //      customer.login("Kamal@yahoo.com","123456");
        //      mn.addNewBooks("WADDE","A World Of Ice and Fire","1996","History"
        //             ,"publisher8",80, (float) 32.99,100,"Author3");

        //     mn.modifyExisting("WADDE",90);
        /*
        mn.placeOrder("WADDE",50);
        mn.placeOrder("WADDE",50);
        mn.placeOrder("ADDDE",50);
        mn.placeOrder("ADLDDE",50);
        */
        //    mn.confirmOrder("WADDE");
        //   mn.promoteUser(1);
        ResultSet rs1 =mn.viewBookOrders();
        ResultSet rs2 =mn.viewBookOrder("WADDE");
        try {
            while(rs1.next()) {
                System.out.print(rs1.getString(1) + "   ");
                System.out.print(rs1.getString(2) + "   ");
                System.out.print(rs1.getInt(3) + "   ");
                System.out.println(rs1.getDate(4));
            }
            System.out.println("WADE");
            if(rs2.next()){
                System.out.print(rs2.getString(1) + "   ");
                System.out.print(rs2.getString(2) + "   ");
                System.out.print(rs2.getInt(3) + "   ");
                System.out.println(rs2.getDate(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        ArrayList<Book> books= fb.findByPubYear("2019");
//        for (int i = 0; i < books.size(); i++) {
//            System.out.println(books.get(i).toString());
//        }

//        Customer c = new Customer();
//        RegisteredCustomer rc = new RegisteredCustomer();
//        User user = c.getUser("u1@gmail.com");
//        user.setUserName("hanan");
//        boolean done = rc.editPersonalInfo(user);
//        System.out.println(done);
//        try {
//            Connection conc = SQLConnection.getInstance().getConnection();
//            String reportPath = "C:\\Users\\Hanan Elkhateeb\\Downloads\\report\\TopFiveUsers.jrxml";
//            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,conc);
//            JasperViewer.viewReport(jasperPrint);
//            conc.close();
//        } catch (JRException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }
}

