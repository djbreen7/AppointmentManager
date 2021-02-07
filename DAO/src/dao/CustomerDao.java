package dao;

import model.Customer;

import java.util.List;

/**
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public interface CustomerDao {
    void upsertCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomer(int customerId);
    void deleteCustomer(int customerId);
}
