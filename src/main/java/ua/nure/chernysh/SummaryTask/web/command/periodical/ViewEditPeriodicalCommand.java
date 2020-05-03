package ua.nure.chernysh.SummaryTask.web.command.periodical;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.db.dao.PeriodicalDAO;
import ua.nure.chernysh.SummaryTask.entity.Periodical;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class describe edit periodical view command
 */
public class ViewEditPeriodicalCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewEditPeriodicalCommand.class);

    /**
     * Method prepare periodical edit form to represent they to user
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        Long periodicalId;
        try {
            periodicalId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        LOG.trace("Obtain periodical");
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();
        Periodical periodical = periodicalDAO.getPeriodicalById(periodicalId);

        if (periodical == null) {
            throw new AppException(Messages.ERR_INVALID_CONTENT);
        }

        request.setAttribute("id", periodical.getId());
        request.setAttribute("name", periodical.getName());
        request.setAttribute("price", periodical.getPrice());

        LOG.trace("Command end");
        return Path.PAGE_EDIT_PERIODICAL;
    }
}
