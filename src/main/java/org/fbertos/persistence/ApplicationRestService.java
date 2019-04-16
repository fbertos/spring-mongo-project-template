package org.fbertos.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages={"org.fbertos.persistence.model", "org.fbertos.security.acls.domain"})
@ComponentScan(basePackages={"org.fbertos"})
public class ApplicationRestService {

 public static void main(String[] args) {
  SpringApplication.run(ApplicationRestService.class, args);
 }
}