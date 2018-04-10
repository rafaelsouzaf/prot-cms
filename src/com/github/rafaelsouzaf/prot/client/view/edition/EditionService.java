
package com.github.rafaelsouzaf.prot.client.view.edition;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("EditionService")
public interface EditionService extends RemoteService {
	
	public abstract List<EditionModel> getAll();
	public abstract PagingLoadResult<EditionModel> getAll(PagingLoadConfig config);
	public abstract Integer insert(EditionModel model);
	public abstract void edit(EditionModel model);
	public abstract void delete(EditionModel model);
	public abstract boolean isValid(String username, String password);

}
