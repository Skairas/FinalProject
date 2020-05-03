package ua.nure.chernysh.SummaryTask.constants;

/**
 * This class contains constants for work with database tables fields
 */
public class Fields {
    public static final String ENTITY_ID = "id";

    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PREFERRED_LANG = "preferred_lang";
    public static final String USER_BALANCE = "balance";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_BANNED = "banned";

    public static final String SUBSCRIPTION_USER_ID = "user_id";
    public static final String SUBSCRIPTION_PERIODICAL_ID = "periodical_id";
    public static final String SUBSCRIPTION_STATUS_ID = "status_id";
    public static final String SUBSCRIPTION_ENDING_DATE = "ending_date";

    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_NAME_TRANSLATED = "translated_category_name";
    public static final String CATEGORY_LOCALE = "locale";
    public static final String CATEGORY_CATEGORY_ID = "category_id";

    public static final String PERIODICAL_PRICE = "price";
    public static final String PERIODICAL_NAME = "periodical_name";
    public static final String PERIODICAL_CATEGORY_ID = "category_id";
}
