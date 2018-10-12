package com.pipecm.simple.service;

import java.util.List;

import com.pipecm.simple.dto.Component;
import com.pipecm.simple.dto.Head;

public interface ComponentService {

	public Component getById(Long id);

	public List<Component> findByName(String name);

	public List<Component> getByHead(Head head);

}
