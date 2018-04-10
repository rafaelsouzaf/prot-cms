package com.github.rafaelsouzaf.prot.client;

import com.extjs.gxt.ui.client.Style.HideMode;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class Entry extends BaseTreeModel {

	private String name;
	private boolean fill;
	private boolean closable = true;
	private HideMode hideMode = HideMode.DISPLAY;

	public Entry(String name, LayoutContainer example) {
		this.name = name;
		set("name", name);
		set("example", example);
		set("path", "images/thumbs/" + getId() + ".gif");
	}

	public Entry(String name, LayoutContainer example, boolean fill) {
		this(name, example);
		this.fill = fill;
	}

	public Entry(String name, LayoutContainer example, boolean fill,
			boolean closable) {
		this(name, example, fill);
		this.closable = closable;
	}

	public Entry(String name, LayoutContainer example, boolean fill,
			boolean closable, HideMode hideMode) {
		this(name, example, fill, closable);
		this.setHideMode(hideMode);
	}

	protected Entry() {

	}

	public LayoutContainer getExample() {
		return (LayoutContainer) get("example");
	}

	public String getId() {
		if (name.equals("% Columns")) {
			return "percentcolumns";
		}
		return name.replaceAll(" ", "").toLowerCase();
	}

	public HideMode getHideMode() {
		return hideMode;
	}

	public String getName() {
		return (String) get("name");
	}

	public boolean isClosable() {
		return closable;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public void setHideMode(HideMode hideMode) {
		this.hideMode = hideMode;
	}

	public String toString() {
		return getName();
	}

}
