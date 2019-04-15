package org.fbertos.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.fbertos.persistence.dao.UserRepository;
import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.sf.ehcache.search.Direction;

/*
R W D
1 1 1

0 
1 D
2 W
3 WD
4 R
5 RD
6 RW
7 RWD 
*/

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
    private UserRepository userRepository;

	@PreAuthorize("hasAuthority('ADMIN_ROLE')")
	public void save(User user) {
		userRepository.save(user);
	}

	@PostAuthorize("returnObject.username == authentication.name or hasPermission(returnObject, 4) or hasPermission(returnObject, 5) or hasPermission(returnObject, 6) or hasPermission(returnObject, 7) or hasAuthority('ADMIN_ROLE')")
	public User get(String id) {
		return userRepository.findById(id).get();
	}

	@PreAuthorize("#user.username == authentication.name or hasPermission(#user, 2) or hasPermission(#user, 3) or hasPermission(#user, 6) or hasPermission(#user, 7) or hasAuthority('ADMIN_ROLE')")
	public void update(User user) {
		userRepository.save(user);
	}

	@PreAuthorize("hasPermission(#user, 1) or hasPermission(#user, 3) or hasPermission(#user, 5) or hasPermission(#user, 7) or hasAuthority('ADMIN_ROLE')")
	public void delete(User user) {
		userRepository.delete(user);
	}

    @PostFilter("filterObject.username == authentication.name or hasPermission(filterObject, 4) or hasPermission(filterObject, 5) or hasPermission(filterObject, 6) or hasPermission(filterObject, 7) or hasAuthority('ADMIN_ROLE')")
	public List<User> find(QueryFilter filter) {
    	Pageable page = PageRequest.of(filter.getPagination().getPage(), filter.getPagination().getItemsPerPage(), filter.getOrder().getDirection().equals(Direction.ASCENDING)?Sort.by(filter.getOrder().getColumn()).ascending():Sort.by(filter.getOrder().getColumn()).descending());
    	List<User> unmodifiable_list = userRepository.find(filter.getQuery(), page);
    	ArrayList<User> modifiable_list = new ArrayList<User>(unmodifiable_list);
		return modifiable_list;
	}

    @PostFilter("filterObject.username == authentication.name or hasPermission(filterObject, 4) or hasPermission(filterObject, 5) or hasPermission(filterObject, 6) or hasPermission(filterObject, 7) or hasAuthority('ADMIN_ROLE')")
	public List<User> find() {
		return userRepository.findAll();
	}
    	
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> list = userRepository.findByUsername(username);
		
		if (list != null && list.size() > 0)
			return list.get(0);
    	
        throw new UsernameNotFoundException(username);
    }    
}
