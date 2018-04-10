package com.github.rafaelsouzaf.prot.client.view.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.rafaelsouzaf.prot.client.view.product.category.CategoryModel;

import com.extjs.gxt.ui.client.data.BaseModel;

public class ProductModel extends BaseModel {
	
	public ProductModel() {
	}
	
	public void setProductId(Integer id) {
		set("id", id);
	}
	public Integer getProductId() {
		return get("id");
	}

	public String getProductCode() {
		return get("code");
	}
	public void setProductCode(String code) {
		set("code", code);
	}

	public String getProductName() {
		return (String) get("name");
	}
	public void setProductName(String name) {
		set("name", name);
	}

	public String getProductDescription() {
		return (String) get("description");
	}
	public void setProductDescription(String description) {
		set("description", description);
	}
	
	public String getProductUrl() {
		return (String) get("url");
	}
	public void setProductUrl(String url) {
		set("url", url);
	}
	
	public void setProductCreateDate(Date createDate) {
		set("createDate", createDate);
	}
	public Date getProductCreateDate() {
		return get("createDate");
	}
	
	public void setProductModifiedDate(Date modifiedDate) {
		set("modifiedDate", modifiedDate);
	}
	public Date getProductModifiedDate() {
		return get("modifiedDate");
	}
	
	
	public List<CategoryModel> categories = new ArrayList<CategoryModel>();
	
	public List<CategoryModel> getCategories() {
		return categories;
	}
	
	public void setCategories(List<CategoryModel> categories) {
		this.categories = categories;
	}
	
	public void addCategory(CategoryModel model) {
		categories.add(model);
	}
	
}
