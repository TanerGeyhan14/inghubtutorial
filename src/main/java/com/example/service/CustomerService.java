package com.example.service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author taner geyhan
 * CustomerService
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Create Customer Method
     * @param customer
     * @return created customer object with input parameters
     */
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * List All Customer Method
     * @return all customers list
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Update Customer Method
     * @param id
     * @param customer
     * @return update customer information with input parameters
     * @throws Exception Error state if customer not found
     */
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found"));
        existingCustomer.setName(customer.getName());
        existingCustomer.setSurname(customer.getSurname());
        existingCustomer.setCreditLimit(customer.getCreditLimit());
        existingCustomer.setUsedCreditLimit(customer.getUsedCreditLimit());
        return customerRepository.save(existingCustomer);
    }

    /**
     * Delete Customer Method
     * @param id
     * @throws Exception Error state if customer not found
     */
    public void deleteCustomer(Long id) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found"));
        customerRepository.delete(customer);
    }

    /**
     * Get Customer Method
     * @param id
     * @return get customer with given id
     */
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
