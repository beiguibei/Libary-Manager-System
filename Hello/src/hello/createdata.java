package hello;

import java.sql.*;
import java.util.Date;

public class createdata {
    //数据库连接信息
    static final String DB_URL = "jdbc:mysql://localhost:3306/library?useSSL=false";
    static final String USER = "root";
    static final String PASS = "baiyixuan233";

    public static void main(String[] args) {
        try {
            //打开数据库连接
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //添加示例图书
            addBook(conn, "数据结构", "吴伟民", "清华大学出版社", new Date(2004,9,10), "9780451524935", 10);
            addBook(conn, "数据库系统", "施伯乐", "高等教育出版社", new Date(2003,9,8), "9780061120084", 8);

            //添加示例作者
            addAuthor(conn, "吴伟民", "中国", new Date(1903, 6, 25));
            addAuthor(conn, "施伯乐", "中国", new Date(1926, 3, 28));

            //添加示例借阅者
            addBorrower(conn, "张三", "1234567");
            addBorrower(conn, "李四", "7654321");

            //添加示例借阅记录
            //borrowBook(conn, 7, 7, new Date()); //张三借阅了数据结构
            //borrowBook(conn, 8, 8, new Date()); //李四借阅了数据库

            //关闭数据库连接
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加图书
    private static void addBook(Connection conn, String title, String author, String publisher, Date publicationDate, String isbn, int stock) throws SQLException {
        String sql = "INSERT INTO library.book (Title, Author, Publisher, PublicationDate, ISBN, Stock) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, publisher);
        pstmt.setDate(4, new java.sql.Date(publicationDate.getTime()));
        pstmt.setString(5, isbn);
        pstmt.setInt(6, stock);
        pstmt.executeUpdate();
        pstmt.close();
    }

    //添加作者
    private static void addAuthor(Connection conn, String name, String nationality, Date birthday) throws SQLException {
        String sql = "INSERT INTO library.author (Name, Nationality, Birthday) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, nationality);
        pstmt.setDate(3, new java.sql.Date(birthday.getTime()));
        pstmt.executeUpdate();
        pstmt.close();
    }

    //添加借阅者
    private static void addBorrower(Connection conn, String name, String contact) throws SQLException {
        String sql = "INSERT INTO library.borrower (Name, Contact) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, contact);
        pstmt.executeUpdate();
        pstmt.close();
    }

    //借阅图书
    private static void borrowBook(Connection conn, int bookID, int borrowerID, Date borrowDate) throws SQLException {
        String sql = "INSERT INTO library.borrowrecord (BookID, BorrowerID, BorrowDate) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookID);
        pstmt.setInt(2, borrowerID);
        pstmt.setDate(3, new java.sql.Date(borrowDate.getTime()));
        pstmt.executeUpdate();
        pstmt.close();
    }
}
