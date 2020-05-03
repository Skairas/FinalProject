package ua.nure.chernysh.SummaryTask.web.command;

import ua.nure.chernysh.SummaryTask.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class describe base class for all commands
 */
public abstract class Command implements Serializable {

    /**
     * @param request Request.
     * @param response Response.
     * @return String forward url.
     */
    public abstract String execute(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException, ServletException, AppException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }
}
