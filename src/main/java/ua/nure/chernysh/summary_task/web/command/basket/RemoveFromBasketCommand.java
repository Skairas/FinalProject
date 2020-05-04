package ua.nure.chernysh.summary_task.web.command.basket;

import org.apache.log4j.Logger;
import ua.nure.chernysh.summary_task.constants.Messages;
import ua.nure.chernysh.summary_task.constants.Path;
import ua.nure.chernysh.summary_task.db.dao.PeriodicalDAO;
import ua.nure.chernysh.summary_task.entity.Periodical;
import ua.nure.chernysh.summary_task.exception.AppException;
import ua.nure.chernysh.summary_task.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * This class describe remove from basket command
 */
public class RemoveFromBasketCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RemoveFromBasketCommand.class);

    /**
     * Method remove periodical from basket if its possible
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();

        List<Long> basket = (List<Long>) session.getAttribute("basket");

        if (basket == null) {
            LOG.trace("Basket is empty");
            session.setAttribute("errorMessage", Messages.ERR_BASKET_CONTENT);
            return Path.PAGE_BASKET;
        }

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
        editSummaryPrice(session, periodical);

        LOG.trace("Remove from list");
        basket.remove(periodicalId);

        LOG.trace("Command end");
        return Path.COMMAND_BASKET;
    }

    /**
     * Change summary price of basket
     *
     * @param session Session.
     * @param periodical Periodical.
     */
    private void editSummaryPrice(HttpSession session, Periodical periodical) {
        LOG.trace("work with summary price");
        Integer summaryPrice = (Integer) session.getAttribute("summaryPrice");
        summaryPrice -= periodical.getPrice();
        if (summaryPrice <= 0) {
            session.removeAttribute("summaryPrice");
        } else {
            session.setAttribute("summaryPrice", summaryPrice);
        }
    }
}
