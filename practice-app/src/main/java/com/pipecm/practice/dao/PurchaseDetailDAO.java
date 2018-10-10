package com.pipecm.practice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pipecm.practice.dto.Purchase;
import com.pipecm.practice.dto.PurchaseDetail;

@Repository
public interface PurchaseDetailDAO extends JpaRepository<PurchaseDetail, Long> {
	
	public List<PurchaseDetail> findByPurchase(Purchase purchase);
	
}
