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

    public ArrayList<Book> findByCategory(String categoryName,int offset){
        String query = "SELECT * FROM BOOK NATURAL JOIN CATEGORY " +
                "WHERE CATEGORY_NAME = '" + categoryName +
                "' LIMIT "+ (offset-1)*50 +",50;";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    public ArrayList<Book> findByPublisher(String publisherName,int offset){
        String query = "SELECT * FROM BOOK NATURAL JOIN PUBLISHER " +
                "WHERE PUBLISHER_NAME = '" + publisherName + "' LIMIT "+ (offset-1)*50 +",50;";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    public ArrayList<Book> findByAuthor(String AuthorName,int offset){
        String query = "SELECT * FROM BOOK NATURAL JOIN ( AUTHOR NATURAL JOIN BOOK_AUTHOR) " +
                "WHERE AUTHOR_NAME = '" + AuthorName + "' LIMIT "+ (offset-1)*50 +",50;";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            while (rs.next()) {
                books.add(constructBook(rs));
            }
            rs.close();
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }

    public ArrayList<Book> findByPubYear(String publicationYear,int offset){
        String query = "SELECT * FROM BOOK " +
                "WHERE PUBLICATION_YEAR = '" + publicationYear + "' LIMIT "+ (offset-1)*50 +",50;";
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = SQLConnection.getInstance().getData(query);
            while (rs.next()) {
                books.add(constructBook(rs));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return books;
    }
    public ArrayList<Book> prevPageCategory(String category,int currentPage){
        if(currentPage>1) {
            return findByCategory(category, currentPage - 1);
        }
        return null; // 0 page
    }
    public ArrayList<Book> prevPagePublisher(String publisher,int currentPage){
        if(currentPage>1) {
            return findByPublisher(publisher,currentPage-1);
        }
        return null; // 0 page
    }
    public ArrayList<Book> prevPageAuthor(String author,int currentPage){
        if(currentPage>1) {
            return findByAuthor(author,currentPage-1);
        }
        return null; // 0 page
    }
    public ArrayList<Book> prevPagePublicationYear(String publicationYear,int currentPage){
        if(currentPage>1) {
            return findByPubYear(publicationYear,currentPage-1);
        }
        return null; // 0 page
    }
    public ArrayList<Book> nextPageCategory(String category,int currentPage){
        return findByCategory(category,currentPage+1);
    }
    public ArrayList<Book> nextPagePublisher(String publisher,int currentPage){
        return findByPublisher(publisher,currentPage+1);
    }
    public ArrayList<Book> nextPageAuthor(String author,int currentPage){
        return findByAuthor(author,currentPage+1);
    }
    public ArrayList<Book> nextPagePublicationYear(String publicationYear,int currentPage){
        return findByPubYear(publicationYear,currentPage+1);
    }
    public ArrayList<Book> pageNumCategory(String category,int pageNumber){
        if(pageNumber>0 && pageNumber <4000) {
            return findByCategory(category, pageNumber);
        }
        return null;
    }
    public ArrayList<Book> pageNumPublisher(String publisher,int pageNumber){
        if(pageNumber>0 && pageNumber <4000) {
            return findByPublisher(publisher,pageNumber);
        }
        return null;
    }
    public ArrayList<Book> pageNumAuthor(String author,int pageNumber){
        if(pageNumber>0 && pageNumber <4000) {
            return findByAuthor(author,pageNumber);
        }
        return null;
    }
    public ArrayList<Book> pageNumPublicationYear(String publicationYear,int pageNumber){
        if(pageNumber>0 && pageNumber <4000) {
            return findByPubYear(publicationYear,pageNumber);
        }
        return null;
    }
    private Book constructBook(ResultSet rs){
        Book book = new Book();
        try {
            book.setIsbn(rs.getString("ISBN"));
            book.setTitle(rs.getString("TITLE"));
            book.setPublicationYear(rs.getString("PUBLICATION_YEAR"));
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
