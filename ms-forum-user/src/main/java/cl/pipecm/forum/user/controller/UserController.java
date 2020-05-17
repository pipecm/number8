package cl.pipecm.forum.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.pipecm.forum.user.common.ServiceResponse;
import cl.pipecm.forum.user.entity.Role;
import cl.pipecm.forum.user.entity.User;
import cl.pipecm.forum.user.service.UserService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/m")
	@ResponseBody
	public ResponseEntity<ServiceResponse> findByEmail(@RequestParam("email") String email) {
		return this.createResponseEntity(service.findByEmail(email), HttpStatus.OK);
	}
	
	@GetMapping("/r")
	@ResponseBody
	public ResponseEntity<ServiceResponse> findByRole(@RequestParam("role") Role role) {	
		return this.createResponseEntity(service.findByRole(role), HttpStatus.OK);
	}
	
	@GetMapping("/n")
	@ResponseBody
	public ResponseEntity<ServiceResponse> findByName(@RequestParam("name") String name) {	
		return this.createResponseEntity(service.findByName(name), HttpStatus.OK);
	}
	
	@GetMapping("/o")
	@ResponseBody
	public ResponseEntity<ServiceResponse> findOnlineUsers() {	
		return this.createResponseEntity(service.findOnlineUsers(), HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<ServiceResponse> create(@RequestBody User user) {
		return this.createResponseEntity(service.add(user), HttpStatus.CREATED);
	}
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<ServiceResponse> update(@RequestBody User user) {
		service.update(user);
		return this.createResponseEntity(null, HttpStatus.OK);
	}
	
	@DeleteMapping
	@ResponseBody
	public ResponseEntity<ServiceResponse> delete(@RequestBody Long id) {
		service.delete(id);
		return this.createResponseEntity(null, HttpStatus.OK);
	}
	
	private ResponseEntity<ServiceResponse> createResponseEntity(Object data, HttpStatus status) {
		ServiceResponse response = ServiceResponse.builder()
												  .code(status.value())
												  .message(status.name())
												  .data(data)
												  .build();
		return new ResponseEntity<ServiceResponse>(response, status);
	}
 }
