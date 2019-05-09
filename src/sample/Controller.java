package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public TextField login_email_tf;
    public TextField login_password_tf;
    private String login_email;
    private String login_password;

    public void loginButtonClicked (ActionEvent event) throws IOException {
        login_email = login_email_tf.getText().toString();
        login_password = login_password_tf.getText().toString();

        //todo check login info
        //todo if all's good (not null user) switch to main view
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    public void regButtonClicked (ActionEvent event) throws IOException {
        Parent regViewParent = FXMLLoader.load(getClass().getResource("registerationView.fxml"));
        Scene regViewScene = new Scene(regViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(regViewScene);
        window.show();
    }


    public void guestButtonClicked (ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }


}
