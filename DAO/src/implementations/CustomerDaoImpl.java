package implementations;

import dao.CustomerDao;
import data.DatabaseConnection;
import model.Customer;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for working with Customers in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class CustomerDaoImpl implements CustomerDao {
    private final ResultSetBuilder resultSetBuilder;

    public CustomerDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }


    /**
     * Update or insert a customer into the database.
     *
     * @param customer The customer to update or insert.
     */
    @Override
    public void upsertCustomer(Customer customer) {
        if (customer.getCustomerId() == 0) {
            addCustomer(customer);
            return;
        }
        updateCustomer(customer);
    }

    
    /**
     * Add a customer into the database.
     *
     * @param customer The customer to add.
     */
    private void addCustomer(Customer customer) {
        String query = String.format(
                "INSERT INTO customers " +
                        "(Customer_Name, Phone, Address, Postal_Code, Division_ID, Created_By, Last_Updated_By) " +
                        "VALUES ('%s', '%s', '%s', '%s', %s, '%s', '%s')",
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getPostalCode(),
                customer.getDivisionId(),
                customer.getCreatedBy(),
                customer.getLastUpdatedBy()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    
    /**
     * Update a customer in the database.
     *
     * @param customer The customer to update.
     */
    private void updateCustomer(Customer customer) {
        String query = String.format(
                "UPDATE customers " +
                        "SET Customer_Name = '%s', " +
                        "Phone = '%s', " +
                        "Address = '%s', " +
                        "Postal_Code = '%s', " +
                        "Division_ID = %s, " +
                        "Last_Updated_By = '%s' " +
                        "WHERE Customer_ID = %s",
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getPostalCode(),
                customer.getDivisionId(),
                customer.getLastUpdatedBy(),
                customer.getCustomerId()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }


    /**
     * Get all customers from the database.
     *
     * @return
     */
    @Override
    public List<Customer> getAllCustomers() {
        String query = "SELECT c.Customer_ID, " +
                "c.Customer_Name, " +
                "c.Address, " +
                "c.Postal_Code, " +
                "c.Phone, " +
                "d.Division_ID, " +
                "d.Division, " +
                "ctry.Country_ID, " +
                "ctry.Country " +
                "FROM customers c " +
                "JOIN first_level_divisions d ON d.Division_ID = c.Division_ID " +
                "JOIN countries ctry ON ctry.Country_ID = d.Country_ID;";
        List<Customer> customers = new ArrayList<>();
        try {
            DatabaseConnection.makeConnection();
            var statement = DatabaseConnection.connection.createStatement();
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var customer = resultSetBuilder.buildCustomerResult(resultSet, false);
                customer.setDivision(resultSetBuilder.buildDivisionResult(resultSet, false));
                customer.getDivision().setCountry(resultSetBuilder.buildCountryResult(resultSet, false));
                customers.add(customer);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return customers;
        }
    }


    /**
     * Get a customer by PK Customer ID from the database.
     *
     * @param customerId The Customer ID to match.
     * @return
     */
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
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                customer = resultSetBuilder.buildCustomerResult(resultSet, true);
                customer.setDivision(resultSetBuilder.buildDivisionResult(resultSet, false));
                customer.getDivision().setCountry(resultSetBuilder.buildCountryResult(resultSet, false));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            return customer;
        }
    }


    /**
     * Delete a customer by PK Customer ID from the database.
     *
     * @param customerId The Customer ID to match.
     */
    @Override
    public void deleteCustomer(int customerId) {
        String appointmentsQuery = String.format(
                "DELETE FROM appointments where Customer_ID = %s", customerId
        );
        String customerQuery = String.format(
                "DELETE FROM customers where Customer_ID = %s", customerId
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(appointmentsQuery);
            DatabaseConnection.connection
                    .createStatement()
                    .execute(customerQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
