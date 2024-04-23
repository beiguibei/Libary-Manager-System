package hello;

import java.sql.*;

public class LibraryDatabase {
    //数据库连接信息
    static final String url = "jdbc:mysql://localhost:3306/library?useSSL=false";
    static final String user = "root";
    static final String pwd = "baiyixuan233";

    public static void main(String[] args) {
        try {
            //打开数据库连接
            Connection conn = DriverManager.getConnection(url,user, pwd);

            //创建图书表
            createBookTable(conn);

            //创建作者表
            createAuthorTable(conn);

            //创建借阅者表
            createBorrowerTable(conn);

            //创建借阅记录表
            createBorrowRecordTable(conn);

            //关闭数据库连接
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //创建作者表  id 名字 国籍 出生日期
    private static void createAuthorTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Author (" +
                "AuthorID INT AUTO_INCREMENT PRIMARY KEY," +
                "Name VARCHAR(255) NOT NULL," +
                "Nationality VARCHAR(255)," +
                "Birthday DATE)";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    //创建图书表  id 书名 作者 出版社 出版日期 ISBN索引
    private static void createBookTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Book (" +
                "BookID INT AUTO_INCREMENT PRIMARY KEY," +
                "Title VARCHAR(255) NOT NULL," +
                "Author VARCHAR(255) NOT NULL," +
                "Publisher VARCHAR(255)," +
                "PublicationDate DATE," +
                "ISBN VARCHAR(13)," +
                "Stock INT DEFAULT 0)";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    //创建借阅者表  id 名字  联系方法
    private static void createBorrowerTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS Borrower (" +
                "BorrowerID INT AUTO_INCREMENT PRIMARY KEY," +
                "Name VARCHAR(255) NOT NULL," +
                "Contact VARCHAR(255))";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    //创建借阅记录表  借阅id  书籍id  借阅id  借阅日期 归还日期
    private static void createBorrowRecordTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS BorrowRecord (" +
                "RecordID INT AUTO_INCREMENT PRIMARY KEY," +
                "BookID INT," +
                "BorrowerID INT," +
                "BorrowDate DATE," +
                "ReturnDate DATE," +
                "FOREIGN KEY (BookID) REFERENCES library.book(BookID) ON DELETE CASCADE," +
                "FOREIGN KEY (BorrowerID) REFERENCES library.borrower(BorrowerID) ON DELETE CASCADE )";
        stmt.executeUpdate(sql);
        stmt.close();
    }
}
