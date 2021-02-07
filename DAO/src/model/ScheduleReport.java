package model;

import java.util.Calendar;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class ScheduleReport {
    private int contactId;
    private int appointmentId;
    private int customerId;
    private Calendar start;
    private Calendar end;
    private String title;
    private String type;
    private String description;

    
    /** 
     * @return int
     */
    public int getContactId() {
        return contactId;
    }

    
    /** 
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    
    /** 
     * @return int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    
    /** 
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    
    /** 
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }

    
    /** 
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    
    /** 
     * @return Calendar
     */
    public Calendar getStart() {
        return start;
    }

    
    /** 
     * @param start
     */
    public void setStart(Calendar start) {
        this.start = start;
    }

    
    /** 
     * @return Calendar
     */
    public Calendar getEnd() {
        return end;
    }

    
    /** 
     * @param end
     */
    public void setEnd(Calendar end) {
        this.end = end;
    }

    
    /** 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    
    /** 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /** 
     * @return String
     */
    public String getType() {
        return type;
    }

    
    /** 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
