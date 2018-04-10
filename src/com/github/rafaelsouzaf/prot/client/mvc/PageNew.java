package com.github.rafaelsouzaf.prot.client.mvc;

import com.github.rafaelsouzaf.prot.client.Entry;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class PageNew extends ContentPanel {

	protected Entry entry;

	public LayoutContainer getContent() {
		return entry.getExample();
	}

	public PageNew(final Entry entry) {
		this.entry = entry;

		setBorders(false);
		setBodyBorder(false);
		setHeaderVisible(false);

		addListener(Events.Adopt, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				if (getParent() != null && getParent() instanceof TabItem) {
					TabItem t = (TabItem) getParent();
					t.setHideMode(entry.getHideMode());
				}
			}
		});
		
		setScrollMode(Scroll.AUTO);
		setLayout(new FitLayout());
		setHideMode(entry.getHideMode());
		add(entry.getExample());

	}

}
