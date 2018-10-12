package com.pipecm.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pipecm.practice.service.CustomerService;
import com.pipecm.practice.dto.Customer;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers() {
		List<Customer> customersList = customerService.getAllCustomers();
		
		if(customersList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		for (Customer customer : customersList) {
			Link selfLink = linkTo(methodOn(CustomerController.class)
			          			.getCustomerById(customer.getCustomerId()))
								.withSelfRel();
			customer.add(selfLink);
		}
		
		Link link = linkTo(methodOn(CustomerController.class)
			      		.getAllCustomers())
						.withSelfRel();
		
		Resources<Customer> response = new Resources<Customer>(customersList, link);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
		Customer customer = customerService.getById(id);
		customer.add(linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel());
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/customers/name/{name}")
	public Resources<Customer> findCustomersByName(@PathVariable("name") String name) {
		List<Customer> customersList = customerService.getCustomersByName(name);
		for (Customer customer : customersList) {
			Link selfLink = linkTo(methodOn(CustomerController.class)
			          			.getCustomerById(customer.getCustomerId()))
								.withSelfRel();
			customer.add(selfLink);
		}
		
		Link link = linkTo(methodOn(CustomerController.class)
			      		.getAllCustomers())
						.withSelfRel();
		
		return new Resources<Customer>(customersList, link);
	} 
	
	@PostMapping("/customers")
	public ResponseEntity<Void> addCustomer(@RequestBody Customer customer, UriComponentsBuilder builder) {
		boolean result = customerService.addCustomer(customer);
		
		if(!result) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri());
		
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}
