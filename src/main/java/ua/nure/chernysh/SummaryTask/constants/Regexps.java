package ua.nure.chernysh.SummaryTask.constants;

/**
 * This class contain REGEXPS for validating some inputs
 */
public class Regexps {
    public static final String LOGIN_REGEXP = "^[a-zA-Z0-9\\._\\-]{3,}$";
    public static final String PASSWORD_REGEXP = "^\\S+$";
    public static final String INFO_FIELD_REGEXP = "\\S+";
}
