package model;

import java.util.Calendar;

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

    public Appointment(
        int appointmentId,
        int customerId,
        Customer customer,
        int userId,
        User user,
        int contactId,
        Contact contact,
        String title,
        String description,
        String location,
        String type,
        Calendar start,
        Calendar end,
        Calendar createDate,
        String createdBy,
        Calendar lastUpdate,
        String lastUpdatedBy
    ) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);

        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.customer = customer;
        this.userId = userId;
        this.user = user;
        this.contactId = contactId;
        this.contact = contact;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }
}
