package ua.nure.chernysh.SummaryTask.db;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.exception.DBException;
import ua.nure.chernysh.SummaryTask.constants.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contain methods to work with DataSource, Connections and support types
 */
public class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DataSource ds;

    /**
     * Method initialize DataSource with parameters from context.xml
     */
    private DBManager() throws DBException {
        try {
            LOG.debug("Operation start. Initialize DataSource");
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/STDB");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    /**
     * Method return connection from ConnectionPool
     *
     * @return Connection object.
     */
    public Connection getConnection() throws DBException {
        Connection con;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    public void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    public void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }


    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    public void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    public void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }
}
