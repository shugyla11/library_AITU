package code.java.it;

import code.java.fantasy.Application;
import code.java.fantasy.ApplicationStarter;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Application application = new ApplicationStarter();
        application.start();
    }
}