package ua.nure.chernysh.summary_task.entity;

/**
 * This class describe Periodical entity.
 * Also contain methods to obtain and set fields content
 */
public class Periodical extends Entity implements Comparable {
    private String name;
    private Integer price;
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getCategory() {
        return categoryId;
    }

    public void setCategory(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
