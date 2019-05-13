package sample;

public class OrderInfo {
    public String orderId;
    public String ISBN;
    public int quantity;
    public String checkoutDate;

    OrderInfo(String orderId, String ISBN, int quantity, String checkOutDate){
        this.orderId = orderId;
        this.ISBN = ISBN;
        this.quantity = quantity;
        this.checkoutDate = checkOutDate;
    }

}
