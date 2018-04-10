package com.github.rafaelsouzaf.prot.client.view.index;

import java.util.List;

import com.github.rafaelsouzaf.prot.client.component.ProtInfo;
import com.github.rafaelsouzaf.prot.client.view.product.ProductModel;
import com.github.rafaelsouzaf.prot.client.view.product.ProductService;
import com.github.rafaelsouzaf.prot.client.view.product.ProductServiceAsync;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

public class Index extends LayoutContainer {

	@Override
	protected void onRender(Element parent, int pos) {

		super.onRender(parent, pos);
		
		/**
		 * INSERT
		 */
//		{
//			ProductModel model = new ProductModel();
//			model.setProductName("nombre");
//			model.setProductDescription("desc");
//			model.setProductCode("CD");
//			model.setProductUrl("http://");
//			model.setProductCreateDate(new Date());
//			model.setProductModifiedDate(new Date());
//			
//			CategoryModel category = new CategoryModel();
//			category.setCategoryName("categoria");
//			category.setCategoryDescription("descripcao");
//			category.setCategoryModifiedDate(new Date());
//			category.setCategoryCreateDate(new Date());
//			
//			model.addCategory(category);
//			
//			ProductServiceAsync service = GWT.create(ProductService.class);
//			service.insert(model, new AsyncCallback<Integer>() {
//				public void onFailure(Throwable caught) {
//					caught.printStackTrace();
//					MessageBox.alert("Error", "Error: "+ caught.getMessage(), null);
//				}
//				
//				public void onSuccess(Integer result) {
//					System.out.println("INSERT: " + result);
//				}
//			});
//		}
		
		/**
		 * SELECT
		 */
		{
//			ProductModel model = new ProductModel();
//			model.setProductId(19);
			
//			CategoryModel category = new CategoryModel();
//			category.setCategoryName("categoria 2");
//			category.setCategoryDescription("descripcao 2");
//			category.setCategoryModifiedDate(new Date());
////			category.setCategoryCreateDate(new Date());
//			model.addCategory(category);
			
			ProductServiceAsync service = GWT.create(ProductService.class);
			service.getAll(new AsyncCallback<List<ProductModel>>() {
				public void onFailure(Throwable caught) {
					MessageBox.alert("Error", "Error: " + caught.getMessage(), null);
					System.out.println("CODIGO: " + ((StatusCodeException)caught).getStatusCode());
					caught.printStackTrace();
				}
				public void onSuccess(List<ProductModel> result) {
					System.out.println("Total: " + result.size());
					for (ProductModel productModel : result) {
						int total = 0;
						if (productModel.getCategories() != null) {
							total = productModel.getCategories().size();
						}
						System.out.println("---------- ID: " + productModel.getProductId() + ", Categories: " + total);
					}
				}
			});
		}
		
		/**
		 * UPDATE
		 */
//		{
//			ProductModel model = new ProductModel();
//			model.setProductId(19);
//			model.setProductName("nombre editado 3");
//			model.setProductDescription("desc editado 3");
//			model.setProductCode("CD editado 3");
//			model.setProductModifiedDate(new Date());
//			
////			CategoryModel category = new CategoryModel();
////			category.setCategoryName("categoria 2");
////			category.setCategoryDescription("descripcao 2");
////			category.setCategoryModifiedDate(new Date());
//////			category.setCategoryCreateDate(new Date());
////			model.addCategory(category);
//			
//			ProductServiceAsync service = GWT.create(ProductService.class);
//			service.edit(model, new AsyncCallback<Integer>() {
//				public void onFailure(Throwable caught) {
//					caught.printStackTrace();
//					MessageBox.alert("Error", "Error: "+ caught.getMessage(), null);
//				}
//				
//				public void onSuccess(Integer result) {
//					System.out.println("EDIT" + result);
//				}
//			});
//		}
		
		ProtInfo.display("Test Message", "You name is asdasd");
		
	}
}
