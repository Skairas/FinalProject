package ua.nure.chernysh.summary_task.entity;

import java.io.Serializable;

/**
 * This class describe Entity parent class of all entities.
 * Also contain methods to obtain and set fields content
 */
public class Entity implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
