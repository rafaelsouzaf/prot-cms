
package com.github.rafaelsouzaf.prot.client.view.product;

import java.util.List;


import com.github.rafaelsouzaf.prot.client.util.FileModel;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ProductService")
public interface ProductService extends RemoteService {
	
	public abstract List<ProductModel> getAll();
	public abstract Integer insert(ProductModel model);
	public abstract void edit(ProductModel model);
	public abstract void delete(ProductModel model);
	
	public abstract List<FileModel> getPhotos();

}
