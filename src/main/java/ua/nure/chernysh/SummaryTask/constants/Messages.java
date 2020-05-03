package ua.nure.chernysh.SummaryTask.constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * This class contains ERROR and SUCCESS message
 * keys which can be received by user while program is running
 */
public class Messages {
    public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "dataSource.error";
    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "connectionObtain.error";
    public static final String ERR_INVALID_CONTENT = "content.error";
    public static final String ERR_BASKET_CONTENT = "basketEmpty.error";
    public static final String ERR_PERIODICAL_NOT_FOUND = "periodical.error";
    public static final String ERR_NOT_ENOUGH_FUNDS = "funds.error";
    public static final String ERR_EMPTY_FIELDS = "empty.error";
    public static final String ERR_INCORRECT_PASSWORD = "password.error";
    public static final String ERR_NO_SUCH_USER = "credentials.error";
    public static final String ERR_CONFIRMATION_PASSWORD = "confirmation.error";
    public static final String ERR_AMOUNT_MUST_BE_POSITIVE = "amount.error";
    public static final String ERR_SUBSCRIPTION_EXIST = "subscriptionExist.error";
    public static final String ERR_LOGIN_EXIST = "loginExist.error";
    public static final String ERR_PERIODICAL_NAME_EXIST = "periodicalExist.error";
    public static final String ERR_CANNOT_DELETE_PERIODICAL = "periodicalDelete.error";
    public static final String ERR_CANNOT_UPDATE_PERIODICAL = "periodicalUpdate.error";
    public static final String ERR_CANNOT_CREATE_SUBSCRIPTION = "subscriptionCreate.error";
    public static final String ERR_CANNOT_UPDATE_USER = "userUpdate.error";
    public static final String ERR_PERMISSION_DENIED = "permission.error";
    public static final String ERR_LOGIN_REQUIRED = "loginRequired.error";
    public static final String ERR_USER_IS_BANNED = "banned.error";
    public static final String ERR_PERIODICAL_IN_BASKET = "inBasket.error";


    public static Map<String, String> successMessages;

    static {
        successMessages = new TreeMap<>();

        successMessages.put("basketAdd", "basketAdd.success");
        successMessages.put("registration", "registration.success");
    }
}
