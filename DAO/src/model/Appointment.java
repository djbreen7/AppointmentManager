package model;

import java.util.Calendar;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Appointment extends BaseEntity {
    private int appointmentId;
    private int customerId;
    private Customer customer;
    private int userId;
    private User user;
    private int contactId;
    private Contact contact;
    private String title;
    private String description;
    private String location;
    private String type;
    private Calendar start;
    private Calendar end;

    public Appointment() {
        super();
        title = "";
        description = "";
        location = "";
        type = "";
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
     * @return Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    
    /** 
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
    /** 
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    
    /** 
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    /** 
     * @return User
     */
    public User getUser() {
        return user;
    }

    
    /** 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    
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
     * @return Contact
     */
    public Contact getContact() {
        return contact;
    }

    
    /** 
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
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
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * @return String
     */
    public String getLocation() {
        return location;
    }

    
    /** 
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
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
}
