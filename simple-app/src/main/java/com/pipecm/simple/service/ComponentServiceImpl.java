package com.pipecm.simple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pipecm.simple.dao.ComponentDAO;
import com.pipecm.simple.dto.Component;
import com.pipecm.simple.dto.Head;

@Service
@Transactional
public class ComponentServiceImpl implements ComponentService {

	@Autowired
	private ComponentDAO dao;
	
	@Override
	public Component getById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Component> findByName(String name) {
		return dao.findByNameContainingIgnoreCase(name);
	}

	@Override
	public List<Component> getByHead(Head head) {
		return dao.findByHead(head);
	}

}
