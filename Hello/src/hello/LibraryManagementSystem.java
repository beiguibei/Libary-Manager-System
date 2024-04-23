package hello;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryManagementSystem extends JFrame implements ActionListener {
    //数据库连接信息
    static final String url = "jdbc:mysql://localhost:3306/library?useSSL=false";
    static final String user = "root";
    static final String pwd = "baiyixuan233";

    //数据库连接对象
    Connection connection = DriverManager.getConnection(url,user,pwd);

    //GUI组件
    private JButton addBookButton, addAuthorButton, addBorrowerButton, addBorrowRecordButton;
    private JButton deleteBookButton, deleteAuthorButton, deleteBorrowerButton, deleteBorrowRecordButton;
    private JButton updateBookButton, updateAuthorButton, updateBorrowerButton, updateBorrowRecordButton;
    private JButton searchBookButton, searchAuthorButton, searchBorrowerButton, searchBorrowRecordButton;
    private JButton tablebookButton, tableauthorButton, tableborrowerButton, tableborrowRecordButton;
    private JTable TableBook, TableAuthor, TableBorrower, TableBorrowRecord;
    private JPanel buttonPanel, tablePanel;
    private CardLayout cardLayout;//布局管理器

    //窗体设计
    public LibraryManagementSystem() throws SQLException {
        setTitle("燕山大学图书馆管理系统");
        setSize(1400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //创建按钮区域
        buttonPanel = new JPanel(new GridLayout(4, 1));
        // 初始化按钮
        addBookButton = new JButton("添加图书");
        addAuthorButton = new JButton("添加作者");
        addBorrowerButton = new JButton("添加借阅者");
        addBorrowRecordButton = new JButton("添加借阅记录");

        deleteBookButton = new JButton("删除图书");
        deleteAuthorButton = new JButton("删除作者");
        deleteBorrowerButton = new JButton("删除借阅者");
        deleteBorrowRecordButton = new JButton("删除借阅记录");

        updateBookButton = new JButton("更新图书");
        updateAuthorButton = new JButton("更新作者");
        updateBorrowerButton = new JButton("更新借阅者");
        updateBorrowRecordButton = new JButton("更新借阅记录");

        searchBookButton = new JButton("查询图书");
        searchAuthorButton = new JButton("查询作者");
        searchBorrowerButton = new JButton("查询借阅者");
        searchBorrowRecordButton = new JButton("查询借阅记录");

        tablebookButton = new JButton("书籍表");
        tableauthorButton = new JButton("作者表");
        tableborrowerButton = new JButton("借阅者表");
        tableborrowRecordButton = new JButton("借阅记录表");

        //添加按钮点击事件监听器
        addBookButton.addActionListener(this);
        addAuthorButton.addActionListener(this);
        addBorrowerButton.addActionListener(this);
        addBorrowRecordButton.addActionListener(this);

        deleteBookButton.addActionListener(this);
        deleteAuthorButton.addActionListener(this);
        deleteBorrowerButton.addActionListener(this);
        deleteBorrowRecordButton.addActionListener(this);

        updateBookButton.addActionListener(this);
        updateAuthorButton.addActionListener(this);
        updateBorrowerButton.addActionListener(this);
        updateBorrowRecordButton.addActionListener(this);

        searchBookButton.addActionListener(this);
        searchAuthorButton.addActionListener(this);
        searchBorrowerButton.addActionListener(this);
        searchBorrowRecordButton.addActionListener(this);

        tablebookButton.addActionListener(this);
        tableauthorButton.addActionListener(this);
        tableborrowerButton.addActionListener(this);
        tableborrowRecordButton.addActionListener(this);

        //将按钮添加到窗体
        buttonPanel.add(new JLabel("添加"));
        buttonPanel.add(addBookButton);
        buttonPanel.add(addAuthorButton);
        buttonPanel.add(addBorrowerButton);
        buttonPanel.add(addBorrowRecordButton);
        buttonPanel.add(tablebookButton);

        buttonPanel.add(new JLabel("删除"));
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(deleteAuthorButton);
        buttonPanel.add(deleteBorrowerButton);
        buttonPanel.add(deleteBorrowRecordButton);
        buttonPanel.add(tableauthorButton);

        buttonPanel.add(new JLabel("修改"));
        buttonPanel.add(updateBookButton);
        buttonPanel.add(updateAuthorButton);
        buttonPanel.add(updateBorrowerButton);
        buttonPanel.add(updateBorrowRecordButton);
        buttonPanel.add(tableborrowerButton);

        buttonPanel.add(new JLabel("查找"));
        buttonPanel.add(searchBookButton);
        buttonPanel.add(searchAuthorButton);
        buttonPanel.add(searchBorrowerButton);
        buttonPanel.add(searchBorrowRecordButton);
        buttonPanel.add(tableborrowRecordButton);

        //创建卡片布局
        tablePanel = new JPanel();
        cardLayout = new CardLayout();
        tablePanel.setLayout(cardLayout);

        //创建表格并添加到卡片布局
        TableBook = new JTable();
        TableAuthor = new JTable();
        TableBorrower = new JTable();
        TableBorrowRecord = new JTable();

        //添加卡片布局
        tablePanel.add(new JScrollPane(TableBook),"书籍表");
        tablePanel.add(new JScrollPane(TableAuthor),"作者表");
        tablePanel.add(new JScrollPane(TableBorrower),"借阅者表");
        tablePanel.add(new JScrollPane(TableBorrowRecord),"借阅记录表");

        add(buttonPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    //添加图书
    private void addBook(String title, String author, String publisher, Date publicationDate, String isbn, int stock) {
        try {
            String sql = "INSERT INTO library.book (Title, Author, Publisher, PublicationDate, ISBN, Stock) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, publisher);
            pstmt.setDate(4, new java.sql.Date(publicationDate.getTime()));
            pstmt.setString(5, isbn);
            pstmt.setInt(6, stock);
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(this, "图书添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "图书添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //添加作者
    private void addAuthor(String name,String nationality,Date birthday) {
        try {
            String sql = "INSERT INTO library.author (Name, Nationality, Birthday) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, nationality);
            pstmt.setDate(3, new java.sql.Date(birthday.getTime()));
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(this, "作者添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "作者添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //添加借阅者
    private void addBorrower(String name,String contact) {
        try {
            String sql = "INSERT INTO library.borrower (name, contact) VALUES ( ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, contact);
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(this, "借阅者添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "借阅者添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //添加借阅记录
    private void addBorrowerecord(String bookid,String borrowerid,Date borrowdate,Date returndate) {
        try {
            String sql = "INSERT INTO library.borrowrecord (BookID, BorrowerID, BorrowDate, ReturnDate) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, bookid);
            pstmt.setString(2, borrowerid);
            pstmt.setDate(3, new java.sql.Date(borrowdate.getTime()));
            pstmt.setDate(4, new java.sql.Date(returndate.getTime()));
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(this, "借阅记录添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "借阅记录添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //删除图书
    private void deleteBook(int bookID) {
        try {
            String sql = "DELETE FROM library.book WHERE BookID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bookID);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "图书删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "图书不存在或删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "图书删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //删除作者
    private void deleteAuthor(int authorid) {
        try {
            String sql = "DELETE FROM library.author WHERE AuthorID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, authorid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "作者删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "作者不存在或删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "作者删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //删除借阅者
    private void deleteBorrower(int borrowerid) {
        try {
            String sql = "DELETE FROM library.borrower WHERE BorrowerID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, borrowerid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "借阅者删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "借阅不存在或删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "借阅者删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //删除借阅记录
    private void deleteBorrowRecord(int recordid) {
        try {
            String sql = "DELETE FROM library.borrowrecord WHERE RecordID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, recordid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "借阅记录删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "借阅记录不存在或删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "借阅记录删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //更新图书
    private void updateBook(int bookid ,String newtitle, String newauthor, String newpublisher, Date newpublicationdate,String newisbn,int newstock) {
        try {
            String sql = "UPDATE library.book SET Title = ?,Author = ?,Publisher = ?,PublicationDate = ?,ISBN = ?,Stock = ? WHERE BookID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newtitle);
            pstmt.setString(2, newauthor);
            pstmt.setString(3, newpublisher);
            pstmt.setDate(4, new java.sql.Date(newpublicationdate.getTime()));
            pstmt.setString(5, newisbn);
            pstmt.setInt(6, newstock);
            pstmt.setInt(7, bookid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "图书信息更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "图书不存在或更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "图书信息更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //更新作者
    private void updateAuthor(int authorid, String newname, String newnationality, Date newbirthday) {
        try {
            String sql = "UPDATE library.author SET Name = ?,Nationality = ?,Birthday = ? WHERE AuthorID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newname);
            pstmt.setString(2, newnationality);
            pstmt.setDate(3, new java.sql.Date(newbirthday.getTime()));
            pstmt.setInt(4, authorid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "作者信息更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "作者不存在或更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "作者信息更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //更新借阅者
    private void updateBorrower(int borrowerid, String newname, String newcontact) {
        try {
            String sql = "UPDATE library.borrower SET Name = ?,Contact = ? WHERE BorrowerID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newname);
            pstmt.setString(2, newcontact);
            pstmt.setInt(3, borrowerid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "借阅者信息更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "借阅者不存在或更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "借阅者信息更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //更新借阅记录
    private void updateBorrowrecord(int recordid, Date newborrowdate, Date newreturndate) {
        try {
            String sql = "UPDATE library.borrowrecord SET BorrowDate = ?, ReturnDate = ? WHERE RecordID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(newborrowdate.getTime()));
            pstmt.setDate(2, new java.sql.Date(newreturndate.getTime()));
            pstmt.setInt(3, recordid);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "借阅记录信息更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "借阅记录不存在或更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "借阅记录信息更新失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //查询图书
    private void searchBookTable(int bookid) {
        try {
            // 执行查询
            String sql = "SELECT * FROM library.book WHERE BookID = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bookid);
            ResultSet resultSet = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 添加列名到表格模型
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnName(columnIndex));
            }

            // 添加数据到表格模型
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }

            // 创建表格并设置模型
            JTable table = new JTable(tableModel);

            // 创建一个滚动窗格
            JScrollPane scrollPane = new JScrollPane(table);

            //清除之前的表
            tablePanel.removeAll();

            //添加表格到卡片容器
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            //刷新UI
            tablePanel.revalidate();
            tablePanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法显示数据库表格！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //查询作者
    private void searchAuthorTable(int authorid) {
        try {
            // 执行查询
            String sql = "SELECT * FROM library.author WHERE AuthorID = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, authorid);
            ResultSet resultSet = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 添加列名到表格模型
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnName(columnIndex));
            }

            // 添加数据到表格模型
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }

            // 创建表格并设置模型
            JTable table = new JTable(tableModel);

            // 创建一个滚动窗格
            JScrollPane scrollPane = new JScrollPane(table);

            //清除之前的表
            tablePanel.removeAll();

            //添加表格到卡片容器
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            //刷新UI
            tablePanel.revalidate();
            tablePanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法显示数据库表格！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //查询借阅者
    private void searchBorrowerTable(int borrowerid) {
        try {
            // 执行查询
            String sql = "SELECT * FROM library.borrower WHERE BorrowerID = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, borrowerid);
            ResultSet resultSet = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 添加列名到表格模型
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnName(columnIndex));
            }

            // 添加数据到表格模型
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }

            // 创建表格并设置模型
            JTable table = new JTable(tableModel);

            // 创建一个滚动窗格
            JScrollPane scrollPane = new JScrollPane(table);

            //清除之前的表
            tablePanel.removeAll();

            //添加表格到卡片容器
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            //刷新UI
            tablePanel.revalidate();
            tablePanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法显示数据库表格！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //查询借阅记录
    private void searchBorrowerrecordTable(int recordid) {
        try {
            // 执行查询
            String sql = "SELECT * FROM library.borrowrecord WHERE RecordID = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, recordid);
            ResultSet resultSet = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 添加列名到表格模型
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnName(columnIndex));
            }

            // 添加数据到表格模型
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }

            // 创建表格并设置模型
            JTable table = new JTable(tableModel);

            // 创建一个滚动窗格
            JScrollPane scrollPane = new JScrollPane(table);

            //清除之前的表
            tablePanel.removeAll();

            //添加表格到卡片容器
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            //刷新UI
            tablePanel.revalidate();
            tablePanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法显示数据库表格！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //显示表格
    private void displayDatabaseTable(String tableName) {
        try {
            // 执行查询
            String sql = "SELECT * FROM " + tableName;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 添加列名到表格模型
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnName(columnIndex));
            }

            // 添加数据到表格模型
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }

            // 创建表格并设置模型
            JTable table = new JTable(tableModel);

            // 创建一个滚动窗格
            JScrollPane scrollPane = new JScrollPane(table);

            //添加表格到卡片容器
            tablePanel.add(scrollPane,tableName);
            cardLayout.show(tablePanel, tableName);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法显示数据库表格！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 处理按钮点击事件
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBookButton) {
            // 执行添加图书的功能
            // 显示输入对话框，让用户输入图书信息
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField publisherField = new JTextField();
            JTextField publicationDateField = new JTextField();
            JTextField isbnField = new JTextField();
            JTextField stockField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("标题:"));
            panel.add(titleField);
            panel.add(new JLabel("作者:"));
            panel.add(authorField);
            panel.add(new JLabel("出版社:"));
            panel.add(publisherField);
            panel.add(new JLabel("出版日期:"));
            panel.add(publicationDateField);
            panel.add(new JLabel("ISBN:"));
            panel.add(isbnField);
            panel.add(new JLabel("stock:"));
            panel.add(stockField);

            int result = JOptionPane.showConfirmDialog(null, panel, "添加图书", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                    //获取用户输入的图书信息
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String publisher = publisherField.getText();
                    String publicationDateString = publicationDateField.getText();
                    String isbn = isbnField.getText();
                    int stock = Integer.parseInt(stockField.getText());

                    //解析日期字符串为 Date 对象
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date publicationDate = null;
                    try {
                        publicationDate = dateFormat.parse(publicationDateString);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    //调用添加图书的方法，并传递图书信息
                if (publicationDate != null) {
                    addBook(title,author,publisher,publicationDate,isbn,stock);
                }
            }
        }
        else if (e.getSource() == addAuthorButton) {
            // 执行添加作者的功能
            // 显示输入对话框，让用户输入作者信息
            JTextField nameField = new JTextField();
            JTextField nationalityField = new JTextField();
            JTextField birthdayField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("名字:"));
            panel.add(nameField);
            panel.add(new JLabel("国籍:"));
            panel.add(nationalityField);
            panel.add(new JLabel("出生日期:"));
            panel.add(birthdayField);

            int result = JOptionPane.showConfirmDialog(null, panel, "添加作者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的作者信息
                String name = nameField.getText();
                String nationality = nationalityField.getText();
                String birthdayString = birthdayField.getText();

                // 解析出生日期字符串为 Date 对象
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthday = null;
                try {
                    birthday = dateFormat.parse(birthdayString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                // 调用添加作者的方法，并传递作者信息
                if (birthday != null) {
                    addAuthor(name,nationality,birthday);
                }
            }

        }
        else if (e.getSource() == addBorrowerButton) {
            // 执行添加借阅者的功能
            // 显示输入对话框，让用户输入借阅者信息
            JTextField nameField = new JTextField();
            JTextField contactField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("借阅者名字:"));
            panel.add(nameField);
            panel.add(new JLabel("联系方式:"));
            panel.add(contactField);

            int result = JOptionPane.showConfirmDialog(null, panel, "添加借阅者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的借阅者信息
                String name = nameField.getText();
                String contact = contactField.getText();
                // 调用添加作者的方法，并传递作者信息
                addBorrower(name,contact);

            }

        }
        else if (e.getSource() == addBorrowRecordButton) {
            // 执行添加借阅记录的功能
            // 显示输入对话框，让用户输入借阅记录信息
            JTextField borrowdateField = new JTextField();
            JTextField returndateField = new JTextField();
            JTextField bookidField = new JTextField();
            JTextField borroweridField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("图书id:"));
            panel.add(bookidField);
            panel.add(new JLabel("借阅者id:"));
            panel.add(borroweridField);
            panel.add(new JLabel("借阅日期:"));
            panel.add(borrowdateField);
            panel.add(new JLabel("归还日期:"));
            panel.add(returndateField);

            int result = JOptionPane.showConfirmDialog(null, panel, "添加借阅记录", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的借阅信息
                String bookid = bookidField.getText();
                String borrowerid = borroweridField.getText();
                String borrowDateString = borrowdateField.getText();
                String returnDateString = returndateField.getText();

                // 解析日期字符串为 Date 对象
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date borrowDate = null;
                Date returnDate = null;
                try {
                    borrowDate = dateFormat.parse(borrowDateString);
                    returnDate = dateFormat.parse(returnDateString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                // 调用添加借阅记录的方法，并传递图书信息
                if (borrowDate != null && returnDate != null) {
                    addBorrowerecord(bookid,borrowerid,borrowDate,returnDate);
                }
            }

        }
        else if (e.getSource() == deleteBookButton) {
            // 执行删除图书的功能
            // 显示输入对话框，让用户输入图书id
            JTextField bookidField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("书籍id:"));
            panel.add(bookidField);

            int result = JOptionPane.showConfirmDialog(null, panel, "删除图书", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                String bookid = bookidField.getText();

                // 调用删除图书的方法，并传递图书id
                if (bookid != null) {
                    deleteBook(Integer.parseInt(bookid));
                }
            }

        }
        else if (e.getSource() == deleteAuthorButton) {
            // 执行删除作者的功能
            // 显示输入对话框，让用户输入作者id
            JTextField authoridField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("作者id:"));
            panel.add(authoridField);

            int result = JOptionPane.showConfirmDialog(null, panel, "删除作者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的作者信息
                String authorid = authoridField.getText();

                // 调用添加作者的方法，并传作者id
                if (authorid != null) {
                    deleteAuthor(Integer.parseInt(authorid));
                }
            }

        }
        else if (e.getSource() == deleteBorrowerButton) {
            // 执行删除借阅者的功能
            // 显示输入对话框，让用户输入借阅者id
            JTextField borroweridField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("借阅者id:"));
            panel.add(borroweridField);

            int result = JOptionPane.showConfirmDialog(null, panel, "删除借阅者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的借阅者信息
                String borrowerid = borroweridField.getText();

                // 调用删除借阅者的方法，并传借阅者id
                if (borrowerid != null) {
                    deleteBorrower(Integer.parseInt(borrowerid));
                }
            }

        }
        else if (e.getSource() == deleteBorrowRecordButton) {
            // 执行删除借阅记录的功能
            // 显示输入对话框，让用户输入借阅记录id
            JTextField recordidField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("借阅记录id:"));
            panel.add(recordidField);

            int result = JOptionPane.showConfirmDialog(null, panel, "删除借阅者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的借阅者信息
                String recordid = recordidField.getText();

                // 调用删除借阅者的方法，并传借阅记录id
                if (recordid != null) {
                    deleteBorrowRecord(Integer.parseInt(recordid));
                }
            }

        }
        else if (e.getSource() == updateBookButton) {
            // 执行更新图书的功能
            // 显示输入对话框，让用户输入图书信息
            JTextField bookidField = new JTextField();
            JTextField newtitleField = new JTextField();
            JTextField newauthorField = new JTextField();
            JTextField newpublisherField = new JTextField();
            JTextField newpublicationDateField = new JTextField();
            JTextField newisbnField = new JTextField();
            JTextField newstockField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要更新的图书id:"));
            panel.add(bookidField);
            panel.add(new JLabel("标题:"));
            panel.add(newtitleField);
            panel.add(new JLabel("作者:"));
            panel.add(newauthorField);
            panel.add(new JLabel("出版社:"));
            panel.add(newpublisherField);
            panel.add(new JLabel("出版日期:"));
            panel.add(newpublicationDateField);
            panel.add(new JLabel("ISBN:"));
            panel.add(newisbnField);
            panel.add(new JLabel("stock:"));
            panel.add(newstockField);

            int result = JOptionPane.showConfirmDialog(null, panel, "更新图书", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                int bookid = Integer.parseInt(bookidField.getText());
                String newtitle = newtitleField.getText();
                String newauthor = newauthorField.getText();
                String newpublisher = newpublisherField.getText();
                String newpublicationDateString = newpublicationDateField.getText();
                String newisbn = newisbnField.getText();
                int newstock = Integer.parseInt(newstockField.getText());

                // 解析日期字符串为 Date 对象
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newpublicationDate = null;
                try {
                    newpublicationDate = dateFormat.parse(newpublicationDateString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                // 调用更新图书的方法，并传递图书信息
                if (newpublicationDate != null) {
                    updateBook(bookid, newtitle, newauthor, newpublisher, newpublicationDate, newisbn, newstock);
                }
            }
        }
        else if (e.getSource() == updateAuthorButton) {
            // 执行更新作者的功能
            // 显示输入对话框，让用户输入作者信息
            JTextField authoridField = new JTextField();
            JTextField newnameField = new JTextField();
            JTextField newnationalityField = new JTextField();
            JTextField newbirthdayField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要更新的作者id:"));
            panel.add(authoridField);
            panel.add(new JLabel("名字:"));
            panel.add(newnameField);
            panel.add(new JLabel("国籍:"));
            panel.add(newnationalityField);
            panel.add(new JLabel("出生日期:"));
            panel.add(newbirthdayField);

            int result = JOptionPane.showConfirmDialog(null, panel, "更新作者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                int authorid = Integer.parseInt(authoridField.getText());
                String newname = newnameField.getText();
                String newnationality = newnationalityField.getText();
                String newbirthdayString = newbirthdayField.getText();

                // 解析日期字符串为 Date 对象
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newbirthday = null;
                try {
                    newbirthday = dateFormat.parse(newbirthdayString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                // 调用更新作者的方法，并传递作者信息
                if (newbirthday != null) {
                    updateAuthor(authorid,newname,newnationality,newbirthday);
                }
            }
        }
        else if (e.getSource() == updateBorrowerButton) {
            // 执行更新借阅者的功能
            // 显示输入对话框，让用户输入借阅者信息
            JTextField borroweridField = new JTextField();
            JTextField newnameField = new JTextField();
            JTextField newcontactField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要更新的借阅者id:"));
            panel.add(borroweridField);
            panel.add(new JLabel("名字:"));
            panel.add(newnameField);
            panel.add(new JLabel("联系方式:"));
            panel.add(newcontactField);

            int result = JOptionPane.showConfirmDialog(null, panel, "更新借阅者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的借阅者信息
                int borrowerid = Integer.parseInt(borroweridField.getText());
                String newname = newnameField.getText();
                String newcontact = newcontactField.getText();

                // 调用更新借阅者的方法，并传递借阅者信息
                if (newcontact != null) {
                    updateBorrower(borrowerid,newname,newcontact);
                }
            }

        }
        else if (e.getSource() == updateBorrowRecordButton) {
            // 执行更新借阅记录的功能
            // 显示输入对话框，让用户输入借阅记录信息
            JTextField recordidField = new JTextField();
            JTextField newborrowerdateField = new JTextField();
            JTextField newreturndataField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要更新的借阅记录id:"));
            panel.add(recordidField);
            panel.add(new JLabel("借阅日期:"));
            panel.add(newborrowerdateField);
            panel.add(new JLabel("归还日期:"));
            panel.add(newreturndataField);

            int result = JOptionPane.showConfirmDialog(null, panel, "更新借阅记录", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的借阅记录信息
                int recordid = Integer.parseInt(recordidField.getText());
                String newborrowdateString = newborrowerdateField.getText();
                String newreturndateString = newreturndataField.getText();

                // 解析日期字符串为 Date 对象
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newborrowerdate = null;
                Date newreturndate = null;
                try {
                    newborrowerdate = dateFormat.parse(newborrowdateString);
                    newreturndate = dateFormat.parse(newreturndateString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                // 调用更新借阅记录的方法，并传递借阅记录信息
                if (newborrowerdate != null && newreturndate != null) {
                    updateBorrowrecord(recordid,newborrowerdate,newreturndate);
                }
            }

        }
        else if (e.getSource() == searchBookButton) {
            // 执行查询图书的功能
            // 显示输入对话框，让用户输入图书id
            JTextField bookidField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要查询的书籍id:"));
            panel.add(bookidField);

            int result = JOptionPane.showConfirmDialog(null, panel, "查询图书", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                int bookid = Integer.parseInt(bookidField.getText());
                String Bookid = String.valueOf(bookid);

                // 调用查询图书的方法，并传递图书id
                if (Bookid != null) {
                    searchBookTable(bookid);
                }
            }

        }
        else if (e.getSource() == searchAuthorButton) {
            // 执行查询作者的功能
            // 显示输入对话框，让用户输入作者id
            JTextField authoridField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要查询的作者id:"));
            panel.add(authoridField);

            int result = JOptionPane.showConfirmDialog(null, panel, "查询作者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                int authorid = Integer.parseInt(authoridField.getText());
                String Authorid = String.valueOf(authorid);

                // 调用查询作者的方法，并传递图书id
                if (Authorid != null) {
                    searchAuthorTable(authorid);
                }
            }

        }
        else if (e.getSource() == searchBorrowerButton) {
            // 执行查询借阅者的功能
            // 显示输入对话框，让用户输入借阅者信息
            JTextField borrowerididField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要查询的借阅者id:"));
            panel.add(borrowerididField);

            int result = JOptionPane.showConfirmDialog(null, panel, "查询借阅者", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                int borrowerid = Integer.parseInt(borrowerididField.getText());
                String Borrowerid = String.valueOf(borrowerid);

                // 调用更新图书的方法，并传递图书信息
                if (Borrowerid != null) {
                    searchBorrowerTable(borrowerid);
                }
            }

        }
        else if (e.getSource() == searchBorrowRecordButton) {
            // 执行查询借阅记录的功能
            // 显示输入对话框，让用户输入借阅记录信息
            JTextField recordidField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("要查询的借阅记录id:"));
            panel.add(recordidField);

            int result = JOptionPane.showConfirmDialog(null, panel, "查询借阅记录", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) { // 用户点击了确认按钮
                // 获取用户输入的图书信息
                int recordid = Integer.parseInt(recordidField.getText());
                String Record = String.valueOf(recordid);

                // 调用查询借阅记录方法，并传递借阅记录id
                if (Record != null) {
                    searchBorrowerrecordTable(recordid);
                }
            }

        }
        else if (e.getSource() == tablebookButton) {
            //执行显示书籍表
            displayDatabaseTable("library.book");
        }
        else if (e.getSource() == tableauthorButton) {
            //执行显示作者表
            displayDatabaseTable("library.author");
        }
        else if (e.getSource() == tableborrowerButton) {
            //执行显示借阅者表
            displayDatabaseTable("library.borrower");
        }
        else if (e.getSource() == tableborrowRecordButton) {
            //执行显示借阅记录表
            displayDatabaseTable("library.borrowrecord");
        }
    }

    public static void main(String[] args) {
        // 启动图书馆管理系统
        try {
            new LibraryManagementSystem();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

