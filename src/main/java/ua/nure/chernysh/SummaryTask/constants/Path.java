package ua.nure.chernysh.SummaryTask.constants;

/**
 * This class contains PATH to the all pages
 * and string which define some GET commands which used in work
 */
public class Path {
    public static final String PAGE_ERROR_PAGE = "error_page.jsp";
    public static final String PAGE_REGISTRATION = "registration.jsp";
    public static final String PAGE_LOGIN = "login.jsp";
    public static final String PAGE_USERS = "WEB-INF/jsp/users.jsp";
    public static final String PAGE_PERIODICALS = "WEB-INF/jsp/periodicals.jsp";
    public static final String PAGE_BASKET = "WEB-INF/jsp/basket.jsp";
    public static final String PAGE_PERIODICAL = "WEB-INF/jsp/periodical.jsp";
    public static final String PAGE_EDIT_PERIODICAL = "WEB-INF/jsp/editPeriodical.jsp";
    public static final String PAGE_ADD_PERIODICAL = "WEB-INF/jsp/addPeriodical.jsp";
    public static final String PAGE_SETTINGS = "WEB-INF/jsp/settings.jsp";
    public static final String PAGE_ACCOUNT_INFO = "WEB-INF/jsp/accountInfo.jsp";
    public static final String PAGE_SUBSCRIPTION = "WEB-INF/jsp/subscription.jsp";
    public static final String PAGE_SUBSCRIPTIONS = "WEB-INF/jsp/subscriptions.jsp";
    public static final String PAGE_REPLENISH = "WEB-INF/jsp/replenish.jsp";

    public static final String COMMAND_USERS = "controller?command=users";
    public static final String COMMAND_PERIODICALS = "controller?command=periodicals";
    public static final String COMMAND_SUBSCRIPTIONS = "controller?command=subscriptions";
    public static final String COMMAND_BASKET = "controller?command=basket";
    public static final String COMMAND_ACCOUNT_INFO = "controller?command=info";
}
