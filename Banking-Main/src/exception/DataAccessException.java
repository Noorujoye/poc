package exception;

import java.sql.SQLException;

public class DataAccessException extends Exception {
    public DataAccessException(String failedToFetchAccount, SQLException e) {
    }
}
