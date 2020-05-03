package ua.nure.chernysh.SummaryTask.db.dao;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Fields;
import ua.nure.chernysh.SummaryTask.db.DBManager;
import ua.nure.chernysh.SummaryTask.entity.Role;
import ua.nure.chernysh.SummaryTask.entity.User;
import ua.nure.chernysh.SummaryTask.exception.DBException;
import ua.nure.chernysh.SummaryTask.constants.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contain methods for working with user entity in DB
 */
public class UserDAO {
    private static final Logger LOG = Logger.getLogger(UserDAO.class);
    private static UserDAO instance;

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private UserDAO() {
    }

    //SQL commands for periodical entity
    private static final String SQL_FIND_USERS_USER = "SELECT * FROM users WHERE role_id=?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET "
            + "first_name=?, last_name=?, balance=?, password=?, preferred_lang=?, banned=? WHERE id=?";

    /**
     * Method obtain list of all users from DB which role equals 'user'
     *
     * @return List<User> object.
     */
    public List<User> getUsers() throws DBException {
        LOG.debug("Operation start");
        List<User> users = new ArrayList<>();

        DBManager dbManager = DBManager.getInstance();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USERS_USER);
            int i = 1;
            pstmt.setInt(i, Role.USER.ordinal());
            rs = pstmt.executeQuery();
            LOG.trace("Extract users");
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return users;
    }

    /**
     * Method obtain user instance from DB which login equals login value
     *
     * @param login Entity login value.
     * @return User object
     */
    public User findUserByLogin(String login) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            int i = 1;
            pstmt.setString(i, login);
            rs = pstmt.executeQuery();
            LOG.trace("Extract user");
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return user;
    }

    /**
     * Method obtain user instance from DB which Id equals userId value
     *
     * @param userId Entity id.
     * @return User object
     */
    public User findUserById(Long userId) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();

        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
            int i = 1;
            pstmt.setLong(i, userId);
            rs = pstmt.executeQuery();
            LOG.trace("Extract user");
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
        return user;
    }

    /**
     * Method create user instance in DB
     *
     * @param user Entity.
     */
    public void createUser(User user) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            pstmt.setString(i++, user.getLogin());
            pstmt.setString(i++, user.getPassword());
            pstmt.setString(i++, user.getFirstName());
            pstmt.setString(i++, user.getLastName());
            pstmt.setString(i++, user.getPreferredLang());
            pstmt.setLong(i++, user.getBalance());
            pstmt.setBoolean(i++, user.isBanned());
            pstmt.setInt(i, user.getRoleId());
            LOG.trace("Try to put user instance in DB");
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                    LOG.trace("User created with id --> " + user.getId());
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method update user fields in DB
     *
     * @param user Entity.
     */
    public void updateUser(User user) throws DBException {
        LOG.debug("Operation start");
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_USER);
            int i = 1;
            pstmt.setString(i++, user.getFirstName());
            pstmt.setString(i++, user.getLastName());
            pstmt.setLong(i++, user.getBalance());
            pstmt.setString(i++, user.getPassword());
            pstmt.setString(i++, user.getPreferredLang());
            pstmt.setBoolean(i++, user.isBanned());
            pstmt.setLong(i, user.getId());
            LOG.trace("Try to update fields");
            if (pstmt.executeUpdate() <= 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }
        LOG.trace("Operation end");
    }

    /**
     * Method extract user entity from ResultSet rs by his fields
     *
     * @param rs ResultSet.
     * @return User object.
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setPreferredLang(rs.getString(Fields.USER_PREFERRED_LANG));
        user.setBalance(rs.getLong(Fields.USER_BALANCE));
        user.setBanned(rs.getBoolean(Fields.USER_BANNED));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        return user;
    }
}
