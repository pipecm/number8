package com.pipecm.simple.service;

import java.util.List;
import com.pipecm.simple.dto.Head;

public interface HeadService {
	
	public Head findById(Long id);

	public List<Head> getActiveHeads();

	public List<Head> findByName(String name);
	
}
