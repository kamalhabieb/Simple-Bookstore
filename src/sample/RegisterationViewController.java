package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterationViewController {


    public TextField fName;
    public TextField phoneNumber;
    public TextField address;
    public TextField password;
    public TextField email;
    public TextField uName;
    public TextField lName;

    private String str_fName;
    private String str_phoneNumber;
    private String str_address;
    private String str_password;
    private String str_email;
    private String str_uName;
    private String str_lName;

    private Customer customer;

    @FXML
    private void initialize(){

    }

    public void initController(Customer cust, User usr){
        this.customer = cust;
    }



    public void regnButtonClicked (ActionEvent event) throws IOException {

        str_address = address.getText();
        str_email = email.getText();
        str_fName = fName.getText();
        str_lName = lName.getText();
        str_uName = uName.getText();
        str_password = password.getText();
        str_phoneNumber = phoneNumber.getText();

        Boolean valid = customer.register(str_uName, str_email,str_fName,str_lName,str_password,str_phoneNumber,str_address);

        if (valid){
            Parent initViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene initViewScene = new Scene(initViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(initViewScene);
            window.show();
        }
        else {
            //todo show invalid reg
        }

    }
}
