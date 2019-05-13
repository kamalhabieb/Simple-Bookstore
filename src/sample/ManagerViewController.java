package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public Button addBtn;
    public Button modBtn;
    public Button ordBtn;
    public Button proBtn;
    public Button viewBtn;
    public Button bookOrdBtn;
    public VBox ordersVBox;
    public Button topUsers;
    public Button topBooks;
    public Button topSales;
    public Button viewReports;
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
    private boolean promoteUserReady = false;
    private int userId;
    private boolean bookOrdersReady = false;
    private String bookOrderISBN;
    private boolean viewOrdersReady = false;
    private boolean viewReportReady = false;
    private Manager manager;
    private int orderQuantity;
    private User user;
    private RegisteredCustomer registeredCustomer;
    private ReportFunctions reportFunctions;

    public void initController(RegisteredCustomer registeredCustomer, User usr) {
        this.registeredCustomer = registeredCustomer;
        this.user = usr;
    }

    @FXML
    private void initialize() {
        manager = new Manager();
        reportFunctions = new ReportFunctions();
        ordersScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        ordersScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        ordersVBox.setSpacing(5);
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
            viewReports.setVisible(false);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
        } else {
            if (!first.getText().isEmpty() && !second.getText().isEmpty() && !third.getText().isEmpty() && !forth.getText().isEmpty() &&
                    !fifth.getText().isEmpty() && !sixth.getText().isEmpty() && !seventh.getText().isEmpty() && !eighth.getText().isEmpty() && !ninth.getText().isEmpty()) {
                ISBN = first.getText();
                title = second.getText();
                publicationYear = third.getText();
                category = forth.getText();
                publisher = fifth.getText();
                threshold = Integer.parseInt(sixth.getText());
                price = Float.parseFloat(seventh.getText());
                quantity = Integer.parseInt(eighth.getText());
                author = ninth.getText();
                manager.addNewBooks(ISBN, title, publicationYear, category, publisher, threshold, price, quantity, author);
            }
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
            viewReports.setVisible(true);
        }

    }

    public void modifyExisting() {
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
            viewReports.setVisible(false);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
        } else {
            if (!first.getText().isEmpty() && !second.getText().isEmpty() && !third.getText().isEmpty() && !forth.getText().isEmpty() &&
                    !fifth.getText().isEmpty()) {
                modifyISBN = first.getText();
                modifySoldQuantity = Integer.parseInt(second.getText());
                manager.modifyExisting(modifyISBN, modifySoldQuantity);
            }
            modifyBookReady = false;
            first.setVisible(false);
            second.setVisible(false);
            addBtn.setVisible(true);
            ordBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            viewReports.setVisible(true);
        }
    }

    public void placeOrder() {
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
            viewReports.setVisible(false);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
        } else {
            if (!first.getText().isEmpty() && !second.getText().isEmpty()) {
                orderISBN = first.getText();
                orderQuantity = Integer.parseInt(second.getText());
                manager.placeOrder(orderISBN, orderQuantity);
            }
            placeOrderReady = false;
            first.setVisible(false);
            second.setVisible(false);
            addBtn.setVisible(true);
            modBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            viewReports.setVisible(true);
        }
    }

    private void confirmOrder(String orderId) {
        manager.confirmOrder(orderId);

    }

    public void promoteUser() {
        if (!promoteUserReady) {
            first.setPromptText("User ID");
            first.setVisible(true);
            promoteUserReady = true;
            ordersScrollPane.setVisible(false);
            addBtn.setVisible(false);
            modBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            ordBtn.setVisible(false);
            viewReports.setVisible(false);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
        } else {
            if (!first.getText().isEmpty()) {
                userId = Integer.parseInt(first.getText());
                manager.promoteUser(userId);
            }
            promoteUserReady = false;
            first.setVisible(false);
            addBtn.setVisible(true);
            modBtn.setVisible(true);
            viewBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            ordBtn.setVisible(true);
            viewReports.setVisible(true);
        }
    }

    public void viewOrders() throws SQLException {
        if (!viewOrdersReady) {
            viewOrdersReady = true;
            ordersScrollPane.setVisible(true);
            addBtn.setVisible(false);
            modBtn.setVisible(false);
            proBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            ordBtn.setVisible(false);
            viewReports.setVisible(false);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
            ResultSet resultSet = manager.viewBookOrders();
            ArrayList<OrderInfo> orderInfos = new ArrayList<>();
            while (resultSet.next()) {
                OrderInfo orderInfo = new OrderInfo(resultSet.getString(1), resultSet.getString(2)
                        , resultSet.getInt(3), resultSet.getString(4));
                orderInfos.add(orderInfo);
                previewOrder(orderInfo);
            }
        } else {
            viewOrdersReady = false;
            ordersScrollPane.setVisible(false);
            addBtn.setVisible(true);
            modBtn.setVisible(true);
            proBtn.setVisible(true);
            bookOrdBtn.setVisible(true);
            ordBtn.setVisible(true);
            viewReports.setVisible(true);
            ordersVBox.getChildren().clear();
        }

    }

    private void previewOrder(OrderInfo orderInfo) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Label ISBN = new Label(orderInfo.ISBN);
        Label id = new Label(orderInfo.orderId);
        Label quantity = new Label("" + orderInfo.quantity);
        Label Date = new Label(orderInfo.checkoutDate);
        Button button = new Button("confirm");// todo backend don't add book in confirm order
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confirmOrder(orderInfo.orderId);
                hBox.setVisible(false);
            }
        });
        hBox.getChildren().addAll(ISBN, id, quantity, Date, button);
        ordersVBox.getChildren().add(hBox);
    }

    public void viewBookOrders() throws SQLException {
        if (!bookOrdersReady) {
            first.setPromptText("Book ISBN");
            first.setVisible(true);
            bookOrdersReady = true;
            ordersScrollPane.setVisible(false);
            addBtn.setVisible(false);
            modBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            ordBtn.setVisible(false);
            viewReports.setVisible(false);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
        } else {
            if (!first.getText().isEmpty()) {
                bookOrderISBN = first.getText();
                ResultSet resultSet = manager.viewBookOrder(bookOrderISBN);
                ArrayList<OrderInfo> orderInfos = new ArrayList<>();
                while (resultSet.next()) {
                    OrderInfo orderInfo = new OrderInfo(resultSet.getString(1), resultSet.getString(2)
                            , resultSet.getInt(3), resultSet.getString(4));
                    orderInfos.add(orderInfo);
                    previewOrder(orderInfo);
                }
            }
            bookOrdersReady = false;
            first.setVisible(false);
            addBtn.setVisible(true);
            modBtn.setVisible(true);
            proBtn.setVisible(true);
            viewBtn.setVisible(true);
            ordBtn.setVisible(true);
            viewReports.setVisible(true);
        }
    }
    
    public void viewReportPressed(){
        if (!viewReportReady) {
            viewReportReady = true;
            first.setVisible(false);
            second.setVisible(false);
            third.setVisible(false);
            forth.setVisible(false);
            fifth.setVisible(false);
            sixth.setVisible(false);
            seventh.setVisible(false);
            eighth.setVisible(false);
            ninth.setVisible(false);
            modBtn.setVisible(false);
            ordBtn.setVisible(false);
            proBtn.setVisible(false);
            viewBtn.setVisible(false);
            bookOrdBtn.setVisible(false);
            addBtn.setVisible(false);
            topBooks.setVisible(true);
            topUsers.setVisible(true);
            topSales.setVisible(true);
        }else{
            viewReportReady = false;
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
            addBtn.setVisible(true);
            topBooks.setVisible(false);
            topUsers.setVisible(false);
            topSales.setVisible(false);
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

    public void getTopUsersReport(){
        reportFunctions.getTopFiveUsers();
    }

    public void getTopBooksReport(){
        reportFunctions.getTopTenBooks();
    }

    public void getTopSalseReport(){
        reportFunctions.getTopSales();
    }
}
