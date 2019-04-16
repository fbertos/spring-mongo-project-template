package org.fbertos.security.acls;

import org.fbertos.security.acls.domain.MongoAcl;

public interface AclService {
	public MongoAcl loadAclByClass(String className);
}
