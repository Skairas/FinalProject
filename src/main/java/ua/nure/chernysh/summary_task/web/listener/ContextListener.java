package ua.nure.chernysh.summary_task.web.listener;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.NamesLists;
import ua.nure.chernysh.summary_task.db.dao.CategoryDAO;
import ua.nure.chernysh.summary_task.entity.Category;
import ua.nure.chernysh.summary_task.exception.DBException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

/**
 * This class implements interface ServletContextListener to initialize application needed content
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    /**
     * Initialize application context:
     * - set default locale
     * - set locales list
     * - set categories map
     * - set sort method list
     * - set locales decoding map
     *
     * @param sce Context event.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.debug("Start initialize context");
        ServletContext ctx = sce.getServletContext();

        LOG.trace("Set default locale --> en");
        ctx.setAttribute("defaultLocale", "en");

        LOG.trace("Set locales list");
        List<String> locales = asList(ctx.getInitParameter("locales"));
        ctx.setAttribute("locales", locales);

        ResourceBundle rs;
        Map<String, Map<String, String>> localesDecoding = new TreeMap<>();
        Map<String, List<Category>> categories = new TreeMap<>();
        for (String s : locales) {
            rs = ResourceBundle.getBundle("messages", new Locale(s));
            Map<String, String> map = new TreeMap<>();
            LOG.trace("Set locales decoding for locale --> " + s);
            for (String string : locales) {
                map.put(rs.getString(string + ".label"), string);
            }
            localesDecoding.put(s, map);
            LOG.trace("Set categories for locale --> " + s);
            try {
                categories.put(s, loadCategories(s));
            } catch (DBException e) {
                LOG.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        ctx.setAttribute("categories", categories);
        ctx.setAttribute("localesDecoding", localesDecoding);

        ctx.setAttribute("sortNames", NamesLists.SORT_METHOD);
    }

    /**
     * Represent string parameter to list
     *
     * @param str String to tokenize
     * @return List<String>
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

    /**
     * Load categories by some locale
     *
     * @param locale Language
     * @return List<Category>
     */
    private List<Category> loadCategories(String locale) throws DBException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        return categoryDAO.getCategories(locale);
    }
}
