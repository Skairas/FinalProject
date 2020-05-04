package ua.nure.chernysh.summary_task.entity;

/**
 * This enum contain set of available subscription status
 */
public enum Status {
    ENDED, PAID;

    public String getName() {
        return name().toLowerCase();
    }
}
