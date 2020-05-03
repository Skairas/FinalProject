package ua.nure.chernysh.SummaryTask.entity.bean;

import ua.nure.chernysh.SummaryTask.entity.Entity;

/**
 * This class describe PeriodicalSubscriptionBean for comfortable representation for user.
 * Also contain methods to obtain and set fields content
 */
public class PeriodicalSubscriptionBean extends Entity {
    private String periodicalName;
    private String categoryName;
    private String statusName;
    private String endingDate;
    private int price;

    public String getPeriodicalName() {
        return periodicalName;
    }

    public void setPeriodicalName(String periodicalName) {
        this.periodicalName = periodicalName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PeriodicalSubscriptionBean{" +
                "periodicalName='" + periodicalName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", endingDate='" + endingDate + '\'' +
                ", periodicalPrice='" + price + '\'' +
                '}';
    }
}
