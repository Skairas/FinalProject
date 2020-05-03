package ua.nure.chernysh.SummaryTask.web.command.authentification;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.constants.Regexps;
import ua.nure.chernysh.SummaryTask.db.dao.UserDAO;
import ua.nure.chernysh.SummaryTask.entity.User;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.exception.DBException;
import ua.nure.chernysh.SummaryTask.web.command.Command;
import ua.nure.chernysh.SummaryTask.web.command.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class describe registration command
 */
public class RegistrationCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    /**
     * Method do register user if its possible
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command start");

        String login = request.getParameter("login");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String password = request.getParameter("password");
        String confPassword = request.getParameter("conf_password");

        HttpSession session = request.getSession();

        if (login == null || password == null || confPassword == null
                || login.isEmpty() || password.isEmpty() || confPassword.isEmpty()
                || !Util.validateField(login, Regexps.LOGIN_REGEXP)
                || !Util.validateField(password, Regexps.PASSWORD_REGEXP)
                || !Util.validateField(confPassword, Regexps.PASSWORD_REGEXP)) {
            LOG.trace("Invalid received parameters");
            session.setAttribute("errorMessage", Messages.ERR_EMPTY_FIELDS);
            return Path.PAGE_REGISTRATION;
        }

        if (!password.equals(confPassword)) {
            LOG.trace("Not equals passwords");
            session.setAttribute("errorMessage", Messages.ERR_CONFIRMATION_PASSWORD);
            return Path.PAGE_REGISTRATION;
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoleId(1);

        LOG.trace("Try to insert user in DB");
        UserDAO userDAO = UserDAO.getInstance();
        try {
            userDAO.createUser(user);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            session.setAttribute("errorMessage", Messages.ERR_LOGIN_EXIST);
            return Path.PAGE_REGISTRATION;
        }

        session.setAttribute("successMessage", Messages.successMessages.get("registration"));

        LOG.trace("Command end");
        return Path.PAGE_LOGIN;
    }
}
