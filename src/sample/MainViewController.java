package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    public VBox searchResultVBox;
    private User user;
    private RegisteredCustomer registeredCustomer;
    private FindBooks find;
    private int offset =1;

    @FXML
    private void initialize() { ;
        find = new FindBooks();
        searchResultVBox.setSpacing(10);
        search_type_box.setValue("ISBN");
        search_type_box.getItems().addAll("ISBN", "Title", "Category", "Publisher", "Author", "PubYear");

    }

    public void initController(RegisteredCustomer regCustomer, User usr) {
        this.registeredCustomer = regCustomer;
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

        switch (searchType) {
            case "ISBN": {
                Book book = find.findByISBN(searchValue);
                books.add(book);
                break;
            }
            case "Title": {
                Book book = find.findByTitle(searchValue);
                books.add(book);
                break;
            }
            case "Category": {
                books = find.findByCategory(searchValue,offset);
                break;
            }
            case "Publisher": {
                books = find.findByPublisher(searchValue,offset);
                break;
            }
            case "Author": {
                books = find.findByAuthor(searchValue,offset);
                break;
            }
            case "PubYear": {
                books = find.findByPubYear(searchValue,offset);
                break;
            }
        }
        addBookToRow(books);
    }

    public void managmentButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerView.fxml"));
        Parent managerViewParent = (Parent) loader.load();
        ManagerViewController managerViewController = loader.getController();
        managerViewController.initController(registeredCustomer,user);
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

    public void cartButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        Parent mainViewParent = (Parent) loader.load();
        cartViewController cartViewController = loader.getController();
        cartViewController.initController(registeredCustomer,user);
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

    private void addBookToRow(ArrayList<Book> books) {
        int current_row_books_num = 0;
        HBox hbox = new HBox();
        for (Book book : books) {
            if (current_row_books_num == 0) {
                hbox = new HBox();
                hbox.setSpacing(90);
                searchResultVBox.getChildren().add(hbox);
            }
            VBox vbox = new VBox();
            Label bookTitle = new Label(book.getTitle());
            Label bookPrice = new Label("" + book.getPrice() + " $");
            Label bookYear = new Label("" + book.getPublicationYear());
            Label bookQuantity = new Label("" + book.getQuantity() + " Available");
            Button add = new Button("Add To Cart");
            add.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        String orderId = registeredCustomer.getRandomOrderID();
                        registeredCustomer.addToShoppingCart(book.getIsbn(), 1, user,orderId,book.getTitle());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            vbox.setPrefSize(80, 50);
            vbox.getChildren().addAll(bookTitle, bookPrice, bookYear, bookQuantity, add);
            hbox.getChildren().add(vbox);
            current_row_books_num++;
            if (current_row_books_num >= 4)
                current_row_books_num = 0;
        }
    }

    public void loadNextPage(){
        offset++;
        searchButtonPressed();
    }

    public void loadPrevPage(){
        if (offset>1){
            offset--;
            searchButtonPressed();
        }
    }
    //todo check if guest/logged (customer/ manager -> show management button)
}
