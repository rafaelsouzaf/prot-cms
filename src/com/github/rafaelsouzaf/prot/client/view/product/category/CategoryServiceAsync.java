package com.github.rafaelsouzaf.prot.client.view.product.category;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CategoryServiceAsync {
	
	public abstract void getAll(AsyncCallback<List<CategoryModel>> callback);
	public abstract void getAll(PagingLoadConfig config, AsyncCallback<PagingLoadResult<CategoryModel>> callback);
	public abstract void insert(CategoryModel model, AsyncCallback callback);
	public abstract void edit(CategoryModel model, AsyncCallback callback);
	public abstract void delete(CategoryModel model, AsyncCallback callback);
	public abstract void isValid(String username, String password, AsyncCallback<Boolean> callback);
	
}
