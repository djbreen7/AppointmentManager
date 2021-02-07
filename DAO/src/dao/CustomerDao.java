package dao;

import model.Customer;

import java.util.List;

/**
 * DAO for working with Customers in the database.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface CustomerDao {

    /**
     * Update or insert a customer into the database.
     *
     * @param customer The customer to update or insert.
     */
    void upsertCustomer(Customer customer);

    /**
     * Get all customers from the database.
     *
     * @return All customers in the database.
     */
    List<Customer> getAllCustomers();

    /**
     * Get a customer by PK Customer ID from the database.
     *
     * @param customerId The Customer ID to match.
     * @return The customer with the provided ID.
     */
    Customer getCustomer(int customerId);

    /**
     * Delete a customer by PK Customer ID from the database.
     *
     * @param customerId The Customer ID to match.
     * @return A boolean indicating if the deletion was successful or not.
     */
    boolean deleteCustomer(int customerId);
}
