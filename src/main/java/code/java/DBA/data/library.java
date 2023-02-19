package code.java.DBA.data;

import java.sql.SQLException;
import java.util.List;

public abstract class library {
    abstract void giveBook() throws SQLException;
    abstract List<String> getStudentBooks(int studentId) throws SQLException;
    abstract void returnBook() throws SQLException;
}
