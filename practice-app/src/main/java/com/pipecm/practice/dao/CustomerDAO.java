package com.pipecm.practice.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pipecm.practice.dto.Customer;
import com.pipecm.practice.dto.CustomerType;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long> {
	
	public List<Customer> findAll();
	
	public Optional<Customer> findById(Long id);
	
	public List<Customer> findByType(CustomerType type);
	
	@Query(value = "SELECT * FROM customer WHERE LOWER(CONCAT(first_name, ' ', last_name)) LIKE CONCAT('%', LOWER(?1), '%')", nativeQuery = true)
	public List<Customer> findByName(String name);
}
