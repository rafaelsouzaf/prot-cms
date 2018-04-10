
package com.github.rafaelsouzaf.prot.client.view.product.category;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CategoryService")
public interface CategoryService extends RemoteService {
	
	public abstract List<CategoryModel> getAll();
	public abstract PagingLoadResult<CategoryModel> getAll(PagingLoadConfig config);
	public abstract Integer insert(CategoryModel model);
	public abstract void edit(CategoryModel model);
	public abstract void delete(CategoryModel model);
	public abstract boolean isValid(String username, String password);

}
