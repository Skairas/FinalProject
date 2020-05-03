package ua.nure.chernysh.SummaryTask.web.command.user;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.exception.AppException;
import ua.nure.chernysh.SummaryTask.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class describe command to view account info page
 */
public class ViewInfoCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ViewInfoCommand.class);

    /**
     * Method send path to account info page
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command start");
        return Path.PAGE_ACCOUNT_INFO;
    }
}
