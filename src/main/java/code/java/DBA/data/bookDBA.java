package code.java.DBA.data;

import code.java.DBA.connect.PSQL;
import code.java.service.CRUD;
import code.java.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class bookDBA implements CRUD {

    private PSQL psql = new PSQL();
    private Scanner in = new Scanner(System.in);


    public ResultSet getCertain(String name) throws SQLException {
        Connection connection = psql.getConnection();
        String sql = "select * from book where title = '" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    @Override
    public List<Object> getAll() throws SQLException {
        Connection connection = psql.getConnection();

        List<Book> list = new ArrayList<>();
        String sql = "select * from book";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Book book = new Book(
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("genre"),
                    resultSet.getInt("quantity")
            );
            list.add(book);
        }


        resultSet.close();
        statement.close();
        connection.close();

        return Collections.singletonList(list);
    }

    @Override
    public void add() throws SQLException {

        Connection connection = psql.getConnection();

        System.out.println("enter book title");
        String title = in.next();
        System.out.println("enter author");
        String author = in.next();
         author = in.next();
        System.out.println("choose genre \nHistorical || Science || Roman, || Math");
        String genre = in.next();
        System.out.println("enter quantity");
        int quantity = in.nextInt();

        String sql = "insert into book (title,author,genre,quantity) values(?,?,?,?)";

        PreparedStatement preparedStatement = null;


        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, genre);
        preparedStatement.setInt(4, quantity);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();


        System.out.println("book added");
    }

    @Override
    public void delete() throws SQLException {

        Connection connection = psql.getConnection();

        System.out.println("enter title for removing");
        String title = in.next();

        String sql = "select * from book where title ='" +title + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (!resultSet.next()) {
            System.out.println("student does not founded");
            return;
        }

        System.out.println("enter 1 for confirm");
        String confirm = in.next();

        if (!confirm.equals(("1"))) {
            System.out.println("action canceled");
            return;
        }

        String sqlDeleteBook  = "delete from book where title ='" + title + "'";
        String bookSql  = "delete from history where book_title ='" + title + "'";
        Statement statementFinal  =connection.createStatement();
        statementFinal.executeUpdate(sqlDeleteBook);
        statementFinal.executeUpdate(bookSql);

        resultSet.close();
        statement.close();
        statementFinal.close();

        System.out.println("book deleted");
    }

    @Override
    public void update() throws SQLException {

        Connection connection = psql.getConnection();

        System.out.println("enter book title for updating");
        String title = in.next();

        String searchBook = "select * from book where  title ='" +title + "'";

        Statement search = connection.createStatement();
        ResultSet bookSearch  = search.executeQuery(searchBook);

        if(!bookSearch.next()){
            System.out.println("book does not founded");
            return;
        }

        System.out.println(bookSearch.getString("title") + " author : " + bookSearch.getString("author") + " quantity: " + bookSearch.getInt("quantity"));

        System.out.println("enter new title for book");
        String upTitle = in.next();
        System.out.println("enter new author");
        String author = in.next();
        System.out.println("choose new genre \nHistorical || Science || Roman, || Math");
        String genre = in.next();
        System.out.println("enter new quantity");
        int quantity  = in.nextInt();




        String SQL = "update book set title  = ? , author = ? , genre = ? , quantity = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1,upTitle);
        preparedStatement.setString(2,author);
        preparedStatement.setString(3,genre);
        preparedStatement.setInt(4,quantity);
        preparedStatement.setInt(5,bookSearch.getInt("id"));

        String updateHistory = "update history set book_title ='" +upTitle + "'" + " where book_id = "
                + bookSearch.getInt("id");


        preparedStatement.executeUpdate();


        Statement statementUpdateHistory = connection.createStatement();
        statementUpdateHistory.executeUpdate(updateHistory);


        bookSearch.close();
        preparedStatement.close();
        search.close();
        statementUpdateHistory.close();

        connection.close();
        System.out.println("book updated");

    }


}
