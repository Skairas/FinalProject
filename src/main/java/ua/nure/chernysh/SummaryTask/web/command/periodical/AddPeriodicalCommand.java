package ua.nure.chernysh.SummaryTask.web.command.periodical;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.constants.Regexps;
import ua.nure.chernysh.SummaryTask.db.dao.PeriodicalDAO;
import ua.nure.chernysh.SummaryTask.entity.Category;
import ua.nure.chernysh.SummaryTask.entity.Periodical;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;
import ua.nure.chernysh.SummaryTask.web.command.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * This class describe add periodical command
 */
public class AddPeriodicalCommand extends Command {
    private static final Logger LOG = Logger.getLogger(AddPeriodicalCommand.class);

    /**
     * Method make add periodical to DB move if its possible
     * <p>
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String category = request.getParameter("category");

        HttpSession session = request.getSession();
        if (name == null || price == null || category == null
                || "".equals(name) || "".equals(price) || "".equals(category)
                || !Util.validateField(name, Regexps.INFO_FIELD_REGEXP)) {
            LOG.trace("Unavailable value");
            session.setAttribute("errorMessage", Messages.ERR_EMPTY_FIELDS);
            return Path.PAGE_ADD_PERIODICAL;
        }

        int priceValue;
        try {
            priceValue = Integer.parseInt(price);
        } catch (NumberFormatException ex) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        LOG.trace("Obtain category");
        Long categoryId = getCategoryId(category, session);

        if (categoryId >= 0L) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        Periodical periodical = new Periodical();
        periodical.setName(name);
        periodical.setPrice(priceValue);
        periodical.setCategory(categoryId);

        LOG.trace("Insert periodical to DB");
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();
        periodicalDAO.createPeriodical(periodical);

        LOG.trace("Command end");
        return Path.COMMAND_PERIODICALS;
    }

    /**
     * Search category in category list
     *
     * @param category Category.
     * @param session Session.
     * @return Long category Id.
     */
    private Long getCategoryId(String category, HttpSession session) {
        List<Category> categories = (List<Category>) session.getAttribute("categories");
        Long categoryId = -1L;
        for (Category cat : categories) {
            if (cat.getName().equals(category)) {
                categoryId = cat.getParentCategoryId();
                break;
            }
        }
        return categoryId;
    }
}
