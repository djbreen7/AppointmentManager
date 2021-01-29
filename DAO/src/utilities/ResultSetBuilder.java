package utilities;

import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.sql.ResultSet;

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
        var createDate = includeAuditing ? CalendarUtils.toCalendar(result.getDate("Create_Date")) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ? CalendarUtils.toCalendar(result.getDate("Last_Update")) : null;
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
        var createDate = includeAuditing ? CalendarUtils.toCalendar(result.getDate("Create_Date")) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ? CalendarUtils.toCalendar(result.getDate("Last_Update")) : null;
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
        var createDate = includeAuditing ? CalendarUtils.toCalendar(result.getDate("Create_Date")) : null;
        var createdBy = includeAuditing ? result.getString("Created_By") : null;
        var lastUpdate = includeAuditing ? CalendarUtils.toCalendar(result.getDate("Last_Update")) : null;
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
}
