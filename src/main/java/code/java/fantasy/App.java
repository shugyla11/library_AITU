package code.java.fantasy;

import java.sql.SQLException;

public interface App {
    void start() throws SQLException;

    default void exit() {
        System.out.println("App closing");
        System.exit(0);
    }
}
