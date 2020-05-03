package ua.nure.chernysh.SummaryTask.web.command.user;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.db.dao.UserDAO;
import ua.nure.chernysh.SummaryTask.entity.User;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class describe user banning command
 */
public class BanCommand extends Command {
    private static final Logger LOG = Logger.getLogger(BanCommand.class);

    /**
     * Method do account banning procedure
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        UserDAO userDAO = UserDAO.getInstance();

        String userId = request.getParameter("uId");

        if (userId == null || "".equals(userId)) {
            throw new AppException("userId = " + userId);
        }

        LOG.debug("Obtain user");
        User user = userDAO.findUserById(Long.parseLong(userId));

        String userBan = request.getParameter("ban");

        if(userBan == null || "".equals(userBan)) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        boolean ban = Boolean.parseBoolean(userBan);
        if (user.isBanned() != ban) {
            LOG.trace("Change user fields");
            user.setBanned(ban);
            userDAO.updateUser(user);
        }

        LOG.trace("Command end");
        return Path.COMMAND_USERS;
    }
}
