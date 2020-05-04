package ua.nure.chernysh.summary_task.web.command.basket;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.PeriodicalDAO;
import ua.nure.chernysh.summary_task.db.dao.SubscriptionDAO;
import ua.nure.chernysh.summary_task.entity.Periodical;
import ua.nure.chernysh.summary_task.entity.Subscription;
import ua.nure.chernysh.summary_task.entity.User;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.constants.Messages;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describe add to basket command
 */
public class AddToBasketCommand extends Command {
    private static final Logger LOG = Logger.getLogger(AddToBasketCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Execute start");
        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            LOG.trace("Parameter id is empty");
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        Long periodicalId;
        try {
            periodicalId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        LOG.trace("Check subscriptions");
        SubscriptionDAO subscriptionDAO = SubscriptionDAO.getInstance();
        List<Subscription> subscriptions =
                subscriptionDAO.getSubscriptions(((User)session.getAttribute("user")).getId());

        for (Subscription s : subscriptions) {
            if(periodicalId.equals(s.getPeriodicalId())) {
                LOG.trace("User is subscribe on this periodical");
                session.setAttribute("errorMessage", Messages.ERR_SUBSCRIPTION_EXIST);
                return Path.PAGE_ACCOUNT_INFO;
            }
        }

        LOG.trace("Obtain periodical");
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();
        Periodical periodical = periodicalDAO.getPeriodicalById(periodicalId);

        if (periodical == null) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        workWithBasket(session, periodical);

        LOG.trace("Command end");
        return Path.COMMAND_BASKET;
    }

    /**
     * Create basket if not exist and add periodical if not already exist, also increase summary price if needed
     *
     * @param session Session.
     * @param periodical Periodical.
     */
    private void workWithBasket(HttpSession session, Periodical periodical) {
        List<Long> basket = (List<Long>) session.getAttribute("basket");
        if (basket == null) {
            LOG.trace("Create basket");
            basket = new ArrayList<>();
            session.setAttribute("basket", basket);
        }

        Integer summaryPrice = (Integer) session.getAttribute("summaryPrice");
        if (summaryPrice == null) {
            LOG.trace("Create summary price");
            summaryPrice = 0;
            session.setAttribute("summaryPrice", summaryPrice);
        }

        if (basket.contains(periodical.getId())) {
            LOG.trace("Periodical already in basket");
            session.setAttribute("errorMessage", Messages.ERR_PERIODICAL_IN_BASKET);
        } else {
            basket.add(periodical.getId());
            LOG.trace("Increase summary price");
            summaryPrice += periodical.getPrice();
            session.setAttribute("summaryPrice", summaryPrice);
        }
    }
}
