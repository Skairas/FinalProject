package ua.nure.chernysh.summary_task.web.command.user;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.UserDAO;
import ua.nure.chernysh.summary_task.entity.User;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class describe command to view users list
 */
public class ViewUsersCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewUsersCommand.class);

    /**
     * Method extract users list from DB and set it to the request
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        UserDAO userDAO = UserDAO.getInstance();

        List<User> users = userDAO.getUsers();
        LOG.trace("Set list to request");
        request.setAttribute("usersList", users);

        LOG.trace("Command end");
        return Path.PAGE_USERS;
    }
}
