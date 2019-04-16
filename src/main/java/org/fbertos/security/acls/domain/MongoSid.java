package org.fbertos.security.acls.domain;

public class MongoSid {
	private String name;
	private boolean isPrincipal;

	public MongoSid() {

	}

	public MongoSid(String name) {
		this.name = name;
		this.isPrincipal = true;
	}

	public MongoSid(String name, boolean isPrincipal) {
		this.name = name;
		this.isPrincipal = isPrincipal;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrincipal() {
		return this.isPrincipal;
	}

	public void setPrincipal(boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	@Override
	public String toString() {
		return "MongoSid[name = " + name + ", isPrincipal = " + isPrincipal + "]";
	}
}