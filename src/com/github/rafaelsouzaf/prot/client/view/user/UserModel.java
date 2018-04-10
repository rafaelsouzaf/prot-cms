package com.github.rafaelsouzaf.prot.client.view.user;

import java.util.Date;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BeanModelTag;

public class UserModel extends BaseModel implements BeanModelTag {

	public UserModel() {
	}
	
	public void setId(Integer id) {
		set("id", id);
	}
	public Integer getId() {
		return get("id");
	}

	public String getUsername() {
		return get("username");
	}
	public void setUsername(String username) {
		set("username", username);
	}

	public String getFirstname() {
		return get("firstname");
	}
	public void setFirstname(String firstname) {
		set("firstname", firstname);
	}

	public String getLastname() {
		return get("lastname");
	}
	public void setLastname(String lastname) {
		set("lastname", lastname);
	}
	
	public String getMidlename() {
		return get("midlename");
	}
	public void setMidlename(String midlename) {
		set("midlename", midlename);
	}
	
	public String getPassword() {
		return (String) get("password");
	}
	public void setPassword(String password) {
		set("password", password);
	}

	public String getStatus() {
		return (String) get("status");
	}
	public void setStatus(String status) {
		set("status", status);
	}
	
	public void setCreateDate(Date createDate) {
		set("createDate", createDate);
	}
	public Date getCreateDate() {
		return get("createDate");
	}
	
	public void setModifiedDate(Date modifiedDate) {
		set("modifiedDate", modifiedDate);
	}
	public Date getModifiedDate() {
		return get("modifiedDate");
	}
	
}
