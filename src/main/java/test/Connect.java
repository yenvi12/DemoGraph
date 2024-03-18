
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
     private static String DB_URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=atm;"
            + "encrypt=true;trustServerCertificate=true";
    private static String USER_NAME = "yenvi";
    private static String PASSWORD = "2201";
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    }
}
