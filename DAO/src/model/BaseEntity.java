package model;

import java.util.Calendar;

public class BaseEntity {
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdatedBy;

    public BaseEntity(Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdatedBy) {
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
