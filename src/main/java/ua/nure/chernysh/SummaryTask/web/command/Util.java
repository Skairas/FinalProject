package ua.nure.chernysh.SummaryTask.web.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contain utility methods
 */
public final class Util {
    private Util() {
    }

    /**
     * Method validate field by regexp
     *
     * @param val Validating string.
     * @param regexp Regexp to validate string.
     * @return boolean
     */
    public static boolean validateField(String val, String regexp) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(val);

        return m.matches();
    }
}
