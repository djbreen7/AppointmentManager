package utilities;

import model.*;

import java.sql.ResultSet;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class ResultSetBuilder {

    public ResultSetBuilder() {

    }

    public Customer buildCustomerResult(ResultSet result, boolean includeAuditing) throws Exception {

        var customerId = result.getInt("Customer_ID");
        var divisionId = result.getInt("Division_ID");
        var name = result.getString("Customer_Name");
        var address = result.getString("Address");
        var postalCode = result.getString("Postal_Code");
        var phone = result.getString("Phone");
        var createDate = includeAuditing ? CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class)) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ? CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class)) : null;
        var lastUpdatedBy = includeAuditing ? result.getString("Last_Updated_By") : null;

        return new Customer(
                customerId,
                divisionId,
                null,
                name,
                address,
                postalCode,
                phone,
                createDate,
                createdBy,
                lastUpdate,
                lastUpdatedBy
        );
    }

    public FirstLevelDivision buildDivisionResult(ResultSet result, boolean includeAuditing) throws Exception {
        var divisionId = result.getInt("Division_ID");
        var countryId = result.getInt("Country_ID");
        var division = result.getString("Division");
        var createDate = includeAuditing ? CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class)) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ? CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class)) : null;
        var lastUpdatedBy = includeAuditing ? result.getString("Last_Updated_By") : null;

        return new FirstLevelDivision(
                divisionId,
                countryId,
                null,
                division,
                createDate,
                createdBy,
                lastUpdate,
                lastUpdatedBy
        );
    }

    public Country buildCountryResult(ResultSet result, boolean includeAuditing) throws Exception {
        var countryId = result.getInt("Country_ID");
        var country = result.getString("Country");
        var createDate = includeAuditing ? CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class)) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ? CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class)) : null;
        var lastUpdatedBy = includeAuditing ? result.getString("Last_Updated_By") : null;

        return new Country(
                countryId,
                country,
                createDate,
                createdBy,
                lastUpdate,
                lastUpdatedBy
        );
    }

    public Appointment buildAppointmentResult(ResultSet result, boolean includeAuditing) throws Exception {
        var appointmentId = result.getInt("Appointment_ID");
        var customerId = result.getInt("Customer_ID");
        var userId = result.getInt("User_ID");
        var contactId = result.getInt("Contact_ID");
        var title = result.getString("Title");
        var description = result.getString("Description");
        var location = result.getString("Location");
        var type = result.getString("Type");
        var start = CalendarUtils.fromLocalDateTime(result.getObject("Start", LocalDateTime.class));
        var end = CalendarUtils.fromLocalDateTime(result.getObject("End", LocalDateTime.class));
        var createDate = includeAuditing ?
                CalendarUtils.fromLocalDateTime(result.getObject("Create_Date", LocalDateTime.class)) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ?
                CalendarUtils.fromLocalDateTime(result.getObject("Last_Update", LocalDateTime.class)) : null;
        var lastUpdatedBy = includeAuditing ? result.getString("Last_Updated_By") : null;

        return new Appointment(
                appointmentId,
                customerId,
                null,
                userId,
                null,
                contactId,
                null,
                title,
                description,
                location,
                type,
                start,
                end,
                createDate,
                createdBy,
                lastUpdate,
                lastUpdatedBy
        );
    }

    public Contact buildContactResult(ResultSet result) throws Exception {
        var contactId = result.getInt("Contact_ID");
        var name = result.getString("Contact_Name");
        var email = result.getString("Email");

        return new Contact(contactId, name, email);
    }
}
