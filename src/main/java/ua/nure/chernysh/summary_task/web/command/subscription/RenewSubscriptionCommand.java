package ua.nure.chernysh.summary_task.web.command.subscription;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Messages;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.PeriodicalDAO;
import ua.nure.chernysh.summary_task.db.dao.SubscriptionDAO;
import ua.nure.chernysh.summary_task.db.dao.UserDAO;
import ua.nure.chernysh.summary_task.entity.Periodical;
import ua.nure.chernysh.summary_task.entity.Status;
import ua.nure.chernysh.summary_task.entity.Subscription;
import ua.nure.chernysh.summary_task.entity.User;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 * This class describe renew subscription command
 */
public class RenewSubscriptionCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RenewSubscriptionCommand.class);

    private static final Long MONTH_MILLIS = 2592000000L;

    /**
     * Method do renew subscription procedure
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        Long subscriptionId;
        try {
            subscriptionId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        LOG.trace("Obtain subscription");
        SubscriptionDAO subscriptionDAO = SubscriptionDAO.getInstance();
        Subscription subscription = subscriptionDAO.getSubscriptionByID(subscriptionId);

        LOG.trace("Obtain periodical");
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();
        Periodical periodical = periodicalDAO.getPeriodicalById(subscription.getPeriodicalId());

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        LOG.trace("Check user balance");
        if (user.getBalance() < periodical.getPrice()) {
            throw new AppException(Messages.ERR_NOT_ENOUGH_FUNDS);
        }

        renewSubscription(subscription);
        subscriptionDAO.updateSubscription(subscription);

        user.setBalance(user.getBalance() - periodical.getPrice());

        LOG.trace("Update user");
        UserDAO userDAO = UserDAO.getInstance();
        userDAO.updateUser(user);

        session.setAttribute("user", user);

        LOG.trace("Command end");
        return Path.COMMAND_SUBSCRIPTIONS;
    }

    /**
     * Do renew subscription in DB
     *
     * @param subscription Subscription entity.
     */
    private void renewSubscription(Subscription subscription) {
        LOG.trace("Set ending date");
        Date endingDate;
        if(subscription.getStatusId() == Status.ENDED.ordinal()) {
            endingDate = new Date((new java.util.Date().getTime() + MONTH_MILLIS));
        } else {
            endingDate = new Date(subscription.getEndingDate().getTime() + MONTH_MILLIS);
        }

        LOG.trace("Update subscription");
        subscription.setEndingDate(endingDate);
        subscription.setStatusId(Status.PAID.ordinal());
    }
}
