package implementations;

import data.DatabaseConnection;
import dao.CustomerDao;
import model.Customer;
import utilities.ResultSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private ResultSetBuilder resultSetBuilder;

    public CustomerDaoImpl() {
        resultSetBuilder = new ResultSetBuilder();
    }

    @Override
    public void upsertCustomer(Customer customer) {
        if (customer.getCustomerId() == -1) {
            addCustomer(customer);
            return;
        }
        updateCustomer(customer);
        }

    public void addCustomer(Customer customer) {
        String query = String.format(
            "INSERT INTO customers " +
            "(Customer_Name, Phone, Address, Postal_Code, Division_ID) " +
            "VALUES ('%s', '%s', '%s', '%s', %s)",
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getPostalCode(),
                customer.getDivisionId()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    public void updateCustomer(Customer customer) {
        String query = String.format(
                "UPDATE customers " +
                "SET Customer_Name = '%s', " +
                "Phone = '%s', " +
                "Address = '%s', " +
                "Postal_Code = '%s', " +
                "Division_ID = %s " +
                "WHERE Customer_ID = %s",
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getPostalCode(),
                customer.getDivisionId(),
                customer.getCustomerId()
        );
        try {
            DatabaseConnection.makeConnection();
            DatabaseConnection.connection
                    .createStatement()
                    .execute(query);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

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
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                var customer = resultSetBuilder.buildCustomerResult(resultSet, false);
                customer.setDivision(resultSetBuilder.buildDivisionResult(resultSet, false));
                customer.getDivision().setCountry(resultSetBuilder.buildCountryResult(resultSet, false));
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
            var resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                customer = resultSetBuilder.buildCustomerResult(resultSet, true);
                customer.setDivision(resultSetBuilder.buildDivisionResult(resultSet, false));
                customer.getDivision().setCountry(resultSetBuilder.buildCountryResult(resultSet, false));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DatabaseConnection.closeConnection();
            return customer;
        }
    }

    @Override
    public void deleteCustomer(int customerId) {

    }
}
