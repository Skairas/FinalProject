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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class describe account replenish command
 */
public class ReplenishAccountCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ReplenishAccountCommand.class);

    /**
     * Method do replenish account if user input correct amount else stop executing with error.
     * If success redirect to account info command.
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();

        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.findUserById(((User) session.getAttribute("user")).getId());

        String amount = request.getParameter("amount");
        if (amount == null || "".equals(amount)) {
            throw new AppException(Messages.ERR_EMPTY_FIELDS);
        }
        LOG.trace("Amount --> " + amount);

        long replenishmentAmount;
        try {
            replenishmentAmount = Long.parseLong(amount);
        } catch (NumberFormatException ex) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        if (replenishmentAmount <= 0) {
            throw new AppException(Messages.ERR_AMOUNT_MUST_BE_POSITIVE);
        }

        LOG.trace("Change user balance --> " + user.toString());
        user.setBalance(user.getBalance() + replenishmentAmount);
        userDAO.updateUser(user);

        session.setAttribute("user", user);

        LOG.trace("Command end");
        return Path.COMMAND_ACCOUNT_INFO;
    }
}
