package ua.nure.chernysh.summary_task.constants;

import ua.nure.chernysh.summary_task.entity.Periodical;

import java.util.Comparator;

/**
 * This class contains comparators set for sorting periodicals list
 */
public class Comparators {
    /**
     * Sort by name A-Z
     */
    public static final Comparator<Periodical> SORT_BY_NAME_UP = (p1, p2) -> {
        if (p1.getName().compareTo(p2.getName()) > 0) {
            return 1;
        } else if (p1.getName().compareTo(p2.getName()) < 0) {
            return -1;
        }
        return 0;
    };

    /**
     * Sort by name Z-A
     */
    public static final Comparator<Periodical> SORT_BY_NAME_DOWN = (p1, p2) -> {
        if (p1.getName().compareTo(p2.getName()) > 0) {
            return -1;
        } else if (p1.getName().compareTo(p2.getName()) < 0) {
            return 1;
        }
        return 0;
    };

    /**
     * Sort by price from cheaper
     */
    public static final Comparator<Periodical> SORT_BY_PRICE_UP = (p1, p2) -> {
        if (p1.getPrice() < p2.getPrice()) {
            return -1;
        } else if (p1.getPrice() > p2.getPrice()){
            return 1;
        }
        return 0;
    };

    /**
     * Sort by price from expensive
     */
    public static final Comparator<Periodical> SORT_BY_PRICE_DOWN = (p1, p2) -> {
        if (p1.getPrice() < p2.getPrice()) {
            return 1;
        } else if (p1.getPrice() > p2.getPrice()){
            return -1;
        }
        return 0;
    };
}
