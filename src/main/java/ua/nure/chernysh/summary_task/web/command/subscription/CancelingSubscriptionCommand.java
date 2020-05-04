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

public class CancelingSubscriptionCommand extends Command {
    private static final Logger LOG = Logger.getLogger(CancelingSubscriptionCommand.class);

    private static final Long DAY_MILLIS = 86400000L;

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

        if (subscription.getStatusId() == Status.ENDED.ordinal()) {
            throw new AppException(Messages.ERR_SUBSCRIPTION_END);
        }

        subscriptionDAO.deleteSubscriptionById(subscriptionId);

        LOG.trace("Obtain periodical");
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();
        Periodical periodical = periodicalDAO.getPeriodicalById(subscription.getPeriodicalId());

        LOG.trace("Calculate remeining days");
        long remainingDays = (subscription.getEndingDate().getTime() - (new java.util.Date()).getTime()) / DAY_MILLIS;

        int refundAmount = (int) ((periodical.getPrice() * remainingDays) / 30);
        LOG.trace("Refund amount --> " + refundAmount);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (refundAmount > 0) {
            LOG.trace("Update user");
            user.setBalance(user.getBalance() + refundAmount);
            UserDAO userDAO = UserDAO.getInstance();
            userDAO.updateUser(user);
            session.setAttribute("user", user);
        }

        LOG.trace("Command end");
        return Path.COMMAND_SUBSCRIPTIONS;
    }
}
