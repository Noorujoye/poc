package database;

import exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static final String url = "jdbc:mysql://localhost:3306/SBIBANKING";
    private final static String userName = System.getenv("DB_USER");
    private final static String password = System.getenv("DB_PASSWORD");

    static {
        try {
            if (url == null || userName == null || password == null) {
                throw new RuntimeException("Environment variables are not set!!! Please check...");
            }
            Class.forName("com.mysql.cj.jdbc.Driver"); // ask jvm to load MySQL JDBC driver class
            logger.info("MySQL Driver Loaded Successfully");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL DRIVER NOT found", e);
            throw new RuntimeException(
                    "MySQL JDBC Driver Not Found...", e
            ); // if jar not found
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url , userName , password);
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database", e);
        }
    }
}
