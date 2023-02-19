package code.java.service;

import code.java.models.Book;

import java.sql.SQLException;
import java.util.List;

public interface libraryService {
    List<Book> getStudentBooks(String name) throws SQLException;
    void giveBook() throws SQLException;
    void returnBook() throws SQLException;
}
