package dao;

import model.Customer;

import java.util.List;

public interface CustomerDao {
    void upsertCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomer(int customerId);
    void deleteCustomer(int customerId);
}
