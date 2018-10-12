package com.pipecm.simple;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pipecm.simple.dao.ComponentDAO;
import com.pipecm.simple.dao.HeadDAO;
import com.pipecm.simple.dto.Component;
import com.pipecm.simple.dto.Head;
import com.pipecm.simple.service.ComponentService;
import com.pipecm.simple.service.ComponentServiceImpl;
import com.pipecm.simple.service.HeadService;
import com.pipecm.simple.service.HeadServiceImpl;


@RunWith(SpringRunner.class)
public class ServiceTests {
	
	@TestConfiguration
    static class SimpleAppTestContextConfiguration {
  
        @Bean
        public HeadService headService() {
            return new HeadServiceImpl();
        }
        
        @Bean
        public ComponentService componentService() {
        	return new ComponentServiceImpl();
        }
    }
	
	@Autowired
	private HeadService headService;
	
	@Autowired
	private ComponentService componentService;
	
	@MockBean
	private HeadDAO headDao;
	
	@MockBean
	private ComponentDAO componentDao;
	
	@Before
	public void setUp() {
		Date timestamp = new Date();
		String headKeyword = "Two";
		String componentKeyword = "One";
		
		Head h1 = new Head();
		h1.setId(1L);
		h1.setName("H1");
		h1.setCreatedOn(timestamp);
		h1.setUpdatedOn(timestamp);
		h1.setActive(true);
		
		Head h2 = new Head();
		h2.setName("two");
		h2.setCreatedOn(timestamp);
		h2.setUpdatedOn(timestamp);
		h2.setActive(false);
		
		Component c1 = new Component();
		c1.setId(1L);
		c1.setName("one");
		c1.setCreatedOn(timestamp);
		c1.setUpdatedOn(timestamp);
		c1.setActive(true);
		c1.setHead(h1);
		
		Component c2 = new Component();
		c2.setId(2L);
		c2.setName("C2");
		c2.setCreatedOn(timestamp);
		c2.setUpdatedOn(timestamp);
		c2.setActive(true);
		c2.setHead(h2);
		
		when(headDao.findById(h1.getId())).thenReturn(Optional.of(h1));
		when(headDao.findByActiveTrue()).thenReturn(singletonList(h1));
		when(headDao.findByNameContainingIgnoreCase(headKeyword)).thenReturn(singletonList(h2));
		
		when(componentDao.findById(c1.getId())).thenReturn(Optional.of(c1));
		when(componentDao.findByNameContainingIgnoreCase(componentKeyword)).thenReturn(singletonList(c1));
		
		when(componentDao.findByHead(h1)).thenReturn(singletonList(c1));
		when(componentDao.findByHead(h2)).thenReturn(singletonList(c2));
	}
	
	@Test
	public void whenFindHeadById_thenReturnASingleHead() {
		Long id = 1L;
		Head found = headService.findById(id);
		
		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(id);
	}
	
	@Test
	public void whenFindActiveHeads_thenReturnActiveHeads() {
		List<Head> activeHeads = headService.getActiveHeads();
		
		assertThat(activeHeads).isNotNull();
		assertThat(activeHeads).isNotEmpty();
		assertThat(activeHeads).hasSize(1);
		
		for(Head head : activeHeads) {
			assertThat(head.isActive()).isEqualTo(true);
		}
	}
	
	@Test
	public void whenFindHeadsByName_thenReturnHeadsByName() {
		String keyword = "Two";
		List<Head> headsByName = headService.findByName(keyword);
		
		assertThat(headsByName).isNotNull();
		assertThat(headsByName).isNotEmpty();
		assertThat(headsByName).hasSize(1);
		
		for(Head head : headsByName) {
			assertThat(head.getName()).containsIgnoringCase(keyword);
		}
	}
	
	@Test
	public void whenFindComponentById_thenReturnASingleComponent() {
		Long id = 1L;
		Component found = componentService.getById(id);
		
		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(id);
	}
	
	@Test
	public void whenFindComponentsByName_thenReturnComponentsByName() {
		String keyword = "One";
		List<Component> componentsByName = componentService.findByName(keyword);
		
		assertThat(componentsByName).isNotNull();
		assertThat(componentsByName).isNotEmpty();
		assertThat(componentsByName).hasSize(1);
		
		for(Component component : componentsByName) {
			assertThat(component.getName()).containsIgnoringCase(keyword);
		}
	}
	
	@Test
	public void whenFindComponentsByHead_thenReturnComponentsfronThatHeadOnly() {
		Head head = headService.findById(1L);
		List<Component> componentsByHead = componentService.getByHead(head);
		
		assertThat(componentsByHead).isNotNull();
		assertThat(componentsByHead).isNotEmpty();
		assertThat(componentsByHead).hasSize(1);
		assertThat(componentsByHead).isEqualTo(head.getComponents());
		
		for(Component component : componentsByHead) {
			assertThat(component.getHead()).isEqualTo(head);
		}
	}
}
