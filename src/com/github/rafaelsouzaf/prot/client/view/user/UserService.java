
package com.github.rafaelsouzaf.prot.client.view.user;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {
	
	public abstract List<UserModel> getAll();
	public abstract PagingLoadResult<UserModel> getAll(PagingLoadConfig config);
	public abstract Integer insert(UserModel model);
	public abstract void edit(UserModel model);
	public abstract void delete(UserModel model);
	public abstract boolean isValid(String username, String password);

}
