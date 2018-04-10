package com.github.rafaelsouzaf.prot.client.view.edition;

import java.util.Date;
import java.util.Map;

import com.github.rafaelsouzaf.prot.client.view.product.ProductModel;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BeanModelTag;

public class EditionModel extends BaseModel implements BeanModelTag {

	public EditionModel() {
	}

	public String getName() {
		return get("name");
	}
	public void setName(String name) {
		set("name", name);
	}

	public ProductModel getProduct() {
		return get("productId");
	}
	public void setProduct(ProductModel model) {
		set("productId", model);
	}

	public void setDate(Date date) {
		set("date", date);
	}
	public Date getDate() {
		return get("date");
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
	
	public void setId(Integer id) {
		set("id", id);
	}
	
	public Integer getId() {
		return get("id");
	}
	
	public String toString() {
		StringBuilder retorno = new StringBuilder();
		Map<String, Object> properties = super.getProperties();
		for (Map.Entry<String, Object> separa: properties.entrySet()) {
			retorno.append(separa.getKey() + " = " + separa.getValue() + ", ");
		}
		return retorno.toString();
	}

}
