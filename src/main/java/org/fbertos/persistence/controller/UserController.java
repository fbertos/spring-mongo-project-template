package org.fbertos.persistence.controller;

import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bson.internal.Base64;
import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.QueryFilter;
import org.fbertos.persistence.search.QueryOrder;
import org.fbertos.persistence.search.QueryPagination;
import org.fbertos.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	 
     @Value("${security.jwt.token.secret-key:secret}")
     private String secretKey = "secret";
	 
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
    	 if (user.getUsername() == null || user.getUsername().trim().length() == 0) return false;
    	 if (user.getPassword() == null || user.getPassword().trim().length() == 0) return false;
    	 
    	 User existing = userService.loadUserByUsername(user.getUsername());
    	 if (existing == null) return false;
    	 
    	 try {
    		 String password = encodePassword(user.getPassword());
    		 user.setPassword(password);
    	 }
    	 catch (Exception e) {
    		 return false;
    	 }
    	 
    	 return true;
     }
     
     private boolean checkForUpdate(User user) {
    	 User existing = userService.get(user.getId().toHexString());
    	 if (existing == null) return false;
    	 user.setUsername(existing.getUsername());
    	 user.setPassword(existing.getPassword());
    	 return true;
     }
     
     private String encodePassword(String password) throws Exception {
         Key aesKey = new SecretKeySpec(secretKey.getBytes(), "AES");
         Cipher cipher = Cipher.getInstance("AES");
         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
         byte[] encrypted = cipher.doFinal(password.getBytes());
 		 return Base64.encode(encrypted);    	 
     }
}
