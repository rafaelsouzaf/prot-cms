package com.github.rafaelsouzaf.prot.client.view.user;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	
	public abstract void getAll(AsyncCallback<List<UserModel>> callback);
	public abstract void getAll(PagingLoadConfig config, AsyncCallback<PagingLoadResult<UserModel>> callback);
	public abstract void insert(UserModel model, AsyncCallback callback);
	public abstract void edit(UserModel model, AsyncCallback callback);
	public abstract void delete(UserModel model, AsyncCallback callback);
	public abstract void isValid(String username, String password, AsyncCallback<Boolean> callback);
	
}
