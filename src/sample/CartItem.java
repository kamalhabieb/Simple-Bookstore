package sample;

public class CartItem {
    private String orderID;
    private String isbn;
    private int quantity;
    private float price;
    private int userID;
    private String title;

    public CartItem(String orderID, int userID, String isbn, int quantity, float price,String title) {
        this.orderID = orderID;
        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
        this.userID = userID;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
