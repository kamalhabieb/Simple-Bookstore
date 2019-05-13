package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public TextField login_email_tf;
    public PasswordField login_password_tf;
    private String login_email;
    private String login_password;
    private Customer customer;

    @FXML
    private void initialize (){
        customer = new Customer();
    }

    public void loginButtonClicked (ActionEvent event) throws IOException {
        login_email = login_email_tf.getText();
        login_password = login_password_tf.getText();
        //todo make the passwordField hidden
        User user = customer.login(login_email,login_password);
        if (user != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
            Parent mainViewParent = (Parent) loader.load();
            MainViewController mainViewController = loader.getController();
            mainViewController.initController(user);
            Scene mainViewScene = new Scene(mainViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainViewScene);
            window.show();
        }else{
            //todo show not registered
            System.out.println("Error");
        }

    }

    public void regButtonClicked (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerationView.fxml"));
        Parent regViewParent = (Parent) loader.load();
        RegisterationViewController regViewController = loader.getController();
        regViewController.initController(customer);

        Scene regViewScene = new Scene(regViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(regViewScene);
        window.show();
    }


//    public void guestButtonClicked (ActionEvent event) throws IOException {
//        Parent mainViewParent = FXMLLoader.load(getClass().getResource("mainView.fxml"));
//        Scene mainViewScene = new Scene(mainViewParent);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(mainViewScene);
//        window.show();
//    }


}
