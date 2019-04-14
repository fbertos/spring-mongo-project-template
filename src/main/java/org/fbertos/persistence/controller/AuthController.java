package org.fbertos.persistence.controller;

import java.util.HashMap;
import java.util.Map;

import org.fbertos.authorization.config.JwtTokenProvider;
import org.fbertos.persistence.model.User;
import org.fbertos.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	 @Autowired
	 UserService userService;
	
	 @Autowired
	 JwtTokenProvider jwtTokenProvider;
	 
	 @Autowired
	 AuthenticationManager authenticationManager;
	 
	 @PostMapping(value="/auth")
	 public @ResponseBody ResponseEntity<AuthInfo> auth(@RequestParam String username, @RequestParam String password) {
		 try {
			 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			 User user = userService.loadUserByUsername(username);
			 String token = jwtTokenProvider.createToken(username, user.getStringAuthorities());
			 AuthInfo info = new AuthInfo(username, token);
			 Map<Object, Object> model = new HashMap<>();
			 model.put("username", username);
			 model.put("token", token);
			 return ResponseEntity.status(HttpStatus.OK).body(info);
		 }
		 catch (AuthenticationException e) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);			 
		 }
	 }
	 
	 @PutMapping(value="/auth/{token}")
	 public @ResponseBody ResponseEntity<AuthInfo> validateToken(@PathVariable String token) {
		 try {
			 if (jwtTokenProvider.validateToken(token)) {
				 String username = jwtTokenProvider.getUsername(token);
				 User user = userService.loadUserByUsername(username);
				 String newToken = jwtTokenProvider.createToken(username, user.getStringAuthorities());
				 AuthInfo info = new AuthInfo(username, newToken);
				 Map<Object, Object> model = new HashMap<>();
				 model.put(username, username);
				 model.put("token", newToken);
				 return ResponseEntity.status(HttpStatus.OK).body(info);
			 }
			 
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		 }
		 catch (AuthenticationException e) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);			 
		 }
	 }
}
