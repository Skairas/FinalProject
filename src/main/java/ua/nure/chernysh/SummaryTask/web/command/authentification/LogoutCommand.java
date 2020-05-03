package ua.nure.chernysh.SummaryTask.web.command.authentification;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class describe logout command
 */
public class LogoutCommand extends Command {
    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    /**
     * Method do log out procedure
     *
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command start");
        HttpSession session = request.getSession();

        LOG.trace("Invalidate session");
        session.invalidate();

        LOG.debug("Command end");
        return Path.PAGE_LOGIN;
    }
}
