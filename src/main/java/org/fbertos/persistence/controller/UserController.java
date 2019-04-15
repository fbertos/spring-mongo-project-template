package org.fbertos.persistence.controller;

import java.util.List;

import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.QueryFilter;
import org.fbertos.persistence.search.QueryOrder;
import org.fbertos.persistence.search.QueryPagination;
import org.fbertos.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	 @Autowired
	 private UserService userService;
	 
	 @GetMapping(value="/{userId}")
	 public @ResponseBody ResponseEntity<User> get(@PathVariable String userId) {
		 User user = userService.get(userId);
		 return ResponseEntity.status(HttpStatus.OK).body(user);
	 }
	 
	 @DeleteMapping(value="/{userId}")
	 public void delete(@PathVariable String userId) {
		 User user = userService.get(userId);
		 userService.delete(user);
     }

	 @PutMapping(value="")
     public @ResponseBody ResponseEntity<User> update(@RequestBody User user) {
		 if (!checkForUpdate(user))
			 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(user);
		 
    	 userService.update(user);
    	 return ResponseEntity.status(HttpStatus.OK).body(user);
     }
	    
	 @PostMapping(value="")
     public @ResponseBody ResponseEntity<User> create(@RequestBody User user) {
		 if (!checkForCreate(user))
			 return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(user);
		 
    	 userService.save(user);
    	 return ResponseEntity.status(HttpStatus.OK).body(user);
     }

	 @GetMapping(value = "")
	 public @ResponseBody ResponseEntity<List<User>> get(@RequestParam(value="q", required=false) String q,
			 @RequestParam(value="order", required=false) String order,			 
			 @RequestParam(value="page", required=false) Integer page,
			 @RequestParam(value="itemsPerPage", required=false) Integer itemsPerPage) {
		 
		 QueryFilter filter = new QueryFilter(q, QueryOrder.parseQueryOrder(order), new QueryPagination(page, itemsPerPage));
		 List<User> list = userService.find(filter);
		 return ResponseEntity.status(HttpStatus.OK).body(list);
	 }
	 
     /*************************************************************
      ************* PRIVATE METHODS ONLY FOR LOCAL USE ************
      **************************************************************/
    
     private boolean checkForCreate(User user) {
    	return false;
     }
     
     private boolean checkForUpdate(User user) {
    	return false;
     }
}
