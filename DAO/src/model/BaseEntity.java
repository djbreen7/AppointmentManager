package model;

import java.util.Calendar;

/**
 * A class for entities to extend when they require create/update information.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class BaseEntity {
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdatedBy;

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
