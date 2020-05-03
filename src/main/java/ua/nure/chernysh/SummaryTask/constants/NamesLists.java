package ua.nure.chernysh.SummaryTask.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contain names for sort method
 */
public class NamesLists {
    public static final List<String> SORT_METHOD = new ArrayList<>();

    static{
        SORT_METHOD.add("az");
        SORT_METHOD.add("za");
        SORT_METHOD.add("min");
        SORT_METHOD.add("max");
    }
}
