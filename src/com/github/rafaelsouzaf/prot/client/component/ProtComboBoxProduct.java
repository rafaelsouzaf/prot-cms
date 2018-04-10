package com.github.rafaelsouzaf.prot.client.component;

import java.util.List;

import com.github.rafaelsouzaf.prot.client.view.product.ProductModel;
import com.github.rafaelsouzaf.prot.client.view.product.ProductService;
import com.github.rafaelsouzaf.prot.client.view.product.ProductServiceAsync;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProtComboBoxProduct extends ComboBox<ProductModel> {
	
	private ProductServiceAsync service = GWT.create(ProductService.class);
	ListStore<ProductModel> store = new ListStore<ProductModel>();

	public ProtComboBoxProduct() {

		setEmptyText("");
		setDisplayField("name");
		setWidth(150);
		setStore(store);
		setEnabled(true);
		setLoadingText("Cargando...");
		setEditable(false);
		
		getAll();

	}
	
	public void getAll() {
		service.getAll(new AsyncCallback<List<ProductModel>>() {
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error", "Error al buscar productos: " + caught.getMessage(), null);
			}
			public void onSuccess(List<ProductModel> result) {
				store.add(result);
			}
		});
	}

}
