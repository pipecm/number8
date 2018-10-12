package com.pipecm.simple;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pipecm.simple.dao.ComponentDAO;
import com.pipecm.simple.dao.HeadDAO;
import com.pipecm.simple.dto.Component;
import com.pipecm.simple.dto.Head;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:testing.properties")
public class DaoTests {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private HeadDAO headDao;
	
	@Autowired
	private ComponentDAO componentDao;
	
	@Test
	public void whenFindHeadById_thenReturnASingleHead() {
		Date timestamp = new Date();
		
		Head head = new Head();
		head.setName("one");
		head.setCreatedOn(timestamp);
		head.setUpdatedOn(timestamp);
		head.setActive(true);
		
		entityManager.persist(head);
		entityManager.flush();
		
		Head found = headDao.findById(head.getId()).get();
		
		assertThat(found.getId()).isEqualTo(head.getId());
		assertThat(found.getName()).isEqualTo(head.getName());
		assertThat(found.getCreatedOn()).isEqualTo(head.getCreatedOn());
		assertThat(found.getUpdatedOn()).isEqualTo(head.getUpdatedOn());
		assertThat(found.isActive()).isEqualTo(head.isActive());
	}
	
	@Test
	public void whenFindActiveHeads_thenReturnActiveHeads() {
		Date timestamp = new Date();
		
		Head h1 = new Head();
		h1.setName("one");
		h1.setCreatedOn(timestamp);
		h1.setUpdatedOn(timestamp);
		h1.setActive(true);
		
		Head h2 = new Head();
		h2.setName("two");
		h2.setCreatedOn(timestamp);
		h2.setUpdatedOn(timestamp);
		h2.setActive(false);
		
		entityManager.persist(h1);
		entityManager.persist(h2);
		entityManager.flush();
		
		List<Head> found = headDao.findByActiveTrue();
		
		assertThat(found.size()).isEqualTo(1);
		assertThat(found).contains(h1);
		assertThat(found).doesNotContain(h2);
	}
	
	@Test
	public void whenFindHeadsByName_thenReturnHeadsByName() {
		Date timestamp = new Date();
		String name = "test";
		
		Head h1 = new Head();
		h1.setName("test_one");
		h1.setCreatedOn(timestamp);
		h1.setUpdatedOn(timestamp);
		h1.setActive(true);
		
		Head h2 = new Head();
		h2.setName("foo");
		h2.setCreatedOn(timestamp);
		h2.setUpdatedOn(timestamp);
		h2.setActive(true);
		
		Head h3 = new Head();
		h3.setName("test_three");
		h3.setCreatedOn(timestamp);
		h3.setUpdatedOn(timestamp);
		h3.setActive(true);
		
		entityManager.persist(h1);
		entityManager.persist(h2);
		entityManager.persist(h3);
		entityManager.flush();
		
		List<Head> found = headDao.findByNameContainingIgnoreCase(name);
		
		assertThat(found.size()).isEqualTo(2);
		assertThat(found).contains(h1);
		assertThat(found).doesNotContain(h2);
		assertThat(found).contains(h3);
	}
	
	@Test
	public void whenFindComponentById_thenReturnASingleComponent() {
		Date timestamp = new Date();
		
		Head head = new Head();
		head.setName("H1");
		head.setCreatedOn(timestamp);
		head.setUpdatedOn(timestamp);
		head.setActive(true);
		
		Component component = new Component();
		component.setName("C1");
		component.setCreatedOn(timestamp);
		component.setUpdatedOn(timestamp);
		component.setActive(true);
		component.setHead(head);
		
		entityManager.persist(head);
		entityManager.persist(component);
		entityManager.flush();
		
		Component found = componentDao.findById(component.getId()).get();
		
		assertThat(found.getId()).isEqualTo(component.getId());
		assertThat(found.getName()).isEqualTo(component.getName());
		assertThat(found.getCreatedOn()).isEqualTo(component.getCreatedOn());
		assertThat(found.getUpdatedOn()).isEqualTo(component.getUpdatedOn());
		assertThat(found.isActive()).isEqualTo(component.isActive());
		assertThat(found.getHead()).isEqualTo(component.getHead());
	}
	
	@Test
	public void whenFindComponentsByName_thenReturnComponentsByName() {
		Date timestamp = new Date();
		String name = "test";
		
		Head head = new Head();
		head.setName("H1");
		head.setCreatedOn(timestamp);
		head.setUpdatedOn(timestamp);
		head.setActive(true);
		
		Component c1 = new Component();
		c1.setName("test01");
		c1.setCreatedOn(timestamp);
		c1.setUpdatedOn(timestamp);
		c1.setActive(true);
		c1.setHead(head);
		
		Component c2 = new Component();
		c2.setName("foo");
		c2.setCreatedOn(timestamp);
		c2.setUpdatedOn(timestamp);
		c2.setActive(true);
		c2.setHead(head);
		
		Component c3 = new Component();
		c3.setName("test03");
		c3.setCreatedOn(timestamp);
		c3.setUpdatedOn(timestamp);
		c3.setActive(true);
		c3.setHead(head);
		
		entityManager.persist(head);
		entityManager.persist(c1);
		entityManager.persist(c2);
		entityManager.persist(c3);
		entityManager.flush();
		
		List<Component> found = componentDao.findByNameContainingIgnoreCase(name);
		
		assertThat(found.size()).isEqualTo(2);
		assertThat(found).contains(c1);
		assertThat(found).doesNotContain(c2);
		assertThat(found).contains(c3);
	}
	
	@Test
	public void whenFindComponentsByHead_thenReturnComponentsfronThatHeadOnly() {
		Date timestamp = new Date();
		
		Head h1 = new Head();
		h1.setName("H1");
		h1.setCreatedOn(timestamp);
		h1.setUpdatedOn(timestamp);
		h1.setActive(true);
		
		Head h2 = new Head();
		h2.setName("H2");
		h2.setCreatedOn(timestamp);
		h2.setUpdatedOn(timestamp);
		h2.setActive(true);
		
		Component c1 = new Component();
		c1.setName("test01");
		c1.setCreatedOn(timestamp);
		c1.setUpdatedOn(timestamp);
		c1.setActive(true);
		c1.setHead(h1);
		
		Component c2 = new Component();
		c2.setName("foo");
		c2.setCreatedOn(timestamp);
		c2.setUpdatedOn(timestamp);
		c2.setActive(true);
		c2.setHead(h2);
		
		Component c3 = new Component();
		c3.setName("test03");
		c3.setCreatedOn(timestamp);
		c3.setUpdatedOn(timestamp);
		c3.setActive(true);
		c3.setHead(h1);
		
		entityManager.persist(h1);
		entityManager.persist(h2);
		entityManager.persist(c1);
		entityManager.persist(c2);
		entityManager.persist(c3);
		entityManager.flush();
		
		List<Component> foundH1 = componentDao.findByHead(h1);
		List<Component> foundH2 = componentDao.findByHead(h2);
		
		assertThat(foundH1.size()).isEqualTo(2);
		assertThat(foundH1).contains(c1);
		assertThat(foundH1).doesNotContain(c2);
		assertThat(foundH1).contains(c3);
		assertThat(foundH1).isEqualTo(h1.getComponents());
		
		assertThat(foundH2.size()).isEqualTo(1);
		assertThat(foundH2).doesNotContain(c1);
		assertThat(foundH2).contains(c2);
		assertThat(foundH2).doesNotContain(c3);
		assertThat(foundH2).isEqualTo(h2.getComponents());
	}
}
