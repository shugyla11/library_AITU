package code.java.DBA.data;

import code.java.DBA.connect.PSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class libraryDBA extends library {

    private Scanner in = new Scanner(System.in);
  //  private studentDAO studentDAO = new studentDAO();
    private bookDBA bookDBA = new bookDBA();
    private PSQL psql = new PSQL();

    @Override
    public void giveBook() throws SQLException {
        Connection connection = psql.getConnection();
        System.out.println("enter student name for register book");
        String name = in.nextLine();
        ResultSet resultSetStudent = studentDBA.getCertain(name);
        if (!resultSetStudent.next()) {
            System.out.println("student does not founded ");
            return;
        }
        int studentId = resultSetStudent.getInt("id");
        System.out.println("enter book title for registering");
        String title = in.nextLine();
        ResultSet resultSetBook = bookDBA.getCertain(title);
        if (!resultSetBook.next()) {
            System.out.println("book does not founded");
            return;
        }
        int bookId = resultSetBook.getInt("id");
        int quantity = resultSetBook.getInt("quantity");
        if (quantity == 0) {
            System.out.println("book does not in stock");
            return;
        }
        System.out.println("enter 1 for confirm action");
        String confirm = in.nextLine();
        if (!confirm.equals("1")) {
            System.out.println("action cancelled");
            return;
        }
        String sql = "insert into history (student_name , book_title , student_id , book_id) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(3, studentId);

        preparedStatement.setString(2, title);
        preparedStatement.setInt(4, bookId);

        preparedStatement.executeUpdate();

        String u = "update book set quantity = " + (quantity - 1) + " where id = " + bookId;
        Statement statement = connection.createStatement();
        statement.executeUpdate(u);


        resultSetBook.close();
        resultSetStudent.close();
        statement.close();
        preparedStatement.close();
        connection.close();

        System.out.println("book: " + title + " given  to : " + name);

    }

    @Override
    List<String> getStudentBooks(int studentId) throws SQLException {
       Connection connection = psql.getConnection();
       Statement statement = connection.createStatement();
       ResultSet resultSet = statement.executeQuery("select * from history where student_id = " + studentId);
       List<String> list = new ArrayList<>();
       while (resultSet.next()){
           list.add(resultSet.getString("book_title"));
       }
       return list;
    }

    @Override
    public void returnBook() throws SQLException {
        Connection connection = psql.getConnection();

        System.out.println("enter student name how wanna return book");
        String name = in.nextLine();
        ResultSet resultSetStudent = studentDBA.getCertain(name);
        if (!resultSetStudent.next()) {
            System.out.println("student does not founded ");
            return;
        }
        int studentId = resultSetStudent.getInt("id");
        System.out.println("enter book title for returning");
        String title = in.nextLine();
        ResultSet resultSetBook = bookDBA.getCertain(title);
        if (!resultSetBook.next()) {
            System.out.println("book does not founded");
            return;
        }
        int bookId = resultSetBook.getInt("id");
        int quantity = resultSetBook.getInt("quantity");
        System.out.println("enter 1 for confirm action");
        String confirm = in.nextLine();
        if (!confirm.equals("1")) {
            System.out.println("action cancelled");
            return;
        }
        String sql = "delete from history where student_id = " + studentId + " and book_id = " + bookId;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        String u = "update book set quantity = " + (quantity + 1) + " where id = " + bookId;
        Statement st= connection.createStatement();
        st.executeUpdate(u);

        resultSetBook.close();
        resultSetStudent.close();
        statement.close();
        st.close();
        connection.close();

        System.out.println("book: " + title + " returned by :" + name);




    }
}
