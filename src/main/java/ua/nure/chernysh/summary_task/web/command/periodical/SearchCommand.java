package ua.nure.chernysh.summary_task.web.command.periodical;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class describe search command
 */
public class SearchCommand extends Command {
    private static final Logger LOG = Logger.getLogger(SearchCommand.class);

    /**
     * Method forming query for view periodicals command
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        String searchQuery = request.getParameter("searchQuery");
        String category = request.getParameter("category");
        String sort = request.getParameter("sort");

        String url = Path.COMMAND_PERIODICALS;
        if (searchQuery != null) {
            LOG.trace("Search query --> " + searchQuery);
            url += "&searchQuery=" + searchQuery;
        }

        if (category != null) {
            LOG.trace("Category --> " + category);
            url += "&category=" + category;
        }

        if (sort != null) {
            LOG.trace("Sort method --> " + sort);
            url += "&sort=" + sort;
        }

        LOG.trace("Command end");
        return url;
    }
}
