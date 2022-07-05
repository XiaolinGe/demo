package com.lynn.demoanz.service;

import com.lynn.demoanz.entity.Customer;
import com.lynn.demoanz.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomer(Long id) {
        return customerRepository.getOne(id); // TODO
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }
}
