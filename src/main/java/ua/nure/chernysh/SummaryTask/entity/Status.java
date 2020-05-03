package ua.nure.chernysh.SummaryTask.entity;

/**
 * This enum contain set of available subscription status
 */
public enum Status {
    ENDED, PAID;

    public String getName() {
        return name().toLowerCase();
    }
}
