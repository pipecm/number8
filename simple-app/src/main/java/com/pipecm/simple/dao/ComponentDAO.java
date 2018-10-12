package com.pipecm.simple.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pipecm.simple.dto.Component;
import com.pipecm.simple.dto.Head;

@Repository
public interface ComponentDAO extends JpaRepository<Component, Long> {

	public List<Component> findByNameContainingIgnoreCase(String name);

	public List<Component> findByHead(Head head);

}
