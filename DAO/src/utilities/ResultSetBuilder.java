package utilities;

import model.*;

import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 * @author Daniel J BreenL
 * @version 1.0
 * @since 1.0
 */
public class ResultSetBuilder {

    
    /** 
     * @param result
     * @param includeAuditing
     * @return Customer
     * @throws Exception
     */
    public Customer buildCustomerResult(ResultSet result, boolean includeAuditing) throws Exception {
        var customer = new Customer();
        customer.setCustomerId(result.getInt("Customer_ID"));
        customer.setDivisionId(result.getInt("Division_ID"));
        customer.setName(result.getString("Customer_Name"));
        customer.setAddress(result.getString("Address"));
        customer.setPostalCode(result.getString("Postal_Code"));
        customer.setPhone(result.getString("Phone"));

        if (includeAuditing)
            getAuditing(customer, result);

        return customer;
    }

    
    /** 
     * @param result
     * @param includeAuditing
     * @return FirstLevelDivision
     * @throws Exception
     */
    public FirstLevelDivision buildDivisionResult(ResultSet result, boolean includeAuditing) throws Exception {
        var division = new FirstLevelDivision();
        division.setDivisionId(result.getInt("Division_ID"));
        division.setCountryId(result.getInt("Country_ID"));
        division.setDivision(result.getString("Division"));

        if (includeAuditing)
            getAuditing(division, result);

        return division;
    }

    
    /** 
     * @param result
     * @param includeAuditing
     * @return Country
     * @throws Exception
     */
    public Country buildCountryResult(ResultSet result, boolean includeAuditing) throws Exception {
        var country = new Country();
        country.setCountryId(result.getInt("Country_ID"));
        country.setCountry(result.getString("Country"));

        if (includeAuditing)
            getAuditing(country, result);

        return country;
    }

    
    /** 
     * @param result
     * @param includeAuditing
     * @return Appointment
     * @throws Exception
     */
    public Appointment buildAppointmentResult(ResultSet result, boolean includeAuditing) throws Exception {
        var appointment = new Appointment();
        appointment.setAppointmentId(result.getInt("Appointment_ID"));
        appointment.setCustomerId(result.getInt("Customer_ID"));
        appointment.setUserId(result.getInt("User_ID"));
        appointment.setContactId(result.getInt("Contact_ID"));
        appointment.setTitle(result.getString("Title"));
        appointment.setDescription(result.getString("Description"));
        appointment.setLocation(result.getString("Location"));
        appointment.setType(result.getString("Type"));
        appointment.setStart(CalendarUtils.fromLocalDateTime(result.getObject("Start", LocalDateTime.class)));
        appointment.setEnd(CalendarUtils.fromLocalDateTime(result.getObject("End", LocalDateTime.class)));

        if (includeAuditing)
            getAuditing(appointment, result);

        return appointment;
    }

    
    /** 
     * @param result
     * @return Contact
     * @throws Exception
     */
    public Contact buildContactResult(ResultSet result) throws Exception {
        var contact = new Contact();
        contact.setContactId(result.getInt("Contact_ID"));
        contact.setName(result.getString("Contact_Name"));
        contact.setEmail(result.getString("Email"));

        return contact;
    }

    
    /** 
     * @param result
     * @param includeAuditing
     * @return User
     * @throws Exception
     */
    public User buildUserResult(ResultSet result, boolean includeAuditing) throws Exception {
        var user = new User();
        user.setUserId(result.getInt("User_ID"));
        user.setUserName(result.getString("User_Name"));
        user.setPassword(result.getString("Password"));

        if (includeAuditing)
            getAuditing(user, result);

        return user;
    }

    
    /** 
     * @param result
     * @return ScheduleReport
     * @throws Exception
     */
    public ScheduleReport buildScheduleReport(ResultSet result) throws Exception {
        var schedule = new ScheduleReport();
        schedule.setContactId(result.getInt("Contact_ID"));
        schedule.setAppointmentId(result.getInt("Appointment_ID"));
        schedule.setCustomerId(result.getInt("Customer_ID"));
        schedule.setStart(CalendarUtils.fromLocalDateTime(result.getObject("Start", LocalDateTime.class)));
        schedule.setEnd(CalendarUtils.fromLocalDateTime(result.getObject("End", LocalDateTime.class)));
        schedule.setTitle(result.getString("Title"));
        schedule.setType(result.getString("Type"));
        schedule.setDescription(result.getString("Description"));

        return schedule;
    }

    
    /** 
     * @param result
     * @return AppointmentSummaryReport
     * @throws Exception
     */
    public AppointmentSummaryReport buildAppointmentSummaryReport(ResultSet result) throws Exception {
        var summary = new AppointmentSummaryReport();
        summary.setMonth(result.getString(1));
        summary.setType(result.getString("Type"));
        summary.setTotal(result.getInt(3));

        return summary;
    }

    
    /** 
     * @param entity
     * @param result
     * @throws Exception
     */
    private void getAuditing(BaseEntity entity, ResultSet result) throws Exception {
        entity.setCreateDate(
                CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class))
        );
        entity.setLastUpdate(
                CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class))
        );
        entity.setCreatedBy(result.getString("Created_By"));
        entity.setLastUpdatedBy(result.getString("Last_Updated_By"));
    }
}
