package ua.nure.chernysh.SummaryTask.web.command.subscription;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.db.dao.SubscriptionDAO;
import ua.nure.chernysh.SummaryTask.db.dao.UserDAO;
import ua.nure.chernysh.SummaryTask.entity.Subscription;
import ua.nure.chernysh.SummaryTask.entity.User;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describe buy subscriptions command
 */
public class BuySubscriptionsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(BuySubscriptionsCommand.class);

    private static final Long monthMillis = 2592000000L;

    /**
     * Method do buying subscriptions procedure
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();

        LOG.trace("Obtain basket");
        List<Long> basket = (List<Long>) session.getAttribute("basket");

        if (basket == null) {
            return Path.COMMAND_BASKET;
        }

        LOG.trace("Obtain user");
        UserDAO userDAO = UserDAO.getInstance();
        User user = (User) session.getAttribute("user");
        Long id = user.getId();
        user = userDAO.findUserById(id);

        LOG.trace("Check balance");
        Integer summaryPrice = (Integer) session.getAttribute("summaryPrice");
        if (user.getBalance() < summaryPrice) {
            throw new AppException(Messages.ERR_NOT_ENOUGH_FUNDS);
        }

        List<Subscription> subscriptionList = prepareSubscriptions(session, basket);

        LOG.trace("Create subscriptions");
        SubscriptionDAO subscriptionDAO = SubscriptionDAO.getInstance();
        subscriptionDAO.createSubscriptions(subscriptionList);

        LOG.trace("Update user");
        user.setBalance(user.getBalance() - summaryPrice);
        userDAO.updateUser(user);

        session.setAttribute("user", user);

        session.removeAttribute("basket");
        session.removeAttribute("summaryPrice");

        LOG.trace("Command end");
        return Path.COMMAND_SUBSCRIPTIONS;
    }

    /**
     * Prepare subscriptions list before buying
     *
     * @param session Session.
     * @param basket List of periodicals.
     * @return List<Subscription> subscriptions list.
     */
    private List<Subscription> prepareSubscriptions(HttpSession session, List<Long> basket) {
        LOG.trace("Make ending date");
        Date endingDate = new Date((new java.util.Date()).getTime());
        endingDate.setTime(endingDate.getTime() + monthMillis);

        LOG.trace("Prepare subscription list");
        Long userId = ((User) session.getAttribute("user")).getId();
        List<Subscription> subscriptionList = new ArrayList<>();
        for (Long id : basket) {
            Subscription subscription = new Subscription();
            subscription.setUserId(userId);
            subscription.setPeriodicalId(id);
            subscription.setStatusId(1);
            subscription.setEndingDate(endingDate);

            subscriptionList.add(subscription);
        }
        return subscriptionList;
    }
}
