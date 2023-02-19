package code.java.DBA.data;

import code.java.models.Student;
import code.java.DBA.connect.PSQL;
import code.java.service.CRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class studentDBA implements CRUD {

    private static PSQL psql = new PSQL();
    private Scanner in = new Scanner(System.in);
    private libraryDBA libraryDBA = new libraryDBA();


    public static ResultSet getCertain(String name) throws SQLException {
        Connection connection = psql.getConnection();
        String sql = "select * from student where name ='" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    @Override
    public List<Object> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();


        String sql = "SELECT * FROM student";
        Connection connection = psql.getConnection();
        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Student student = new Student(rs.getInt("barcode"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    (rs.getString("gruppa")),
                    libraryDBA.getStudentBooks(rs.getInt("id"))
            );
            students.add(student);
        }

        rs.close();
        st.close();
        connection.close();

        return Collections.singletonList(students);
    }

    @Override
    public void add() throws SQLException {
        Connection connection = psql.getConnection();
        System.out.println("Enter student barcode example [221642] ");
        int barcode = in.nextInt();
        System.out.println("Enter student name");
        String name = in.next();
        System.out.println("enter age");
        int age = in.nextInt();
        System.out.println("choose group \nComputer science || SoftwareEngineering || Big Data Analysis || Media Technologies");
        String group = in.next();


        String sql = "insert into student (barcode,name,age,gruppa) values(?,?,?,?)";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, barcode);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, age);
        preparedStatement.setString(4, group);


        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        System.out.println("student added");

    }

    @Override
    public void delete() throws SQLException {
        Connection connection = psql.getConnection();
        System.out.println("enter student name for removing");
        String name = in.next();
        String sql = "select * from student where name ='" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            System.out.println("student does not founded");
            return;
        }
        System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        System.out.println("enter 1 for confirm process");
        String confirm = in.next();
        if (!confirm.equals("1")) {
            System.out.println("action  cancelled");
            return;
        }


        Statement statementDeleteStudent = connection.createStatement();
        statementDeleteStudent.executeUpdate("delete from student where id = " + resultSet.getInt("id"));
        Statement statementDeleteHistory = connection.createStatement();
        statementDeleteHistory.executeUpdate("delete from history where student_name  ='" + name + "'");


        System.out.println("student removed");


        resultSet.close();
        statement.close();
        statementDeleteHistory.close();
        statementDeleteStudent.close();
        connection.close();

    }

    @Override
    public void update() throws SQLException {
        Connection connection = psql.getConnection();

        System.out.println("enter student name for updating ");
        String searchName = in.next();
        String sql = "select * from student where name ='" + searchName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) {
            System.out.println("student does not founded");
            return;
        }
        System.out.println("enter new barcode");
        int barcode = in.nextInt();
        System.out.println("enter new name ");
        String name = in.next();
        System.out.println("enter new age");
        int age = in.nextInt();
        System.out.println("choose group 1.Computer science || 2.SoftwareEngineering || 3.Big Data Analysis || 4.Media Technologies");
        String group = in.next();

        String SQL = "update student set barcode = " + barcode + " ,  name ='" + name + "'" + " ,  age = "
                + age + " where id = " + resultSet.getInt("id");

        String sqlUpdate = "update student set gruppa  ='" + group + "'" + " where id = " +
                resultSet.getInt("id");

        String sqlUpdateHistory = "update history set student_name  ='" + name + "'" + " where student_id =" +
                resultSet.getInt("id");


        Statement STATEMENT = connection.createStatement();
        STATEMENT.executeUpdate(sqlUpdateHistory);
        Statement statementUPD = connection.createStatement();
        statementUPD.executeUpdate(sqlUpdate);
        statement.executeUpdate(SQL);


        STATEMENT.close();
        statementUPD.close();
        statement.close();
        resultSet.close();
        connection.close();


        System.out.println("student successfully updated ");
    }
}
