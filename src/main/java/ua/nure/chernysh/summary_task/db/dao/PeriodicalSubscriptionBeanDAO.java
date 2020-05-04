package ua.nure.chernysh.summary_task.db.dao;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Fields;
import ua.nure.chernysh.summary_task.db.DBManager;
import ua.nure.chernysh.summary_task.entity.Status;
import ua.nure.chernysh.summary_task.entity.bean.PeriodicalSubscriptionBean;
import ua.nure.chernysh.summary_task.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contain methods for working with PeriodicalSubscriptionBean entity in DB
 */
public class PeriodicalSubscriptionBeanDAO {
    private static final Logger LOG = Logger.getLogger(PeriodicalSubscriptionBeanDAO.class);

    private static PeriodicalSubscriptionBeanDAO instance;

    public static synchronized PeriodicalSubscriptionBeanDAO getInstance() {
        if (instance == null) {
            instance = new PeriodicalSubscriptionBeanDAO();
        }
        return instance;
    }

    private PeriodicalSubscriptionBeanDAO() {
    }

    //SQL commands for PeriodicalSubscriptionBean entity
    private static final String SQL_FIND_PERIODICAL_SUBSCRIPTION_BEANS_BY_USER =
            "SELECT s.id,p.periodical_name,c.translated_category_name,s.ending_date,s.status_id,p.price " +
                    "FROM subscriptions s,periodicals p,categories_translated c " +
                    "WHERE s.user_id=? AND s.periodical_id=p.id AND c.category_id=p.category_id AND c.locale=?";
    private static final String SQL_FIND_PERIODICAL_SUBSCRIPTION_BEAN_BY_ID =
            "SELECT s.id,p.periodical_name,c.translated_category_name,s.ending_date,s.status_id,p.price " +
                    "FROM subscriptions s,periodicals p,categories_translated c " +
                    "WHERE s.id=? AND s.periodical_id=p.id AND c.category_id=p.category_id AND c.locale=?";

    /**
     * Method extract PeriodicalSubscriptionBeans from DB for user with Id userId and for some locale
     *
     * @param userId Entity user id.
     * @param locale Language.
     * @return List<PeriodicalSubscriptionBean> object.
     */
    public List<PeriodicalSubscriptionBean> getPeriodicalSubscriptions(Long userId, String locale) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        List<PeriodicalSubscriptionBean> periodicalSubscriptionBeanList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PERIODICAL_SUBSCRIPTION_BEANS_BY_USER);
            int i = 1;
            pstmt.setLong(i++, userId);
            pstmt.setString(i, locale);
            rs = pstmt.executeQuery();
            LOG.trace("Extract beans");
            while (rs.next()) {
                periodicalSubscriptionBeanList.add(extractPeriodicalSubscriptionBean(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return periodicalSubscriptionBeanList;
    }

    /**
     * Method extract PeriodicalSubscriptionBeans from DB with for subscription with Id subscriptionId and for some locale
     *
     * @param subscriptionId Entity id.
     * @param locale Language.
     * @return PeriodicalSubscriptionBean object
     */
    public PeriodicalSubscriptionBean findPeriodicalSubscriptionBeanById(Long subscriptionId, String locale) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        PeriodicalSubscriptionBean psBean = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PERIODICAL_SUBSCRIPTION_BEAN_BY_ID);
            int i = 1;
            pstmt.setLong(i++, subscriptionId);
            pstmt.setString(i, locale);
            rs = pstmt.executeQuery();
            LOG.trace("Extract bean for Id --> " + subscriptionId);
            if (rs.next()) {
                psBean = extractPeriodicalSubscriptionBean(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return psBean;
    }

    /**
     * Method extract PeriodicalSubscriptionBean entity from ResultSet rs by his fields
     *
     * @param rs ResultSet.
     * @return PeriodicalSubscriptionBean object.
     */
    private PeriodicalSubscriptionBean extractPeriodicalSubscriptionBean(ResultSet rs) throws SQLException {
        PeriodicalSubscriptionBean psBean = new PeriodicalSubscriptionBean();
        psBean.setId(rs.getLong(Fields.ENTITY_ID));
        psBean.setPeriodicalName(rs.getString(Fields.PERIODICAL_NAME));
        psBean.setCategoryName(rs.getString(Fields.CATEGORY_NAME_TRANSLATED));
        psBean.setEndingDate(rs.getString(Fields.SUBSCRIPTION_ENDING_DATE));
        psBean.setStatusName(Status.values()[rs.getInt(Fields.SUBSCRIPTION_STATUS_ID)].getName());
        psBean.setPrice(rs.getInt(Fields.PERIODICAL_PRICE));
        return psBean;
    }
}
