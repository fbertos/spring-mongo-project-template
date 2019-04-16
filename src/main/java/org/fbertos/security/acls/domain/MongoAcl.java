package org.fbertos.security.acls.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@CompoundIndexes({
		@CompoundIndex(name = "domainObject", def = "{'instanceId' : 1, 'className' : 1}")
})
@Document(collection = "Acl")
public class MongoAcl {
	@Id
	private Serializable id;
	private String className;
	private Serializable instanceId;
	private MongoSid owner;
	
	@Indexed
	private Serializable parentId = null;
	
	private boolean inheritPermissions = true;
	private List<DomainObjectPermission> permissions = new ArrayList<>();

	public MongoAcl() {
	}
	
	public MongoAcl(Serializable instanceId, String className, Serializable id) {
		this.id = id;
		this.instanceId = instanceId;
		this.className = className;

		String ownerName = SecurityContextHolder.getContext().getAuthentication().getName();
		this.owner = new MongoSid(ownerName);
	}

	public MongoAcl(Serializable instanceId, String className, Serializable id, MongoSid owner,
					Serializable parentId, boolean entriesInheriting) {
		this(instanceId, className, id);
		this.parentId = parentId;
		this.owner = owner;
		this.inheritPermissions = entriesInheriting;
	}

	public String getClassName() {
		return this.className;
	}

	public Serializable getInstanceId() {
		return this.instanceId;
	}

	public MongoSid getOwner() {
		return this.owner;
	}

	public Serializable getId() {
		return this.id;
	}

	public boolean isInheritPermissions() {
		return this.inheritPermissions;
	}

	public Serializable getParentId() {
		return this.parentId;
	}

	public List<DomainObjectPermission> getPermissions() {
		return this.permissions;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setPermissions(List<DomainObjectPermission> permissions) {
		this.permissions = permissions;
	}

	public void setInheritPermissions(boolean inheritPermissions) {
		this.inheritPermissions = inheritPermissions;
	}

	@Override
	public String toString() {
		return "MongoAcl[id = " + id
				+ ", className = " + className
				+ ", instanceId = " + instanceId
				+ ", parentId = " + parentId
				+ ", inheritPermissions = " + inheritPermissions
				+ ", owner = " + owner
				+ ", permissions = " + permissions
				+ "]";
	}
}