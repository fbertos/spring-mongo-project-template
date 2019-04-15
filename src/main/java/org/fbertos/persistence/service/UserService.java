package org.fbertos.persistence.service;

import java.util.List;

import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.QueryFilter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {
	public void save(User user);
	public User get(String id);
	public void update(User user);
	public void delete(User user);
	
	public List<User> find();
	public List<User> find(QueryFilter filter);
	public User loadUserByUsername(String username) throws UsernameNotFoundException;
}
