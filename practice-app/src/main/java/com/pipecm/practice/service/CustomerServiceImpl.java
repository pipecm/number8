package com.pipecm.practice.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pipecm.practice.dao.CustomerDAO;
import com.pipecm.practice.dto.Customer;
import com.pipecm.practice.dto.CustomerType;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDao;
	
	@Override
	public List<Customer> getAllCustomers() {
		return customerDao.findAll();
	}

	@Override
	public Customer getById(Long id) {
		return customerDao.findById(id).orElse(null);
	}

	@Override
	public List<Customer> getCustomersByType(CustomerType type) {
		return customerDao.findByType(type);
	}

	@Override
	public List<Customer> getCustomersByName(String name) {
		return customerDao.findByName(name);
	}

	@Override
	public synchronized boolean addCustomer(Customer customer) {
		List<Customer> existingCustomers = customerDao.findByDocumentId(customer.getDocumentId());
		if(existingCustomers.size() > 0) return false;
		else {
			customerDao.save(customer);
			return true;
		}
	}
	
	
}
