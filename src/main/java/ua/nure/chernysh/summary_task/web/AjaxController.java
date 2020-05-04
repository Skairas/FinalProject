package ua.nure.chernysh.summary_task.web;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Messages;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;
import ua.nure.chernysh.summary_task.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONObject;

/**
 * This class implements interface HttpServlet for processing AJAX request to the server by address 'ajax'
 */
@WebServlet(name = "AjaxController", urlPatterns = "/ajax")
public class AjaxController extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AjaxController.class);

    /**
     * Processing POST request: extract command and then call method execute on command
     *
     * @param request Request.
     * @param response Response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("Controller starts");
        request.getSession().removeAttribute("errorMessage");
        request.getSession().removeAttribute("successMessage");

        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        try {
            command.execute(request, response);
            ResourceBundle rs = ResourceBundle.getBundle("messages",
                    new Locale((String) request.getSession().getAttribute("lang")));
            JSONObject json = new JSONObject();
            if (request.getSession().getAttribute("errorMessage") == null) {
                LOG.trace("Command success");
                json.put("successMessage", rs.getString(Messages.successMessages.get(commandName)));
            } else {
                LOG.trace("Command error");
                json.put("errorMessage", rs.getString((String) request.getSession().getAttribute("errorMessage")));
            }
            LOG.trace("Result --> " + json.toString());
            response.getWriter().print(json.toString());
        } catch (AppException ex) {
            LOG.error(ex.getMessage(), ex);
            response.getWriter().print(ex.getMessage());
        }
    }
}
