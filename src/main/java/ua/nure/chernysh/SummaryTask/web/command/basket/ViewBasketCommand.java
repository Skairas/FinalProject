package ua.nure.chernysh.SummaryTask.web.command.basket;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describe basket view command
 */
public class ViewBasketCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewBasketCommand.class);

    /**
     * Method prepare basket to represent they to user
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        HttpSession session = request.getSession();

        List<Long> basket = (List<Long>) session.getAttribute("basket");

        if (basket == null || basket.isEmpty()) {
            LOG.trace("Basket empty");
            session.setAttribute("errorMessage", Messages.ERR_BASKET_CONTENT);
            return Path.PAGE_BASKET;
        }

        LOG.trace("Obtain periodicals");
        List<Periodical> periodicals = new ArrayList<>();
        PeriodicalDAO periodicalDAO = PeriodicalDAO.getInstance();
        for (Long l : basket) {
            Periodical periodical = periodicalDAO.getPeriodicalById(l);
            periodicals.add(periodical);
        }

        request.setAttribute("periodicals", periodicals);

        LOG.trace("Command end");
        return Path.PAGE_BASKET;
    }
}
