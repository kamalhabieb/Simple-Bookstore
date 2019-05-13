package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class cartViewController {

    public Label totalPriceLabel;
    public Label totalQuantityLabel;
    public VBox cartItemsVbox;
    private RegisteredCustomer regCustomer;
    private User usr;
    private ArrayList<CartItem> cartItems;
    private Double totalPrice=0.0;
    private int totalQuantity=0;

    public void initController(RegisteredCustomer registeredCustomer, User user) {
        this.regCustomer = registeredCustomer;
        this.usr = user;
        cartItemsVbox.setSpacing(20);
        cartItems = registeredCustomer.viewItemsInCart();
        viewCartItems(cartItems);

    }

    private void viewCartItems (ArrayList<CartItem> items){
        for(CartItem item : items){
            totalQuantity+=item.getQuantity();
            totalPrice+=item.getPrice()*item.getQuantity();
            HBox hbox = new HBox();
            Label title = new Label(item.getTitle());
            Label quantity = new Label("Quantity: "+item.getQuantity());
            Label price = new Label("Each: "+item.getPrice());
            Button increment = new Button("+");
            increment.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    regCustomer.increaseQuantityOfCartItem(item);
                    quantity.setText("Quantity: "+item.getQuantity());
                    totalPrice += item.getPrice();
                    totalPriceLabel.setText(""+totalPrice);
                    totalQuantity++;
                    totalQuantityLabel.setText(""+totalQuantity);
                }
            });
            Button decrement = new Button("-");
            decrement.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (item.getQuantity()>1) {
                        regCustomer.decreaseQuantityOfCartItem(item);
                        quantity.setText("Quantity: " + item.getQuantity());
                        totalPrice -= item.getPrice();
                        totalPriceLabel.setText("" + totalPrice);
                        totalQuantity--;
                        totalQuantityLabel.setText("" + totalQuantity);
                    }else{
                        hbox.setVisible(false);
                        totalPrice -= item.getPrice();
                        totalPriceLabel.setText("" + totalPrice);
                        totalQuantity--;
                        totalQuantityLabel.setText("" + totalQuantity);

                    }
                }
            });
            hbox.getChildren().addAll(title,quantity,price,increment,decrement);
            hbox.setSpacing(100);
            cartItemsVbox.getChildren().add(hbox);
        }

        totalPriceLabel.setText(""+totalPrice+" $");
        totalQuantityLabel.setText(""+totalQuantity+" Item");

    }

    public void checkourButtonPressed() throws SQLException {
        boolean valid = regCustomer.checkOutCart();
        System.out.println(valid);
        if (!valid){
            //todo show error
        }
    }

    public void backPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent mainViewParent = (Parent) loader.load();
        MainViewController mainViewController = loader.getController();
        mainViewController.initController(regCustomer,usr);
        Scene mainViewScene = new Scene(mainViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }


}
