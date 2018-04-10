package com.github.rafaelsouzaf.prot.client;

import com.github.rafaelsouzaf.prot.client.mvc.AppController;
import com.github.rafaelsouzaf.prot.client.mvc.ContentController;
import com.github.rafaelsouzaf.prot.client.mvc.NavigationController;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class ProtCMS implements EntryPoint {

	public static final String MODEL = "model";

	public void onModuleLoad() {
		showSite();
//		Viewport viewport = new Viewport();
//		viewport.setLayout(new CenterLayout());
//		viewport.add(new Login());
//		RootPanel.get().add(viewport);
	}
	
	public static void showSite() {
		try {
			
//			ThemeManager.register(Slate.SLATE);
//			GXT.setDefaultTheme(Slate.SLATE, true);

			MenuCategory model = new MenuCategory();
			Registry.register(MODEL, model);

			Dispatcher dispatcher = Dispatcher.get();
			dispatcher.addController(new AppController());
			dispatcher.addController(new NavigationController());
			dispatcher.addController(new ContentController());
			dispatcher.dispatch(AppEvents.Init);

			String hash = Window.Location.getHash();

			showPage(model.findEntry("index"));

			/**
			 * Si tiene una pagina por default en
			 * la URL de la pagina
			 */
			if (!"".equals(hash)) {
				hash = hash.substring(1);
				Entry entry = model.findEntry(hash);
				if (entry != null) {
					showPage(entry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showPage(Entry entry) {
		AppEvent appEvent = new AppEvent(AppEvents.ShowPage, entry);
		appEvent.setHistoryEvent(true);
		appEvent.setToken(entry.getId());
		Dispatcher.forwardEvent(appEvent);
	}

}
