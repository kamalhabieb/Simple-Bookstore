package sample;

import javafx.event.ActionEvent;
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

    public void regnButtonClicked (ActionEvent event) throws IOException {

        str_address = address.getText().toString();
        str_email = email.getText().toString();
        str_fName = fName.getText().toString();
        str_lName = lName.getText().toString();
        str_uName = uName.getText().toString();
        str_password = password.getText().toString();
        str_phoneNumber = phoneNumber.getText().toString();

        //todo check reg info
        //todo if all's good (user created) switch to main view
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }
}
