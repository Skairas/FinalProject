package ua.nure.chernysh.summary_task.web.command.subscription;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.PeriodicalSubscriptionBeanDAO;
import ua.nure.chernysh.summary_task.db.dao.SubscriptionDAO;
import ua.nure.chernysh.summary_task.entity.Status;
import ua.nure.chernysh.summary_task.entity.User;
import ua.nure.chernysh.summary_task.entity.bean.PeriodicalSubscriptionBean;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * This class describe subscriptions view command
 */
public class ViewSubscriptionsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewSubscriptionsCommand.class);

    /**
     * Method prepare subscriptions to represent they to user
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String lang = (String) session.getAttribute("lang");

        LOG.trace("Obtain PeriodicalSubscriptionList --> " + user.toString());
        PeriodicalSubscriptionBeanDAO psbDAO = PeriodicalSubscriptionBeanDAO.getInstance();
        List<PeriodicalSubscriptionBean> psbList = psbDAO.getPeriodicalSubscriptions(user.getId(), lang);

        LOG.trace("Verify subscriptions");
        SubscriptionDAO subscriptionDAO = SubscriptionDAO.getInstance();
        for (PeriodicalSubscriptionBean psBean : psbList) {
            if (Util.invalidateSubscription(psBean.getId(), psBean.getEndingDate(), subscriptionDAO)) {
                psBean.setStatusName(Status.ENDED.toString().toLowerCase());
            }
        }

        if (psbList.size() <= 0) {
            request.setAttribute("emptyList", "yes");
        }
        request.setAttribute("subscriptions", psbList);

        LOG.trace("Command end");
        return Path.PAGE_SUBSCRIPTIONS;
    }
}
