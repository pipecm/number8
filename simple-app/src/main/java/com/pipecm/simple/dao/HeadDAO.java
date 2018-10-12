package com.pipecm.simple.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pipecm.simple.dto.Head;

@Repository
public interface HeadDAO extends JpaRepository<Head, Long> {
	public List<Head> findByActiveTrue();

	public List<Head> findByNameContainingIgnoreCase(String name);
}
