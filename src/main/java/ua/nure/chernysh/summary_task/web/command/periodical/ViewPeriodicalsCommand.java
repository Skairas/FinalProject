package ua.nure.chernysh.summary_task.web.command.periodical;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Comparators;
import ua.nure.chernysh.summary_task.constants.NamesLists;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.PeriodicalDAO;
import ua.nure.chernysh.summary_task.entity.Periodical;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * This class describe periodicals view command
 */
public class ViewPeriodicalsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewPeriodicalsCommand.class);

    private static Map<String, Comparator<Periodical>> sortComparators = new TreeMap<>();

    static {
        sortComparators.put(NamesLists.SORT_METHOD.get(0), Comparators.SORT_BY_NAME_UP);
        sortComparators.put(NamesLists.SORT_METHOD.get(1), Comparators.SORT_BY_NAME_DOWN);
        sortComparators.put(NamesLists.SORT_METHOD.get(2), Comparators.SORT_BY_PRICE_UP);
        sortComparators.put(NamesLists.SORT_METHOD.get(3), Comparators.SORT_BY_PRICE_DOWN);
    }

    /**
     * Method prepare periodicals to represent they to user
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();

        String searchQuery = request.getParameter("searchQuery");
        String category = request.getParameter("category");
        String sort = request.getParameter("sort");

        LOG.trace("Obtain periodicals");
        List<Periodical> periodicals;
        if ((category == null || "".equals(category)) && (searchQuery == null || "".equals(searchQuery))) {
            periodicals = periodicalDAO.getPeriodicals();
        } else {
            if (category == null || "".equals(category)) {
                periodicals = periodicalDAO.getPeriodicalsByNamePart(searchQuery);
            } else if (searchQuery == null || "".equals(searchQuery)) {
                periodicals = periodicalDAO.getPeriodicalsByCategory(Long.parseLong(category));
            } else {
                periodicals = periodicalDAO.getPeriodicalsByNamePartInCategory(searchQuery, Long.parseLong(category));
            }
        }

        if (sort == null || "".equals(sort) || !NamesLists.SORT_METHOD.contains(sort)) {
            sort = "az";
        }

        LOG.trace("Sorting periodical list");
        periodicals.sort(sortComparators.get(sort));

        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("category", category);
        request.setAttribute("sort", sort);

        if (periodicals.size() <= 0) {
            request.setAttribute("emptyList", "yes");
        }
        request.setAttribute("periodicals", periodicals);

        LOG.trace("Command end");
        return Path.PAGE_PERIODICALS;
    }
}
