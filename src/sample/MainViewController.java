package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainViewController {
    public ChoiceBox search_type_box;
    public Button management_button;
    public TextField search_box;
    public Button search_button;
    public Button profileBtn;
    public Button logoutBtn;

    private User user;
    private RegisteredCustomer registeredCustomer;
    private FindBooks find;
    @FXML
    private void initialize() {
        registeredCustomer = new RegisteredCustomer();
            find = new FindBooks();
        search_type_box.setValue("ISBN");
        search_type_box.getItems().addAll("ISBN", "Title", "Category", "Publisher", "Author", "PubYear");

    }

    public void initController(User usr) {
        this.user = usr;
        if (user.isManager()) {
            management_button.setVisible(true);
        }
    }

    public void searchButtonPressed() {
        String searchType = search_type_box.getValue().toString();
        String searchValue = search_box.getText();

        ArrayList books = new ArrayList<Book>();
        //todo if invalid search show error

        switch (searchType){
            case "ISBN":{
                Book book = find.findByISBN(searchValue);
                books.add(book);
                break;
            }
            case "Title":{
                Book book = find.findByTitle(searchValue);
                books.add(book);
                break;
            }
            case "Category":{
                books = find.findByCategory(searchValue);
                break;
            }
            case "Publisher":{
                books = find.findByPublisher(searchValue);
                break;
            }
            case "Author":{
                books = find.findByAuthor(searchValue);
                break;
            }
            case "PubYear":{
                books = find.findByPubYear(searchValue);
                break;
            }
        }
    }

    public void managmentButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerView.fxml"));
        Parent managerViewParent = (Parent) loader.load();
        ManagerViewController managerViewController = loader.getController();
        managerViewController.initController(user);
        Scene profileViewScene = new Scene(managerViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
    }

    public void profileButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileView.fxml"));
        Parent mainViewParent = (Parent) loader.load();
        ProfileViewController profileViewController = loader.getController();
        profileViewController.initController(user);
        Scene profileViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(profileViewScene);
        window.show();
    }

    public void cartButtonPressed(ActionEvent event) throws IOException, SQLException {
        String orderId = registeredCustomer.getRandomOrderID();
        registeredCustomer.addToShoppingCart("ADDDE",12,user,orderId);
        ///////////////////////
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        Parent mainViewParent = (Parent) loader.load();
        cartViewController cartViewController = loader.getController();
        cartViewController.initController(registeredCustomer);
        Scene cartViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cartViewScene);
        window.show();
    }

    public void logoutButtonPressed(ActionEvent event) throws IOException {
        registeredCustomer.logout(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent loginViewParent = (Parent) loader.load();
        Scene loginViewScene = new Scene(loginViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    //todo check if guest/logged (customer/ manager -> show management button)
}
