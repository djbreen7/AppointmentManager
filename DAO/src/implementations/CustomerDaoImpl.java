package implementations;

import calendar.CalendarUtils;
import data.DatabaseConnection;
import interfaces.CustomerDao;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public List<Customer> getAllCustomers() {
        String query = String.format(
                "SELECT c.Customer_ID " +
                        ",c.Customer_Name " +
                        ",c.Address " +
                        ",c.Postal_Code " +
                        ",c.Phone " +
                        ",d.Division_ID " +
                        ",d.Division " +
                        ",ctry.Country_ID " +
                        ",ctry.Country " +
                        "FROM customers c " +
                        "JOIN first_level_divisions d ON d.Division_ID = c.Division_ID " +
                        "JOIN countries ctry ON ctry.Country_ID = d.Country_ID;");
        List<Customer> customers = new ArrayList();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var customer = buildCustomerResult(result, false);
                customer.setDivision(buildDivisionResult(result, false));
                customer.getDivision().setCountry(buildCountryResult(result, false));
                customers.add(customer);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return customers;
        }
    }

    @Override
    public Customer getCustomer(int customerId) {
        String query = String.format(
                "SELECT c.Customer_ID " +
                        ",c.Customer_Name " +
                        ",c.Address " +
                        ",c.Postal_Code " +
                        ",c.Phone " +
                        ",c.Create_Date " +
                        ",c.Created_By " +
                        ",c.Last_Update " +
                        ",c.Last_Updated_By " +
                        ",d.Division_ID " +
                        ",d.Division " +
                        ",ctry.Country_ID " +
                        ",ctry.Country " +
                        "FROM customers c " +
                        "JOIN first_level_divisions d ON d.Division_ID = c.Division_ID " +
                        "JOIN countries ctry ON ctry.Country_ID = d.Country_ID " +
                        "WHERE Customer_ID = %s;", customerId);
        Customer customer = null;
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                customer = buildCustomerResult(result, true);
                customer.setDivision(buildDivisionResult(result, false));
                customer.getDivision().setCountry(buildCountryResult(result, false));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return customer;
        }
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(int customerId) {

    }

    private Customer buildCustomerResult(ResultSet result, boolean includeAuditing) throws Exception {
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

    private FirstLevelDivision buildDivisionResult(ResultSet result, boolean includeAuditing) throws Exception {
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

    private Country buildCountryResult(ResultSet result, boolean includeAuditing) throws Exception {
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
