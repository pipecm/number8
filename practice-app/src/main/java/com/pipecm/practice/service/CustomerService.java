package com.pipecm.practice.service;

import java.util.List;
import com.pipecm.practice.dto.Customer;
import com.pipecm.practice.dto.CustomerType;

public interface CustomerService {
	public List<Customer> getAllCustomers();
	public Customer getById(Long id);
	public List<Customer> getCustomersByType(CustomerType type);
	public List<Customer> getCustomersByName(String name);
	public boolean addCustomer(Customer customer);
}
