package org.fbertos.persistence.controller;

import org.fbertos.security.acls.AclService;
import org.fbertos.security.acls.domain.MongoAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acls")
public class AclController {
	@Autowired
	AclService aclService;
	
	 @GetMapping(value="/{className}")
	 public @ResponseBody ResponseEntity<MongoAcl> get(@PathVariable String className) {
		 MongoAcl acl = aclService.loadAclByClass(className);
		 return ResponseEntity.status(HttpStatus.OK).body(acl);
	 }
	
}
