package com.pipecm.practice.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pipecm.practice.dto.Customer;
import com.pipecm.practice.dto.Purchase;

@Repository
public interface PurchaseDAO extends JpaRepository<Purchase, Long> {
	
	public static final String SQL_TOTAL_PRICE = "select * from purchase pu "
												+ "where (select sum(d.quantity * p.price) "
														+ "from purchase_detail d, product p "
														+ "where d.product_id = p.id "
														+ "and d.purchase_id = pu.id) >= ?1";
	
	public List<Purchase> findAllByOrderByDateDesc();
	
	public Optional<Purchase> findById(Long id);
	
	public List<Purchase> findByCustomerOrderByDateDesc(Customer customer);
	
	@Query(value = SQL_TOTAL_PRICE, nativeQuery = true)
	public List<Purchase> findByTotalPriceGreaterOrEqualThan(int totalPrice);
	
	
}
