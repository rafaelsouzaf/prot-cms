package com.github.rafaelsouzaf.prot.client;


import com.extjs.gxt.ui.client.Style.HideMode;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class MenuModel extends BaseTreeModel {

	protected MenuModel() {
	}

	public MenuModel(String name) {
		set("name", name);
	}

	public String getName() {
		return (String) get("name");
	}

	public void addEntry(String title, LayoutContainer page) {
		add(new Entry(title, page));
	}

	public void addEntry(String title, LayoutContainer page, boolean fill) {
		add(new Entry(title, page, fill));
	}

	public void addEntry(String title, LayoutContainer page, boolean fill,
			boolean closable, HideMode hideMode) {
		add(new Entry(title, page, fill, closable, hideMode));
	}

}
