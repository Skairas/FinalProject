package ua.nure.chernysh.SummaryTask.web;

import org.apache.log4j.Logger;

import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;
import ua.nure.chernysh.SummaryTask.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class implements interface HttpServlet for processing request to the server by address 'controller'
 */
@WebServlet(name = "CommandController", urlPatterns = "/controller")
public class CommandController extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(CommandController.class);

    /**
     * Processing POST request
     *
     * @param request Request.
     * @param response Response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(process(request, response));
    }

    /**
     * Processing POST request
     *
     * @param request Request.
     * @param response Response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(process(request, response)).forward(request, response);
    }

    /**
     * Processing request: extract command and then call execute metho on command
     *
     * @param request Request.
     * @param response Response.
     * @return String object
     */
    private String process(HttpServletRequest request,
                           HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Controller starts");

        request.getSession().removeAttribute("errorMessage");
        request.getSession().removeAttribute("successMessage");

        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
            LOG.trace("Command success");
        } catch (AppException ex) {
            LOG.error(ex.getMessage(), ex);
            request.getSession().setAttribute("errorMessage", ex.getMessage());
        }
        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> " + forward);

        return forward;
    }
}
