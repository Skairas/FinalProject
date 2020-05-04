package ua.nure.chernysh.summary_task.web.command.settings;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.entity.User;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class describe settings view command
 */
public class ViewSettingsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);

    /**
     * Method prepare settings to represent they to user
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();

        LOG.trace("Obtain user info");
        request.setAttribute("firstName", ((User)session.getAttribute("user")).getFirstName());
        request.setAttribute("lastName", ((User)session.getAttribute("user")).getLastName());

        LOG.trace("Command end");
        return Path.PAGE_SETTINGS;
    }
}
