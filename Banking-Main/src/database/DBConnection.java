package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final static String url = "jdbc:mysql://localhost:3306/SBIBanking";
    private final static String userName = "root";
    private final static String password = "#CSE2828#";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // ask jvm to load MySQL JDBC driver class
//          System.out.println("MySQL Driver Loaded Successfully...");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "MySQL JDBC Driver Not Found...", e
            ); // if jar not found
        }
    }

    public static Connection getConnection() {
            try {
                return DriverManager.getConnection(url , userName , password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}
