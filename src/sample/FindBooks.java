package sample;

import java.sql.ResultSet;
import java.util.ArrayList;

public class FindBooks {
    public Book findByISBN(String isbn){
        String query = "SELECT * FROM BOOK " +
                "WHERE ISBN = '" + isbn + "';";
        ResultSet rs = null;
        try {
            rs = SQLConnection.getInstance().getData(query);
            rs.next();
            Book book = constructBook(rs);
            return book;
        } catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public Book findByTitle(String title){
        String query = "SELECT * FROM BOOK " +
                "WHERE TITLE = '" + title + "';";
        ResultSet rs = null;
        try {
            rs = SQLConnection.getInstance().getData(query);
            rs.next();
            Book book = constructBook(rs);
            return book;
        } catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Book> findByCategory(String categoryName){
        String query = "SELECT * FROM BOOK NATURAL JOIN CATEGORY " +
                "WHERE CATEGORY_NAME = '" + categoryName + "';";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            rs.next();
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    public ArrayList<Book> findByPublisher(String publisherName){
        String query = "SELECT * FROM BOOK NATURAL JOIN PUBLISHER " +
                "WHERE PUBLISHER_NAME = '" + publisherName + "';";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            rs.next();
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    public ArrayList<Book> findByAuthor(String AuthorName){
        String query = "SELECT * FROM BOOK NATURAL JOIN ( AUTHOR NATURAL JOIN BOOK_AUTHOR) " +
                "WHERE AUTHOR_NAME = '" + AuthorName + "';";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            rs.next();
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    public ArrayList<Book> findByPubYear(int publicationYear){
        String query = "SELECT * FROM BOOK " +
                "WHERE PUBLICATION_YEAR = " + publicationYear + ";";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            rs.next();
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    private Book constructBook(ResultSet rs){
        Book book = new Book();
        try {
            book.setIsbn(rs.getString("ISBN"));
            book.setTitle(rs.getString("TITLE"));
            book.setPublicationYear(Integer.parseInt(rs.getString("PUBLICATION_YEAR")));
            book.setCategoryID(Integer.parseInt(rs.getString("CATEGORY_ID")));
            book.setPublisherID(Integer.parseInt(rs.getString("PUBLISHER_ID")));
            book.setThreshold(Integer.parseInt(rs.getString("THERSHOLD")));
            book.setPrice(Float.parseFloat(rs.getString("PRICE")));
            book.setQuantity(Integer.parseInt(rs.getString("QUANTITY")));
            return book;
        } catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
