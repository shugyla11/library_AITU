package code.java.DBA.connect;

import java.sql.Connection;
import java.sql.DriverManager;

public
class  PSQL implements database {
    @Override
    public Connection getConnection() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/library";
        try {
            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(connectionUrl, "postgres", "Shugyla2005");

            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
