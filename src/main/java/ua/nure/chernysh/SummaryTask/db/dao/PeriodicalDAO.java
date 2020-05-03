package ua.nure.chernysh.SummaryTask.db.dao;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Fields;
import ua.nure.chernysh.SummaryTask.db.DBManager;
import ua.nure.chernysh.SummaryTask.entity.Periodical;
import ua.nure.chernysh.SummaryTask.entity.User;
import ua.nure.chernysh.SummaryTask.exception.DBException;
import ua.nure.chernysh.SummaryTask.constants.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contain methods for working with periodical entity in DB
 */
public class PeriodicalDAO {
    private static final Logger LOG = Logger.getLogger(PeriodicalDAO.class);

    private static PeriodicalDAO instance;

    public static synchronized PeriodicalDAO getInstance() {
        if (instance == null) {
            instance = new PeriodicalDAO();
        }
        return instance;
    }

    private PeriodicalDAO() {
    }

    //SQL commands for periodical entity
    private static final String SQL_FIND_PERIODICALS = "SELECT * FROM periodicals";
    private static final String SQL_FIND_PERIODICALS_BY_CATEGORY = "SELECT * FROM periodicals WHERE category_id=?";
    private static final String SQL_FIND_PERIODICALS_BY_NAME_PART = "SELECT * FROM periodicals WHERE periodical_name REGEXP ?";
    private static final String SQL_FIND_PERIODICALS_BY_NAME_PART_IN_CATEGORY = "SELECT * FROM periodicals WHERE periodical_name REGEXP ? AND category_id=?";
    private static final String SQL_FIND_PERIODICAL_BY_ID = "SELECT * FROM periodicals WHERE id=?";
    private static final String SQL_UPDATE_PERIODICAL = "UPDATE periodicals SET periodical_name=?, price=? WHERE id=?";
    private static final String SQL_DELETE_PERIODICAL = "DELETE FROM periodicals WHERE id=?";
    private static final String SQL_INSERT_PERIODICAL = "INSERT INTO periodicals VALUES (DEFAULT, ?, ?, ?)";

    /**
     * Method obtain list of all periodicals from DB
     *
     * @return List<Periodical> object
     */
    public List<Periodical> getPeriodicals() throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        List<Periodical> periodicals = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_PERIODICALS);
            LOG.trace("Extract periodicals");
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, stmt, rs);
        }
        LOG.trace("Operation end");
        return periodicals;
    }

    /**
     * Method obtain list of periodicals by some categoryId
     *
     * @param categoryId Entity category id.
     * @return List<Periodical> object
     */
    public List<Periodical> getPeriodicalsByCategory(long categoryId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        List<Periodical> periodicals = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PERIODICALS_BY_CATEGORY);
            int i = 1;
            pstmt.setLong(i, categoryId);
            rs = pstmt.executeQuery();
            LOG.trace("Extract periodicals");
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return periodicals;
    }

    /**
     * Method obtain periodicals by part of his name searchQuery
     *
     * @param searchQuery Search query.
     * @return List<Periodical> object
     */
    public List<Periodical> getPeriodicalsByNamePart(String searchQuery) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        List<Periodical> periodicals = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PERIODICALS_BY_NAME_PART);
            int i = 1;
            pstmt.setString(i, ".*" + searchQuery + ".*");
            rs = pstmt.executeQuery();
            LOG.trace("Extract periodicals");
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return periodicals;
    }

    /**
     * Method obtain periodicals by part of his name searchQuery in some category
     *
     * @param searchQuery Search query.
     * @param categoryId  Entity category id.
     * @return List<Periodical> object
     */
    public List<Periodical> getPeriodicalsByNamePartInCategory(String searchQuery, long categoryId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        List<Periodical> periodicals = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PERIODICALS_BY_NAME_PART_IN_CATEGORY);
            int i = 1;
            pstmt.setString(i++, ".*" + searchQuery + ".*");
            pstmt.setLong(i, categoryId);
            rs = pstmt.executeQuery();
            LOG.trace("Extract periodicals");
            while (rs.next()) {
                periodicals.add(extractPeriodical(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            LOG.trace("Operation end");
            dbManager.close(con, pstmt, rs);
        }

        return periodicals;
    }

    /**
     * Method obtain periodical by periodicalId
     *
     * @param periodicalId Entity id.
     * @return Periodical object
     */
    public Periodical getPeriodicalById(Long periodicalId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        Periodical periodical = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PERIODICAL_BY_ID);
            int i = 1;
            pstmt.setLong(i, periodicalId);
            rs = pstmt.executeQuery();
            LOG.trace("Extract periodical");
            if (rs.next()) {
                periodical = extractPeriodical(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return periodical;
    }

    /**
     * Method create periodical instance in DB
     *
     * @param periodical Instance of entity.
     */
    public void createPeriodical(Periodical periodical) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_PERIODICAL, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            pstmt.setString(i++, periodical.getName());
            pstmt.setInt(i++, periodical.getPrice());
            pstmt.setLong(i, periodical.getCategory());
            LOG.trace("Try to put instance in DB");
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    periodical.setId(rs.getLong(1));
                    LOG.trace("New periodical Id --> " + periodical.getId());
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_PERIODICAL_NAME_EXIST, ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method update periodical fields in DB
     *
     * @param periodical Instance of entity.
     */
    public void updatePeriodical(Periodical periodical) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_PERIODICAL);
            int i = 1;
            pstmt.setString(i++, periodical.getName());
            pstmt.setInt(i++, periodical.getPrice());
            pstmt.setLong(i, periodical.getId());
            LOG.trace("Try to update fields");
            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_PERIODICAL, ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method delete periodical from DB
     *
     * @param periodicalId Entity id.
     */
    public void deletePeriodical(Long periodicalId) throws DBException {
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
            pstmt.setLong(i, periodicalId);
            LOG.trace("Try to delete periodical with id --> " + periodicalId);
            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_PERIODICAL, ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method extract periodical entity from ResultSet rs by his fields
     *
     * @param rs ResultSet.
     * @return Periodical object
     */
    private Periodical extractPeriodical(ResultSet rs) throws SQLException {
        Periodical periodical = new Periodical();
        periodical.setId(rs.getLong(Fields.ENTITY_ID));
        periodical.setName(rs.getString(Fields.PERIODICAL_NAME));
        periodical.setPrice(rs.getInt(Fields.PERIODICAL_PRICE));
        periodical.setCategory(rs.getLong(Fields.PERIODICAL_CATEGORY_ID));
        return periodical;
    }
}
