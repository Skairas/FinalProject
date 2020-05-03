package ua.nure.chernysh.SummaryTask.web.command.settings;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.constants.Regexps;
import ua.nure.chernysh.SummaryTask.db.dao.UserDAO;
import ua.nure.chernysh.SummaryTask.entity.Category;
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
 * This class describe settings command
 */
public class SettingsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(SettingsCommand.class);

    /**
     * Method apply setting to the account if they necessary
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        boolean updateNeeded = false;
        String field = request.getParameter("firstName");
        if (field != null && !"".equals(field) && !field.equals(user.getFirstName())
                && Util.validateField(field, Regexps.INFO_FIELD_REGEXP)) {
            LOG.trace("Change fName");
            user.setFirstName(field);
            updateNeeded = true;
        }

        field = request.getParameter("lastName");
        if (field != null && !"".equals(field) && !field.equals(user.getLastName())
                && Util.validateField(field, Regexps.INFO_FIELD_REGEXP)) {
            LOG.trace("Change lName");
            user.setLastName(field);
            updateNeeded = true;
        }

        Map<String, List<String>> localesDecoding =
                (Map<String, List<String>>) session.getServletContext().getAttribute("localesDecoding");
        Map<String, String> locales = (Map<String, String>) localesDecoding.get(session.getAttribute("lang"));

        updateNeeded = isUpdateNeeded(request, session, user, updateNeeded, locales);

        if (updateNeeded) {
            LOG.trace("Update user");
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.updateUser(user);
        }

        LOG.trace("Command end");
        return Path.COMMAND_ACCOUNT_INFO;
    }

    /**
     * SetUp language if its needed and check to update user
     *
     * @param request Request.
     * @param session Session.
     * @param user User entity.
     * @param updateNeeded Boolean value.
     * @param locales Language.
     * @return boolean Update needed,
     */
    private boolean isUpdateNeeded(HttpServletRequest request, HttpSession session,
                                   User user, boolean updateNeeded, Map<String, String> locales) {
        String field;
        String checked = request.getParameter("prefLang");
        field = request.getParameter("language");
        if (field != null && !"".equals(field)) {
            String code = locales.get(field);
            if (code != null) {
                if (checked != null && !field.equals(user.getPreferredLang())) {
                    LOG.trace("Change prefLang");
                    user.setPreferredLang(code);
                    updateNeeded = true;
                }
                if (!field.equals(session.getAttribute("lang"))) {
                    LOG.trace("Change lang");
                    session.setAttribute("lang", code);
                    List<Category> categories =
                            ((Map<String, List<Category>>) session.getServletContext().getAttribute("categories"))
                                    .get(code);
                    session.setAttribute("categories", categories);
                }
            }
        }
        return updateNeeded;
    }
}
