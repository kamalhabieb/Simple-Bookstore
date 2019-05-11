package sample;

public class Book {
    private String isbn;
    private String title;
    private String publicationYear;
    private int categoryID;
    private int publisherID;
    private int threshold;
    private float price;
    private int quantity;

//    public Book(String isbn, String title, int publicationYear, int categoryID, int publisherID, int threshold,
//                float price, int quantity, ArrayList<String> authorsNames) {
//        this.isbn = isbn;
//        this.title = title;
//        this.publicationYear = publicationYear;
//        this.categoryID = categoryID;
//        this.publisherID = publisherID;
//        this.threshold = threshold;
//        this.price = price;
//        this.quantity = quantity;
//        this.authorsNames = authorsNames;
//    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", categoryID=" + categoryID +
                ", publisherID=" + publisherID +
                ", threshold=" + threshold +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
