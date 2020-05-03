package ua.nure.chernysh.SummaryTask.exception;

/**
 * This class describe AppException, main exception of application
 */
public class AppException extends  Exception {
    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }
}
