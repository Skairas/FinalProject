package ua.nure.chernysh.summary_task.exception;

/**
 * This class describe DBException that theow away methods which work with DB
 */
public class DBException extends AppException {
    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
