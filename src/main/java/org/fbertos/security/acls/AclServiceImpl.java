package org.fbertos.security.acls;

import java.util.List;

import org.fbertos.security.acls.dao.AclRepository;
import org.fbertos.security.acls.domain.MongoAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AclServiceImpl implements AclService {
	@Autowired
	AclRepository aclRepository;
	
	@PreAuthorize("hasAuthority('ADMIN_ROLE')")
	public void save(MongoAcl acl) {
		aclRepository.save(acl);
	}

	@PreAuthorize("hasAuthority('ADMIN_ROLE')")
	public void update(MongoAcl acl) {
		aclRepository.save(acl);
	}
	
	@PreAuthorize("hasAuthority('ADMIN_ROLE')")
	public MongoAcl loadAclByClass(String className) {
		List<MongoAcl> list = aclRepository.findByClassName(className);
		if (list != null) return list.get(0);
		return null;
	}
}
