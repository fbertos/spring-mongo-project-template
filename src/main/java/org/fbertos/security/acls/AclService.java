package org.fbertos.security.acls;

import org.fbertos.security.acls.domain.MongoAcl;

public interface AclService {
	public void update(MongoAcl acl);
	public MongoAcl loadAclByClass(String className);
	public void save(MongoAcl acl);
}
