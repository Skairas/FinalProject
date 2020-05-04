package ua.nure.chernysh.summary_task.web.command.subscription;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Messages;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.PeriodicalSubscriptionBeanDAO;
import ua.nure.chernysh.summary_task.db.dao.SubscriptionDAO;
import ua.nure.chernysh.summary_task.entity.Status;
import ua.nure.chernysh.summary_task.entity.bean.PeriodicalSubscriptionBean;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class describe subscription view command
 */
public class ViewSubscriptionCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewSubscriptionCommand.class);

    /**
     * Method prepare subscription to represent it to user
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

        HttpSession session = request.getSession();

        LOG.trace("Obtain PeriodicalSubscription --> " + subscriptionId);
        PeriodicalSubscriptionBeanDAO psbDAO = PeriodicalSubscriptionBeanDAO.getInstance();
        PeriodicalSubscriptionBean subscription =
                psbDAO.findPeriodicalSubscriptionBeanById(subscriptionId, (String) session.getAttribute("lang"));

        if (subscription == null) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        LOG.trace("Verify subscriptions");
        SubscriptionDAO subscriptionDAO = SubscriptionDAO.getInstance();
        if (Util.invalidateSubscription(subscription.getId(), subscription.getEndingDate(), subscriptionDAO)) {
            subscription.setStatusName(Status.ENDED.toString().toLowerCase());
        }

        request.setAttribute("subscription", subscription);

        LOG.trace("Command end");
        return Path.PAGE_SUBSCRIPTION;
    }
}
