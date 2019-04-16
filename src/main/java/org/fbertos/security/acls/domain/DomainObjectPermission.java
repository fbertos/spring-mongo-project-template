package org.fbertos.security.acls.domain;

import org.springframework.util.Assert;

import java.io.Serializable;

public class DomainObjectPermission {
	private final Serializable id;
	private final MongoSid sid;
	private int permission;
	private final boolean granting;
	private boolean auditFailure;
	private boolean auditSuccess;
	
	public DomainObjectPermission(Serializable id, MongoSid sid, int permission,
								  boolean granting, boolean auditSuccess, boolean auditFailure) {
		Assert.notNull(sid, "Sid required");
		this.id = id;
		this.sid = sid;
		this.permission = permission;
		this.granting = granting;
		this.auditSuccess = auditSuccess;
		this.auditFailure = auditFailure;
	}

	public Serializable getId() {
		return this.id;
	}

	public int getPermission() {
		return this.permission;
	}

	public MongoSid getSid() {
		return this.sid;
	}

	public boolean isAuditFailure() {
		return this.auditFailure;
	}

	public boolean isAuditSuccess() {
		return this.auditSuccess;
	}

	public boolean isGranting() {
		return this.granting;
	}

	public void setAuditFailure(boolean auditFailure) {
		this.auditFailure = auditFailure;
	}

	public void setAuditSuccess(boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "DomainObjectPermission[id = " + id
				+ ", sid = " + sid
				+ ", permission = " + permission
				+ ", granting = " + granting
				+ ", auditSuccess = " + auditSuccess
				+ ", auditFailure = " + auditFailure
				+ "]";
	}
}