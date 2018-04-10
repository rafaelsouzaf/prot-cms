package com.github.rafaelsouzaf.prot.client.view.product;

import java.util.List;


import com.github.rafaelsouzaf.prot.client.util.FileModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {
	
	public abstract void getAll(AsyncCallback<List<ProductModel>> callback);
	public abstract void insert(ProductModel model, AsyncCallback callback);
	public abstract void edit(ProductModel model, AsyncCallback callback);
	public abstract void delete(ProductModel model, AsyncCallback callback);
	
	public abstract void getPhotos(AsyncCallback<List<FileModel>> callback);
	
}
