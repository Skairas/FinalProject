package ua.nure.chernysh.SummaryTask.web.command.periodical;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.constants.Regexps;
import ua.nure.chernysh.SummaryTask.db.dao.PeriodicalDAO;
import ua.nure.chernysh.SummaryTask.entity.Periodical;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;
import ua.nure.chernysh.SummaryTask.web.command.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class describe edit periodical command
 */
public class EditPeriodicalCommand extends Command {
    private static final Logger LOG = Logger.getLogger(EditPeriodicalCommand.class);

    /**
     * Method apply changes to periodical if they necessary
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

        boolean updateNeeded = isUpdateNeeded(request, periodical);

        if (updateNeeded) {
            LOG.trace("Update periodical");
            periodicalDAO.updatePeriodical(periodical);
        }

        LOG.trace("Command end");
        return Path.COMMAND_PERIODICALS;
    }

    /**
     * Check if needed update
     *
     * @param request Request.
     * @param periodical Periodical.
     * @return boolean Update needed.
     */
    private boolean isUpdateNeeded(HttpServletRequest request, Periodical periodical) throws AppException {
        boolean updateNeeded = false;
        String field = request.getParameter("name");
        if (field != null && !("".equals(field)) && !field.equals(periodical.getName())
                && Util.validateField(field, Regexps.INFO_FIELD_REGEXP)) {
            LOG.trace("Change name --> " + field);
            periodical.setName(field);
            updateNeeded = true;
        }

        field = request.getParameter("price");
        if (field != null && !"".equals(field)) {
            int price;
            try {
                price = Integer.parseInt(field);
            } catch (NumberFormatException ex) {
                throw new AppException(Messages.ERR_INVALID_CONTENT);
            }
            if (price != periodical.getPrice()) {
                LOG.trace("Change price --> " + price);
                periodical.setPrice(price);
                updateNeeded = true;
            }
        }
        return updateNeeded;
    }
}
