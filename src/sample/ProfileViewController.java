package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProfileViewController {

    public TextField usr_name;
    public TextField f_name;
    public TextField l_name;
    public TextField email;
    public TextField phone_number;
    public TextField shipping_address;
    public PasswordField passwordField;
    private User user;

    private boolean editPassword;
    private RegisteredCustomer registeredCustomer;



    public void initController(User usr) {
        this.user = usr;
        this.registeredCustomer = new RegisteredCustomer();
        usr_name.setText(user.getUserName());
        usr_name.setEditable(false);
        f_name.setText(user.getfName());
        f_name.setEditable(false);
        l_name.setText(user.getlName());
        l_name.setEditable(false);
        email.setText(user.getEmail());
        email.setEditable(false);
        phone_number.setText(user.getPhoneNumber());
        phone_number.setEditable(false);
        shipping_address.setText(user.getShippingAddress());
        shipping_address.setEditable(false);
        passwordField.setEditable(false);
    }


    public void editInfo() {
        usr_name.setEditable(true);
        f_name.setEditable(true);
        l_name.setEditable(true);
        email.setEditable(true);
        phone_number.setEditable(true);
        shipping_address.setEditable(true);
        passwordField.setEditable(true);
    }

    public void confirmPressed() throws SQLException {
        String newUsrName = usr_name.getText();
        String newFName = f_name.getText();
        String newLName = l_name.getText();
        String newEmail = email.getText();
        String phoneNum = phone_number.getText();
        String shippingAdd = shipping_address.getText();
        String password = passwordField.getText();

        if (!newUsrName.trim().isEmpty()) {
            user.setUserName(newUsrName);
        }
        if (!newFName.trim().isEmpty()) {
            user.setfName(newFName);
        }
        if (!newLName.trim().isEmpty()) {
            user.setlName(newLName);
        }
        if (!newEmail.trim().isEmpty()) {
            user.setEmail(newEmail);
        }
        if (!phoneNum.trim().isEmpty()) {
            user.setPhoneNumber(phoneNum);
        }
        if (!shippingAdd.trim().isEmpty()) {
            user.setShippingAddress(shippingAdd);
        }
        if (!password.trim().isEmpty()) {
            user.setPassword(password);
        }

        Boolean valid = registeredCustomer.editPersonalInfo(user);
        if (!valid){
            //todo show error
        }
    }

    public void backPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent mainViewParent = (Parent) loader.load();
        MainViewController mainViewController = loader.getController();
        mainViewController.initController(registeredCustomer, user);
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }


}
