package ua.nure.chernysh.summary_task.entity;

import java.sql.Date;

/**
 * This class describe Subscription entity.
 * Also contain methods to obtain and set fields content
 */
public class Subscription extends Entity {
    private Long userId;
    private Long periodicalId;
    private int statusId;
    private Date endingDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(Long periodicalId) {
        this.periodicalId = periodicalId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "userId=" + userId +
                ", periodicalId=" + periodicalId +
                ", statusId=" + statusId +
                '}';
    }
}
