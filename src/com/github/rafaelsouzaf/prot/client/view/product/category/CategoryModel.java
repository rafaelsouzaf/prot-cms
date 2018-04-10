package com.github.rafaelsouzaf.prot.client.view.product.category;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BeanModelTag;

public class CategoryModel extends BaseModel implements BeanModelTag {

	public CategoryModel() {
	}

	public void setCategoryId(Integer id) {
		set("id", id);
	}
	public Integer getCategoryId() {
		return get("id");
	}
	
	public String getCategoryName() {
		return get("category");
	}
	public void setCategoryName(String category) {
		set("category", category);
	}
	
	public String getCategoryDescription() {
		return get("description");
	}
	public void setCategoryDescription(String description) {
		set("description", description);
	}
	
	public void setCategoryCreateDate(Date createDate) {
		set("createDate", createDate);
	}
	public Date getCategoryCreateDate() {
		return get("createDate");
	}
	
	public void setCategoryModifiedDate(Date modifiedDate) {
		set("modifiedDate", modifiedDate);
	}
	public Date getCategoryModifiedDate() {
		return get("modifiedDate");
	}
	
	
	
	public void setIdx(Integer idx) {
		set("idx", idx);
	}
	public Integer getIdx() {
		return get("idx");
	}
	
}
