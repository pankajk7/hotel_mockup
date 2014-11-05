package com.hotel_mockup.entities;

public class LoginUser {
	
	String created_at;
	String display_name;
	String email;
	String encrypted_password;
	String id;
	String remember_token;
	String roles;
	String salt;
	String updated_at;
	
	
	String confirmation_token;
	public String getConfirmation_token() {
		return confirmation_token;
	}
	public void setConfirmation_token(String confirmation_token) {
		this.confirmation_token = confirmation_token;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEncrypted_password() {
		return encrypted_password;
	}
	public void setEncrypted_password(String encrypted_password) {
		this.encrypted_password = encrypted_password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemember_token() {
		return remember_token;
	}
	public void setRemember_token(String remember_token) {
		this.remember_token = remember_token;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}	

}
