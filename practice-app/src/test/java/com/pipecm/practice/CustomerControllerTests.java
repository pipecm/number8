package com.pipecm.practice;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.pipecm.practice.controller.CustomerController;
import com.pipecm.practice.dto.Customer;
import com.pipecm.practice.dto.CustomerType;
import com.pipecm.practice.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {
	
	@Autowired
	private MockMvc mock;
	
	@MockBean
	private CustomerService service;
	
	@Test
	public void whenGetAllCustomers_thenRetrieveAllCustomers() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("Rosamel");
		customer.setLastName("Fierro");
		customer.setDocumentId("14353567-8");
		customer.setType(CustomerType.PERSON);
		
		customer.add(linkTo(methodOn(CustomerController.class)
				.getCustomerById(customer.getCustomerId()))
				.withSelfRel());
		
		List<Customer> customersList = singletonList(customer);
				
		given(service.getAllCustomers()).willReturn(customersList);
		
		mock.perform(get("/api/customers")			
			.accept(MediaTypes.HAL_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
			.andExpect(jsonPath("$._embedded.customerList", hasSize(1)))
			.andExpect(jsonPath("$._embedded.customerList[0].customerId", is(customer.getCustomerId().intValue())))
			.andExpect(jsonPath("$._embedded.customerList[0].firstName", is(customer.getFirstName())))
			.andExpect(jsonPath("$._embedded.customerList[0].lastName", is(customer.getLastName())))
			.andExpect(jsonPath("$._embedded.customerList[0].documentId", is(customer.getDocumentId())))
			.andExpect(jsonPath("$._embedded.customerList[0].type", is(customer.getType().name())));
			
	}
	
	@Test
	public void whenNoCustomers_thenRetrieveEmptyList() throws Exception {
		List<Customer> emptyList = new ArrayList<Customer>();
		
		given(service.getAllCustomers()).willReturn(emptyList);
		
		mock.perform(get("/api/customers")			
				.accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isNoContent());
		
	}
}
