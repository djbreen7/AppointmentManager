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
     * Builds a Customer.
     *
     * @param result The result set to work from.
     * @param includeAuditing Indicates if auditing columns should be included.
     * @return Customer
     * @throws Exception Throws an error in case of failure.
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
     * Builds a FirstLevelDivision.
     *
     * @param result The result set to work from.
     * @param includeAuditing Indicates if auditing columns should be included.
     * @return FirstLevelDivision
     * @throws Exception Throws an error in case of failure.
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
     * Builds a Country.
     *
     * @param result The result set to work from.
     * @param includeAuditing Indicates if auditing columns should be included.
     * @return Country
     * @throws Exception Throws an error in case of failure.
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
     * Builds an Appointment.
     *
     * @param result The result set to work from.
     * @param includeAuditing Indicates if auditing columns should be included.
     * @return Appointment
     * @throws Exception Throws an error in case of failure.
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
     * Builds a Contact.
     *
     * @param result The result set to work from.
     * @return Contact
     * @throws Exception Throws an error in case of failure.
     */
    public Contact buildContactResult(ResultSet result) throws Exception {
        var contact = new Contact();
        contact.setContactId(result.getInt("Contact_ID"));
        contact.setName(result.getString("Contact_Name"));
        contact.setEmail(result.getString("Email"));

        return contact;
    }

    
    /**
     * Builds a User.
     *
     * @param result The result set to work from.
     * @param includeAuditing Indicates if auditing columns should be included.
     * @return User
     * @throws Exception Throws an error in case of failure.
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
     * Builds a Schedule Report
     *
     * @param result The result set to work from.
     * @return ScheduleReport
     * @throws Exception Throws an error in case of failure.
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
     * Builds an Appointment Summary Report
     *
     * @param result The result set to work from.
     * @return AppointmentSummaryReport
     * @throws Exception Throws an error in case of failure.
     */
    public AppointmentSummaryReport buildAppointmentSummaryReport(ResultSet result) throws Exception {
        var summary = new AppointmentSummaryReport();
        summary.setMonth(result.getString(1));
        summary.setType(result.getString("Type"));
        summary.setTotal(result.getInt(3));

        return summary;
    }

    
    /**
     * Helper for getting database auditing information.
     *
     * @param entity The entity to get auditing information for.
     * @param result The result set to work from.
     * @throws Exception Throws an error in case of failure.
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
