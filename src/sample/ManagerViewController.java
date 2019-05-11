package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class ManagerViewController {
    public TextField first;
    public TextField second;
    public TextField third;
    public TextField forth;
    public TextField fifth;
    public TextField sixth;
    public TextField seventh;
    public TextField eighth;
    public TextField ninth;
    public Button confirmBtn;
    public ScrollPane ordersScrollPane;
    public AnchorPane ordersAnchorPane;
    public Button addBtn;
    public Button modBtn;
    public Button ordBtn;
    public Button proBtn;
    public Button viewBtn;
    public Button bookOrdBtn;
    public Button confBtn;

    boolean addBookReady = false;
    String ISBN;
    String title;
    String publicationYear;
    String category;
    String publisher;
    int threshold;
    float price;
    int quantity;
    String author;
    private String modifyISBN;
    private int modifySoldQuantity;
    private boolean modifyBookReady = false;

    private String orderISBN;
    private boolean placeOrderReady = false;
    String orderId;
    private boolean confirmOrderReady = false;
    private boolean promoteUserReady = false;
    private int userId;
    private boolean bookOrdersReady = false;
    private String bookOrderISBN;
    private boolean viewOrdersReady = false;

    private Manager manager ;
    private int orderQuantity;

    private User user;

    public void initController(User usr){
        this.user = usr;
    }

    @FXML
    private void initialize(){
        manager = new Manager();
    }

    public void addBook() {
        if (!addBookReady) {
            first.setPromptText("ISBN");
            first.setVisible(true);
            second.setPromptText("Title");
            second.setVisible(true);
            third.setPromptText("Publication Year");
            third.setVisible(true);
            forth.setPromptText("Category");
            forth.setVisible(true);
            fifth.setPromptText("Publisher");
            fifth.setVisible(true);
            sixth.setPromptText("Threshold");
            sixth.setVisible(true);
            seventh.setPromptText("Price");
            seventh.setVisible(true);
            eighth.setPromptText("Quantity");
            eighth.setVisible(true);
            ninth.setPromptText("Author");
            ninth.setVisible(true);
            addBookReady = true;
            modBtn.setVisible(false);
            ordBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            confBtn.setVisible(false);
        } else {
            ISBN = first.getText();
            title = second.getText();
            publicationYear = third.getText();
            category = forth.getText();
            publisher = fifth.getText();
            threshold = Integer.parseInt(sixth.getText());
            price = Float.parseFloat(seventh.getText());
            quantity = Integer.parseInt(eighth.getText());
            author = ninth.getText();
            addBookReady = false;
            first.setVisible(false);
            second.setVisible(false);
            third.setVisible(false);
            forth.setVisible(false);
            fifth.setVisible(false);
            sixth.setVisible(false);
            seventh.setVisible(false);
            eighth.setVisible(false);
            ninth.setVisible(false);
            modBtn.setVisible(true);
            ordBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            confBtn.setVisible(true);

            manager.addNewBooks(ISBN, title, publicationYear, category, publisher, threshold, price, quantity, author);
        }

    }

    public void modifyExisting(){
        if (!modifyBookReady) {
            first.setPromptText("ISBN");
            first.setVisible(true);
            second.setPromptText("Sold Quantity");
            second.setVisible(true);
            modifyBookReady = true;
            ordersScrollPane.setVisible(false);

            addBtn.setVisible(false);
            ordBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            confBtn.setVisible(false);
        } else {
            modifyISBN = first.getText();
            modifySoldQuantity = Integer.parseInt(second.getText());
            modifyBookReady = false;
            first.setVisible(false);
            second.setVisible(false);
            manager.modifyExisting(modifyISBN,modifySoldQuantity);
            addBtn.setVisible(true);
            ordBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            confBtn.setVisible(true);
        }
    }

    public void placeOrder(){
        if (!placeOrderReady) {
            first.setPromptText("ISBN");
            first.setVisible(true);
            second.setPromptText("Quantity");
            second.setVisible(true);
            placeOrderReady = true;
            ordersScrollPane.setVisible(false);

            addBtn.setVisible(false);
            modBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            confBtn.setVisible(false);
        } else {
            orderISBN = first.getText();
            orderQuantity = Integer.parseInt(second.getText());
            placeOrderReady = false;
            first.setVisible(false);
            second.setVisible(false);
            manager.placeOrder(orderISBN,orderQuantity);
            addBtn.setVisible(true);
            modBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            confBtn.setVisible(true);
        }
    }

    public void confirmOrder(){
        if (!confirmOrderReady) {
            first.setPromptText("ISBN");
            first.setVisible(true);
            confirmOrderReady = true;
            ordersScrollPane.setVisible(false);

            addBtn.setVisible(false);
            modBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            ordBtn.setVisible(false);
        } else {
            orderId = first.getText();
            confirmOrderReady = false;
            first.setVisible(false);

            manager.confirmOrder(orderId);

            addBtn.setVisible(true);
            modBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            ordBtn.setVisible(true);
        }
    }

    public void promoteUser (){
        if (!promoteUserReady) {
            first.setPromptText("User ID");
            first.setVisible(true);
            promoteUserReady = true;
            ordersScrollPane.setVisible(false);

            addBtn.setVisible(false);
            modBtn.setVisible(false);
            confBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            ordBtn.setVisible(false);
        } else {
            userId = Integer.parseInt(first.getText());
            promoteUserReady = false;
            first.setVisible(false);

            manager.promoteUser(userId);

            addBtn.setVisible(true);
            modBtn.setVisible(true);
            confBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            ordBtn.setVisible(true);
        }
    }

    public void viewOrders (){
        if (!viewOrdersReady) {
            viewOrdersReady = true;
            ordersScrollPane.setVisible(true);

            addBtn.setVisible(false);
            modBtn.setVisible(false);
            confBtn.setVisible(false);
            proBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            ordBtn.setVisible(false);
        } else {
            viewOrdersReady = false;
            ordersScrollPane.setVisible(false);

            ResultSet resultSet = manager.viewBookOrders();

            addBtn.setVisible(true);
            modBtn.setVisible(true);
            confBtn.setVisible(true);
            proBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            ordBtn.setVisible(true);
        }

    }

    public void viewBookOrders (){
        if (!bookOrdersReady) {
            first.setPromptText("Book ISBN");
            first.setVisible(true);
            bookOrdersReady = true;
            ordersScrollPane.setVisible(false);

            addBtn.setVisible(false);
            modBtn.setVisible(false);
            confBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            ordBtn.setVisible(false);
        } else {
            bookOrderISBN = first.getText();
            bookOrdersReady = false;
            first.setVisible(false);

            ResultSet resultSet = manager.viewBookOrder(bookOrderISBN);

            addBtn.setVisible(true);
            modBtn.setVisible(true);
            confBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            ordBtn.setVisible(true);
        }
    }

    public void backPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent mainViewParent = (Parent) loader.load();
        MainViewController mainViewController = loader.getController();
        mainViewController.initController(user);
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }
}
