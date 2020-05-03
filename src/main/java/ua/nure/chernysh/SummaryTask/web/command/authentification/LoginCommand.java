package ua.nure.chernysh.SummaryTask.web.command.authentification;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.constants.Regexps;
import ua.nure.chernysh.SummaryTask.db.dao.UserDAO;
import ua.nure.chernysh.SummaryTask.entity.Category;
import ua.nure.chernysh.SummaryTask.entity.Role;
import ua.nure.chernysh.SummaryTask.entity.User;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;
import ua.nure.chernysh.SummaryTask.web.command.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class describe login command
 */
public class LoginCommand extends Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    /**
     * Method do log in procedure for user using received credentials
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");

        HttpSession session = request.getSession();

        UserDAO userDAO = UserDAO.getInstance();
        String login = request.getParameter("login");

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            session.setAttribute("errorMessage", Messages.ERR_EMPTY_FIELDS);
            return Path.PAGE_LOGIN;
        }

        LOG.trace("Obtain user");
        User user = userDAO.findUserByLogin(login);

        if (user == null) {
            session.setAttribute("errorMessage", Messages.ERR_NO_SUCH_USER);
            return Path.PAGE_LOGIN;
        }

        if (user.isBanned()) {
            throw new AppException(Messages.ERR_USER_IS_BANNED);
        }

        if (!password.equals(user.getPassword())) {
            session.setAttribute("errorMessage", Messages.ERR_INCORRECT_PASSWORD);
            return Path.PAGE_LOGIN;
        }

        Role userRole = Role.getRole(user);
        LOG.trace("userRole --> " + userRole);


        session.setAttribute("user", user);
        session.setAttribute("userRole", userRole);
        session.setAttribute("lang", user.getPreferredLang());

        List<Category> categories =
                ((Map<String, List<Category>>) session.getServletContext().getAttribute("categories"))
                        .get(user.getPreferredLang());
        session.setAttribute("categories", categories);

        LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        LOG.debug("Command end");
        return Path.COMMAND_PERIODICALS;
    }
}


