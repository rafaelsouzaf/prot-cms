package com.github.rafaelsouzaf.prot.client.mvc;

import java.util.ArrayList;
import java.util.List;

import com.github.rafaelsouzaf.prot.client.AppEvents;
import com.github.rafaelsouzaf.prot.client.ProtCMS;
import com.github.rafaelsouzaf.prot.client.Entry;
import com.github.rafaelsouzaf.prot.client.MenuCategory;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.binder.TreeBinder;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.data.TreeModelReader;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionService;
import com.extjs.gxt.ui.client.event.SourceSelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;

public class NavigationView extends View {

	private MenuCategory model;
	private ContentPanel westPanel;
	private TreeBinder<ModelData> binder;
	private TreeStore<ModelData> treeStore;

	public NavigationView(Controller controller) {
		super(controller);
	}

	protected void initialize() {
		model = (MenuCategory) Registry.get(ProtCMS.MODEL);
		SelectionService.get().addListener(
				new SelectionChangedListener<TreeModel>() {
					public void selectionChanged(SelectionChangedEvent<TreeModel> event) {
						List<TreeModel> sel = event.getSelection();
						if (sel.size() > 0) {
							TreeModel m = (TreeModel) event.getSelection().get(0);
							if (m != null && m instanceof Entry) {
								ProtCMS.showPage((Entry) m);
							}
						}
					}
				});

		westPanel = (ContentPanel) Registry.get(AppView.WEST_PANEL);
		westPanel.setScrollMode(Scroll.AUTOY);
		westPanel.setHeading("Menu");
		westPanel.setLayout(new FitLayout());
		westPanel.add(createTreeContent());
		westPanel.syncSize();
	}

	private Tree createTreeContent() {
		Tree tree = new Tree();
		tree.getStyle().setLeafIconStyle("icon-list");

		TreeLoader<ModelData> loader = new BaseTreeLoader<ModelData>(new TreeModelReader<List<ModelData>>());
		treeStore = new TreeStore<ModelData>(loader);

		binder = new TreeBinder<ModelData>(tree, treeStore);
		binder.setAutoLoad(true);
		binder.setDisplayProperty("name");

		SelectionService.get().addListener(new SourceSelectionChangedListener(binder));
		SelectionService.get().register(binder);

		loader.load(model);
		
		/**
		 * Expanded all
		 */
		List<TreeItem> items = tree.getRootItem().getItems();
		for (TreeItem treeItem: items) {
			treeItem.setExpanded(true);
		}
		
		return tree;
	}

	protected void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.HidePage) {
			binder.setSelection(new ArrayList<ModelData>());
		} else if (type == AppEvents.TabChange) {
			if (((Entry) event.getData()).getName() == "index") {
				binder.setSelection(new ArrayList<ModelData>());
			} else {
				binder.setSelection((Entry) event.getData());
			}
		}
	}

}
