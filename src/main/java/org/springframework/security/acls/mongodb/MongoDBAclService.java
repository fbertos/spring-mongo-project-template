package org.springframework.security.acls.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.dao.AclRepository;
import org.springframework.security.acls.domain.MongoAcl;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.util.Assert;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class MongoDBAclService implements AclService {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected AclRepository aclRepository;

	private final LookupStrategy lookupStrategy;

	public MongoDBAclService(AclRepository aclRepository, LookupStrategy lookupStrategy) {
		Assert.notNull(aclRepository, "AclRepository required");
		Assert.notNull(lookupStrategy, "LookupStrategy required");
		this.aclRepository = aclRepository;
		this.lookupStrategy = lookupStrategy;
	}

	@Override
	public List<ObjectIdentity> findChildren(ObjectIdentity parentIdentity) {

		List<MongoAcl> aclsForDomainObject =
				aclRepository.findByInstanceIdAndClassName(parentIdentity.getIdentifier(), parentIdentity.getType());
		if (null == aclsForDomainObject || aclsForDomainObject.isEmpty()) {
			return null;
		}
		LinkedHashSet<MongoAcl> children = new LinkedHashSet<>();
		// find children for each found ACL entity
		for (MongoAcl acl : aclsForDomainObject) {
			List<MongoAcl> childAclsOfDomainObject = aclRepository.findByParentId(acl.getId());
			children.addAll(childAclsOfDomainObject);
		}

		List<ObjectIdentity> foundChildren = new ArrayList<>();
		for (MongoAcl child : children) {
			try {
				ObjectIdentity oId = new ObjectIdentityImpl(Class.forName(child.getClassName()), child.getInstanceId());
				if (!foundChildren.contains(oId)) {
					foundChildren.add(oId);
				}
			} catch (ClassNotFoundException cnfEx) {
				LOG.error("Could not find class of domain object '{}' referenced by ACL {}",
						child.getClassName(), child.getId());
			}
		}
		return foundChildren;
	}

	@Override
	public Acl readAclById(ObjectIdentity object) throws NotFoundException {
		return readAclById(object, null);
	}

	@Override
	public Acl readAclById(ObjectIdentity object, List<Sid> sids) throws NotFoundException {
		Map<ObjectIdentity, Acl> map = readAclsById(Collections.singletonList(object), sids);
		Assert.isTrue(map.containsKey(object),
				"There should have been an Acl entry for ObjectIdentity " + object);

		return map.get(object);
	}

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects)
			throws NotFoundException {
		return readAclsById(objects, null);
	}

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids)
			throws NotFoundException {
		
		Map<ObjectIdentity, Acl> result = lookupStrategy.readAclsById(objects, sids);

		// Check every requested object identity was found (throw NotFoundException if needed)
		for (ObjectIdentity oid : objects) {
			if (!result.containsKey(oid)) {
				throw new NotFoundException(
						"Unable to find ACL information for object identity '" + oid + "'");
			}
		}

		return result;
	}
}