package ua.nure.chernysh.SummaryTask.web.command.subscription;

import ua.nure.chernysh.SummaryTask.db.dao.SubscriptionDAO;
import ua.nure.chernysh.SummaryTask.entity.Status;
import ua.nure.chernysh.SummaryTask.entity.Subscription;
import ua.nure.chernysh.SummaryTask.exception.DBException;

import java.util.Date;

/**
 * This class contain utility methods
 */
public final class Util {
    private Util() {
    }

    /**
     * This method verify subscription status
     *
     * @param id Entity id.
     * @param endingDate Ending date.
     * @return boolean
     */
    public static boolean invalidateSubscription(Long id, String endingDate, SubscriptionDAO subscriptionDAO) throws DBException {
        Date date = new Date();
        if (date.after(java.sql.Date.valueOf(endingDate))) {
            Subscription subscription = subscriptionDAO.getSubscriptionByID(id);
            subscription.setStatusId(Status.ENDED.ordinal());
            subscriptionDAO.updateSubscription(subscription);

            return true;
        }
        return false;
    }
}
