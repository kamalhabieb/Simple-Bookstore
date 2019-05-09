package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Book Store");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

//    static Connection connection = null;
//    static String databaseName=  "library";
//    static String databaseURL=  "jdbc:mysql://localhost:3306/"+databaseName;
//    static String userName = "root";
//    static String password = "TIGER";
    public static void main(String args[]){
        launch(args);
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//            connection = DriverManager.getConnection(
//                    databaseURL,userName,password);
//
//            Statement stmt=connection.createStatement();
//            ResultSet rs=stmt.executeQuery("select * from book");
//            while(rs.next())
//                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
//            connection.close();
//        }catch(Exception e){ System.out.println(e);}
    }
}
