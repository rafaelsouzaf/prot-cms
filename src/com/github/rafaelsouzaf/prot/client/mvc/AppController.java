package com.github.rafaelsouzaf.prot.client.mvc;

import com.github.rafaelsouzaf.prot.client.AppEvents;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class AppController extends Controller {

	private AppView appView;

	public AppController() {
		appView = new AppView(this);
		registerEventTypes(AppEvents.Init);
	}

	public void handleEvent(AppEvent event) {
		if (event.getType() == AppEvents.Init) {
			forwardToView(appView, event);
		}
	}

}
