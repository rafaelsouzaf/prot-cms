package com.github.rafaelsouzaf.prot.client;

import java.util.ArrayList;
import java.util.List;

import com.github.rafaelsouzaf.prot.client.view.edition.EditionView;
import com.github.rafaelsouzaf.prot.client.view.index.Index;
import com.github.rafaelsouzaf.prot.client.view.product.Product;
import com.github.rafaelsouzaf.prot.client.view.product.category.Category;
import com.github.rafaelsouzaf.prot.client.view.user.UserView;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;

public class MenuCategory extends BaseTreeModel {

	protected List<Entry> entries = new ArrayList<Entry>();

	public MenuCategory() {

		set("index", new Entry("Inicio", new Index(), true, false));
		
		MenuModel grids = new MenuModel("Prot CMS");
		grids.addEntry("Ediciones", new EditionView());
		grids.addEntry("Productos", new Product());
		grids.addEntry("Categorias", new Category());
		grids.addEntry("Usuarios", new UserView());
		grids.addEntry("Editable Grid", new Product());
		grids.addEntry("Xml Grid", new Product());
		add(grids);

		{
			MenuModel layouts = new MenuModel("Layouts 2");
			layouts.addEntry("AccordionLayout", new Product());
			layouts.addEntry("AnchorLayout", new Product());
			layouts.addEntry("BorderLayout", new Product(), true);
			layouts.addEntry("CardLayout", new Product());
			layouts.addEntry("CenterLayout", new Product(), true);
			layouts.addEntry("RowLayout", new Product(), true);
			layouts.addEntry("VBoxLayout", new Product(), true);
			layouts.addEntry("HBoxLayout", new Product(), true);
			add(layouts);
		}
		
		{
			MenuModel layouts = new MenuModel("Layouts 3");
			layouts.addEntry("AccordionLayout", new Product());
			layouts.addEntry("AnchorLayout", new Product());
			layouts.addEntry("BorderLayout", new Product(), true);
			layouts.addEntry("CardLayout", new Product());
			layouts.addEntry("CenterLayout", new Product(), true);
			layouts.addEntry("RowLayout", new Product(), true);
			layouts.addEntry("VBoxLayout", new Product(), true);
			layouts.addEntry("HBoxLayout", new Product(), true);
			add(layouts);
		}
		

		loadEntries(this);
	}

	public Entry findEntry(String name) {
		if (get(name) != null) {
			return (Entry) get(name);
		}
		for (Entry entry : getEntries()) {
			if (name.equals(entry.getId())) {
				return entry;
			}
		}
		return null;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	private void loadEntries(TreeModel model) {
		for (ModelData child : model.getChildren()) {
			if (child instanceof Entry) {
				entries.add((Entry) child);
			} else if (child instanceof MenuModel) {
				loadEntries((MenuModel) child);
			}
		}
	}
}
