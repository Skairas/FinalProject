package ua.nure.chernysh.SummaryTask.web.filter;

import org.apache.log4j.Logger;
import ua.nure.chernysh.SummaryTask.constants.Messages;
import ua.nure.chernysh.SummaryTask.constants.Path;
import ua.nure.chernysh.SummaryTask.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * This class describe security filter for requests to servlets
 */
public class SecurityFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);

    private Map<Role, List<String>> accessMap = new TreeMap<>();
    private List<String> commonCommand;
    private List<String> outOfControl;

    public void destroy() {
    }

    /**
     * Method allowed or denied permission for users according to user role and destination command
     *
     * @param request Request.
     * @param response Response.
     * @param chain Filter chain.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOG.debug("Filter start");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = request.getParameter("command");

        if (outOfControl.contains(commandName)) {
            LOG.trace("Out of control");
            chain.doFilter(request, response);
        } else {
            HttpSession session = httpRequest.getSession();

            Role userRole = (Role) session.getAttribute("userRole");
            if (userRole == null) {
                LOG.trace("User not exist");
                session.setAttribute("errorMessage", Messages.ERR_LOGIN_REQUIRED);
                request.getRequestDispatcher(Path.PAGE_LOGIN).forward(request, response);
            } else {
                if (accessMap.get(userRole).contains(commandName)
                        || commonCommand.contains(commandName)) {
                    LOG.trace(commandName + "access allowed");
                    chain.doFilter(request, response);
                } else {
                    LOG.trace(commandName + " access denied");
                    session.setAttribute("errorMessage", Messages.ERR_PERMISSION_DENIED);
                    request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
                }
            }
        }
        LOG.debug("Filter end");
    }

    /**
     * Initialize commands lists
     *
     * @param config Filter config.
     */
    public void init(FilterConfig config) throws ServletException {
        LOG.debug("Security filter init");
        accessMap.put(Role.ADMIN, asList(config.getInitParameter("adminCommand")));
        LOG.trace("Admin commands set up");
        accessMap.put(Role.USER, asList(config.getInitParameter("userCommand")));
        LOG.trace("User commands set up");
        commonCommand = asList(config.getInitParameter("commonCommand"));
        LOG.trace("Common commands set up");
        outOfControl = asList(config.getInitParameter("outOfControl"));
        LOG.trace("outOfControl commands set up");
        LOG.trace("Security filter init end");
    }

    /**
     * Represent string parameter to list
     *
     * @param str String to tokenize
     * @return List<String>
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}
