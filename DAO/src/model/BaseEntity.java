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

    
    /** 
     * @return Calendar
     */
    public Calendar getCreateDate() {
        return createDate;
    }

    
    /** 
     * @param createDate
     */
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    
    /** 
     * @return String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    
    /** 
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    /** 
     * @return Calendar
     */
    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    
    /** 
     * @param lastUpdate
     */
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    
    /** 
     * @return String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    
    /** 
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


}
