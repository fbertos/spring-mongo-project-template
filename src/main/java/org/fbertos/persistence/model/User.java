package org.fbertos.persistence.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "User")
public class User implements UserDetails {
	private static final long serialVersionUID = 560900391361803331L;

	@Id
	private ObjectId _id;	
    private String username;
    private String password;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean enabled;
    
    /** Extra fields **/
    private String fullName;
    private String contactDetails;
    
    @JsonIgnore
    private Collection<Authority> authorities;    

	public ObjectId getId() {
		return _id;
	}

	public void setId(ObjectId id) {
		this._id = id;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isAccountNonExpired() {
		return !this.accountExpired;
	}

	public boolean isAccountNonLocked() {
		return !this.accountLocked;
	}

	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public void setAuthorities(Collection<Authority> auth) {
		this.authorities = auth;
	}

	public List<String> getStringAuthorities() {
		ArrayList<String> list = new ArrayList<String>();
		
		if (this.authorities != null) { 
			for (GrantedAuthority item : this.authorities) {
				list.add(item.getAuthority());
			}
		}
		
		return list;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
}