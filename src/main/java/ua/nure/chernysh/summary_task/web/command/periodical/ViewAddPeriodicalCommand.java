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
 * This class describe periodical view command
 */
public class ViewAddPeriodicalCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewAddPeriodicalCommand.class);

    /**
     * Method redirect user to add periodical page
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        return Path.PAGE_ADD_PERIODICAL;
    }
}
