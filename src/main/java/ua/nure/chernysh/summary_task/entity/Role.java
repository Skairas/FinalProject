package ua.nure.chernysh.summary_task.entity;

/**
 * This enum contains set of available user roles
 */
public enum Role {
    ADMIN, USER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
