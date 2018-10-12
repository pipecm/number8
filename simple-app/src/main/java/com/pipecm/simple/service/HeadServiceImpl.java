package com.pipecm.simple.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pipecm.simple.dao.HeadDAO;
import com.pipecm.simple.dto.Head;

@Service
@Transactional
public class HeadServiceImpl implements HeadService {

	@Autowired
	private HeadDAO dao;
	
	@Override
	public Head findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Head> getActiveHeads() {
		return dao.findByActiveTrue();
	}

	@Override
	public List<Head> findByName(String name) {
		return dao.findByNameContainingIgnoreCase(name);
	}

}
