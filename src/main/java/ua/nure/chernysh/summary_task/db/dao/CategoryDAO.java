package ua.nure.chernysh.summary_task.db.dao;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Fields;
import ua.nure.chernysh.summary_task.db.DBManager;
import ua.nure.chernysh.summary_task.entity.Category;
import ua.nure.chernysh.summary_task.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contain methods for working with category entity in DB
 */
public class CategoryDAO {
    private static final Logger LOG = Logger.getLogger(CategoryDAO.class);

    private static CategoryDAO instance;

    public static synchronized CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    private CategoryDAO() {
    }

    //SQL commands for category entity
    private static final String SQL_FIND_CATEGORIES_BY_LOCALE = "SELECT * FROM categories_translated WHERE locale=?";

    /**
     * Method obtain category entities from db by locale
     *
     * @param locale Language.
     * @return List<Category> object
     */
    public List<Category> getCategories(String locale) throws DBException {
        LOG.debug("Operation start");
        List<Category> categories = new ArrayList<>();

        DBManager dbManager = DBManager.getInstance();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.trace("Obtain connection");
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_CATEGORIES_BY_LOCALE);
            pstmt.setString(1, locale);
            rs = pstmt.executeQuery();
            LOG.trace("Extract categories for locale --> " + locale);
            while (rs.next()) {
                categories.add(extractCategory(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            dbManager.close(con, pstmt, rs);
        }

        LOG.trace("Operation end");
        return categories;
    }

    /**
     * Method extract category entity from ResultSet rs by his fields
     *
     * @param rs ResultSet.
     * @return Category object.
     */
    private Category extractCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong(Fields.ENTITY_ID));
        category.setLocale(rs.getString(Fields.CATEGORY_LOCALE));
        category.setName(rs.getString(Fields.CATEGORY_NAME_TRANSLATED));
        category.setParentCategoryId(rs.getLong(Fields.CATEGORY_CATEGORY_ID));
        return category;
    }
}
