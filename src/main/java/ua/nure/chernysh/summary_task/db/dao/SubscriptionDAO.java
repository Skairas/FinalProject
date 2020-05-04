package ua.nure.chernysh.summary_task.db.dao;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Fields;
import ua.nure.chernysh.summary_task.constants.Messages;
import ua.nure.chernysh.summary_task.db.DBManager;
import ua.nure.chernysh.summary_task.entity.Subscription;
import ua.nure.chernysh.summary_task.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contain methods for working with subscription entity in DB
 */
public class SubscriptionDAO {
    private static final Logger LOG = Logger.getLogger(SubscriptionDAO.class);

    private static SubscriptionDAO instance;

    public static synchronized SubscriptionDAO getInstance() {
        if (instance == null) {
            instance = new SubscriptionDAO();
        }
        return instance;
    }

    private SubscriptionDAO() {
    }

    //SQL commands for PeriodicalSubscriptionBean entity
    private static final String SQL_FIND_SUBSCRIPTIONS_BY_USER = "SELECT * FROM subscriptions WHERE user_id=?";
    private static final String SQL_FIND_SUBSCRIPTION_BY_ID = "SELECT * FROM subscriptions WHERE id=?";
    private static final String SQL_INSERT_SUBSCRIPTION = "INSERT INTO subscriptions VALUES (DEFAULT, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_SUBSCRIPTION = "UPDATE subscriptions SET ending_date=?, status_id=? WHERE id=?";
    private static final String SQL_DELETE_PERIODICAL = "DELETE FROM subscriptions WHERE id=?";

    /**
     * Method obtain list of all subscription from DB for user with id userId
     *
     * @param userId Entity user id.
     * @return List<Subscription> object
     */
    public List<Subscription> getSubscriptions(Long userId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        List<Subscription> subscriptions = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_SUBSCRIPTIONS_BY_USER);
            int i = 1;
            pstmt.setLong(i, userId);
            rs = pstmt.executeQuery();
            LOG.trace("Extract subscriptions for user --> " + userId);
            while (rs.next()) {
                subscriptions.add(extractSubscription(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return subscriptions;
    }

    /**
     * Method obtain subscription by subscriptionId
     *
     * @param subscriptionId Entity id.
     * @return Subscription object
     */
    public Subscription getSubscriptionByID(Long subscriptionId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        Subscription subscription = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_SUBSCRIPTION_BY_ID);
            int i = 1;
            pstmt.setLong(i, subscriptionId);
            rs = pstmt.executeQuery();
            LOG.trace("Extract subscription by Id --> " + subscriptionId);
            if (rs.next()) {
                subscription = extractSubscription(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return subscription;
    }

    /**
     * Method insert set of subscription from subscriptionList using transaction
     *
     * @param subscriptionList List of subscription.
     */
    public void createSubscriptions(List<Subscription> subscriptionList) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            LOG.trace("Try to put subscription in DB");
            for (Subscription sub : subscriptionList) {
                createSubscription(sub, con, dbManager);
            }
            con.commit();
        } catch (SQLException ex) {
            LOG.trace("Error occurred --> " + ex.getMessage() + ". Rollback transaction");
            dbManager.rollback(con);
            throw new DBException(Messages.ERR_CANNOT_CREATE_SUBSCRIPTION, ex);
        } finally {
            dbManager.close(con);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method insert one subscription using connection. Implements transaction part
     *
     * @param subscription Entity.
     * @param con          Connection.
     * @param dbManager    DBManager.
     */
    private void createSubscription(Subscription subscription, Connection con, DBManager dbManager) throws SQLException {
        LOG.debug("Operation start");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            pstmt.setLong(i++, subscription.getUserId());
            pstmt.setLong(i++, subscription.getPeriodicalId());
            pstmt.setInt(i++, subscription.getStatusId());
            pstmt.setString(i, subscription.getEndingDate().toString());
            LOG.trace("Try to put subscription in DB");
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    subscription.setId(rs.getLong(1));
                    LOG.trace("New subscription Id --> " + subscription.getId());
                }
            }
        } finally {
            dbManager.close(rs);
            dbManager.close(pstmt);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method update subscription entity in DB
     *
     * @param subscription Entity.
     */
    public void updateSubscription(Subscription subscription) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_SUBSCRIPTION);
            int i = 1;
            pstmt.setString(i++, subscription.getEndingDate().toString());
            pstmt.setInt(i++, subscription.getStatusId());
            pstmt.setLong(i, subscription.getId());
            LOG.trace("Try to update subscription with Id --> " + subscription.getId());
            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    public void deleteSubscriptionById(Long subscriptionId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_PERIODICAL);
            int i = 1;
            pstmt.setLong(i, subscriptionId);
            LOG.trace("Try to delete periodical with id --> " + subscriptionId);
            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_SUBSCRIPTION, ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method extract subscription entity from ResultSet rs by his fields
     *
     * @param rs ResultSet.
     * @return Subscription object.
     */
    private Subscription extractSubscription(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(rs.getLong(Fields.ENTITY_ID));
        subscription.setUserId(rs.getLong(Fields.SUBSCRIPTION_USER_ID));
        subscription.setPeriodicalId(rs.getLong(Fields.SUBSCRIPTION_PERIODICAL_ID));
        subscription.setEndingDate(Date.valueOf(rs.getString(Fields.SUBSCRIPTION_ENDING_DATE)));
        subscription.setStatusId(rs.getInt(Fields.SUBSCRIPTION_STATUS_ID));
        return subscription;
    }
}
