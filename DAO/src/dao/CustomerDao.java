package dao;

import model.Customer;

import java.util.List;

public interface CustomerDao {
    public void upsertCustomer(Customer customer);
    public List<Customer> getAllCustomers();
    public Customer getCustomer(int customerId);
    public void deleteCustomer(int customerId);
}
