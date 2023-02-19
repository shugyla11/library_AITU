package code.java.service;


import java.sql.SQLException;
import java.util.List;

public interface CRUD {
    List<Object> getAll() throws SQLException;
    void add() throws SQLException;
    void delete() throws SQLException;
    void update() throws SQLException;
}
