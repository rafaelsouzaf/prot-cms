package com.github.rafaelsouzaf.prot.client.view.edition;

import java.util.List;

import com.github.rafaelsouzaf.prot.client.util.FileModel;
import com.github.rafaelsouzaf.prot.client.view.product.ProductService;
import com.github.rafaelsouzaf.prot.client.view.product.ProductServiceAsync;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.dnd.ListViewDragSource;
import com.extjs.gxt.ui.client.dnd.TreeDragSource;
import com.extjs.gxt.ui.client.dnd.TreeDropTarget;
import com.extjs.gxt.ui.client.dnd.DND.Feedback;
import com.extjs.gxt.ui.client.dnd.DND.Operation;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.event.DNDListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ImageOrganizerExample extends LayoutContainer {

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		LayoutContainer container = new LayoutContainer();
//		container.setSize(650, 300);
		container.setHeight(400);
		container.setBorders(true);
		container.setLayout(new BorderLayout());
		container.setStyleName("image-chose-personal-background");
		
		final Tree tree = new Tree();
		
		TreeItem album1 = new TreeItem("Album 1");
		album1.setLeaf(false);
		album1.setExpanded(true);
		
		TreeItem album2 = new TreeItem("Album 2");
		album2.setLeaf(false);
		album2.setExpanded(true);
		
		TreeItem album3 = new TreeItem("Album 3");
		album3.setLeaf(false);
		album3.setExpanded(true);
		
		tree.getRootItem().add(album1);
		tree.getRootItem().add(album2);
		tree.getRootItem().add(album3);

		ContentPanel west = new ContentPanel();
		west.setHeading("Canales");
		west.add(tree);
		west.setScrollMode(Scroll.AUTO);

		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST);
		westData.setMargins(new Margins(5, 0, 5, 5));
		westData.setSplit(true);
		container.add(west, westData);

		ContentPanel center = new ContentPanel();
		center.setHeading("Paginas");
		center.setScrollMode(Scroll.AUTO);

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setMargins(new Margins(5));
		container.add(center, centerData);

		final ProductServiceAsync service = GWT.create(ProductService.class);
		
		RpcProxy<List<FileModel>> proxy = new RpcProxy<List<FileModel>>() {
			@Override
			protected void load(Object loadConfig, AsyncCallback<List<FileModel>> callback) {
				service.getPhotos(callback);
			}
		};

		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(proxy, new BeanModelReader());
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		loader.load();

		ListView<BeanModel> view = new ListView<BeanModel>() {
			@Override
			protected BeanModel prepareData(BeanModel model) {
				FileModel photo = model.getBean();
				long size = photo.getSize() / 1000;
				model.set("shortName", Util.ellipse(photo.getName(), 15));
				model.set("sizeString", NumberFormat.getFormat("0").format(size) + "k");
				model.set("dateString", DateTimeFormat.getMediumDateTimeFormat().format(photo.getDate()));
				return model;
			}
		};
		view.setId("img-chooser-view");
		view.setTemplate(getTemplate());
		view.setBorders(false);
		view.setStore(store);
		view.setItemSelector("div.thumb-wrap");

		center.add(view);

		new ListViewDragSource(view);
		
		TreeDragSource source = new TreeDragSource(tree);
		source.addDNDListener(new DNDListener() {
			@Override
			public void dragStart(DNDEvent e) {
				TreeItem item = tree.findItem(e.getTarget());
				if (item != null && item == tree.getRootItem().getItem(0) && tree.getRootItem().getItemCount() == 1) {
					e.setCancelled(true);
					e.getStatus().setStatus(false);
					return;
				}
				super.dragStart(e);
			}
		});

		TreeDropTarget target = new TreeDropTarget(tree) {
			@Override
			protected void handleAppendDrop(DNDEvent event, TreeItem item) {
				List<BeanModel> sel = event.getData();
				for (BeanModel bean: sel) {
					FileModel photo = bean.getBean();
					TreeItem newItem = new TreeItem();
					newItem.setText(photo.getName().substring(0, photo.getName().indexOf(".")));
					item.add(newItem);
				}
			}
		};
		target.setAllowSelfAsSource(true);
		target.setOperation(Operation.MOVE);
		target.setFeedback(Feedback.APPEND);

		add(container);
	}

	private native String getTemplate() /*-{
		return ['<tpl for=".">', 
		'<div class="thumb-wrap" id="{name}" style="border: 1px solid white">', 
		'<div class="thumb"><img src="{path}" title="{name}"></div>', 
		'<span>{shortName}</span></div>', 
		'</tpl>'].join("");
	}-*/;
}