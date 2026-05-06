package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final static String url = "jdbc:mysql://localhost:3306/SBIBanking";
    private final static String userName = "root";
    private final static String password = "#CSE2828#";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url , userName , password);
    }
}
