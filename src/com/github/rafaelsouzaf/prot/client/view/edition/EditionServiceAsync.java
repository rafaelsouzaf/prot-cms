package com.github.rafaelsouzaf.prot.client.view.edition;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EditionServiceAsync {
	
	public abstract void getAll(AsyncCallback<List<EditionModel>> callback);
	public abstract void getAll(PagingLoadConfig config, AsyncCallback<PagingLoadResult<EditionModel>> callback);
	public abstract void insert(EditionModel model, AsyncCallback callback);
	public abstract void edit(EditionModel model, AsyncCallback callback);
	public abstract void delete(EditionModel model, AsyncCallback callback);
	public abstract void isValid(String username, String password, AsyncCallback<Boolean> callback);
	
}
