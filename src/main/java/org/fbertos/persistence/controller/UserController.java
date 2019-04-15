package org.fbertos.persistence.controller;

import java.util.List;

import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.QueryFilter;
import org.fbertos.persistence.search.QueryOrder;
import org.fbertos.persistence.search.QueryPagination;
import org.fbertos.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	 @Autowired
	 private UserService userService;
	 
	 @GetMapping(value="/users/{userId}")
	 public @ResponseBody User get(@PathVariable String userId) {
		 User user = userService.get(userId);
		 return user;
	 }
	 
	 @DeleteMapping(value="/users/{userId}")
	 public void delete(@PathVariable String userId) {
		 User user = userService.get(userId);
		 userService.delete(user);
     }

	 @PutMapping(value="/users")
     public void update(@RequestBody User user) {
    	 userService.update(user);
     }
	    
	 @PostMapping(value="/users")
     public @ResponseBody User create(@RequestBody User user) {
    	 userService.save(user);
    	 return user;
     }

	 @GetMapping(value = "/users")
	 public @ResponseBody List<User> get(@RequestParam(value="q", required=false) String q,
			 @RequestParam(value="order", required=false) String order,			 
			 @RequestParam(value="page", required=false) Integer page,
			 @RequestParam(value="itemsPerPage", required=false) Integer itemsPerPage) {
		 
		 QueryFilter filter = new QueryFilter(q, QueryOrder.parseQueryOrder(order), new QueryPagination(page, itemsPerPage));
		 return userService.find(filter);
	 }
}
