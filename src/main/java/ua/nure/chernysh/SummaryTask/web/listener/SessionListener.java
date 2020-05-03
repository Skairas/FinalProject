package ua.nure.chernysh.SummaryTask.web.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * This class implements interface HttpSessionListener to initialize session with default settings
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOG = Logger.getLogger(SessionListener.class);

    /**
     * Initialize session with default lang from application context
     *
     * @param se Session event.
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.debug("Session created");
        HttpSession session = se.getSession();
        ServletContext ctx = session.getServletContext();

        LOG.trace("Session set lang variable");
        session.setAttribute("lang", ctx.getAttribute("defaultLocale"));
    }
}
