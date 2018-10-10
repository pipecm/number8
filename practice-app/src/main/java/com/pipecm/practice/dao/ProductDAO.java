package com.pipecm.practice.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pipecm.practice.dto.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {
	
	public List<Product> findAll();
	
	public Optional<Product> findById(Long id);
	
	public List<Product> findByNameContainingIgnoreCase(String name);
	
	@Query(value = "SELECT SUM(quantity) FROM purchase_detail WHERE product_id = ?1", nativeQuery = true)
	public int getSoldUnits(Long id);
	
}
