package ua.nure.chernysh.summary_task.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * This class describe pages encoding format
 */
public class EncodingFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);

    private String encoding;

    public void destroy() {
    }

    /**
     *  Set up request encoding to default server encoding format
     *
     * @param req Request.
     * @param resp Response.
     * @param chain Filter chain.
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        LOG.debug("Filter start");
        String requestEncoding = req.getCharacterEncoding();
        if (requestEncoding == null) {
            LOG.trace("Set request encoding --> " + encoding);
            req.setCharacterEncoding(encoding);
        }

        chain.doFilter(req, resp);
        LOG.trace("Filter end");
    }

    /**
     * Initialize encoding variable from initial parameters
     *
     * @param config Filter configuration
     */
    public void init(FilterConfig config) throws ServletException {
        LOG.debug("Init start");
        encoding = config.getInitParameter("encoding");
        LOG.trace("Default encoding set up --> " + encoding);
        LOG.trace("Init end");
    }

}
