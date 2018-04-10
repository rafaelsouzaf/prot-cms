package com.github.rafaelsouzaf.prot.client.mvc;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView extends View {

	public static final String CENTER_PANEL = "centerPanel";
	public static final String WEST_PANEL = "westPanel";
	public static final String NORTH_PANEL = "northPanel";
	public static final String SOUTH_PANEL = "southPanel";

	private Viewport viewport;
	private ContentPanel centerPanel;
	private ContentPanel westPanel;

	public AppView(Controller controller) {
		super(controller);
	}

	protected void handleEvent(AppEvent event) {

	}

	protected void initialize() {
		viewport = new Viewport();
		viewport.setLayout(new BorderLayout());
		createNorth();
		createWest();
		createCenter();
		createSouth();

		RootPanel rootPanel = RootPanel.get();
		if (rootPanel != null) {
			rootPanel.add(viewport);
		}
		
	}

	private void createCenter() {
		centerPanel = new ContentPanel();
		centerPanel.setBorders(false);
		centerPanel.setBodyBorder(false);
		centerPanel.setHeaderVisible(false);
		centerPanel.setLayout(new FitLayout());

		BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
		data.setMargins(new Margins(5, 5, 5, 0));
		viewport.add(centerPanel, data);
		Registry.register(CENTER_PANEL, centerPanel);
	}

	private void createWest() {
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 220, 150, 320);
		data.setMargins(new Margins(7, 5, 5, 5));
		data.setCollapsible(true);
		westPanel = new ContentPanel();

		viewport.add(westPanel, data);
		Registry.register(WEST_PANEL, westPanel);
	}
	
	private void createNorth() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n <table border='0' width='100%' cellspacing='0' cellpadding='0' bgcolor='#FCA624'>");
		sb.append("\n 	<tr>");
		sb.append("\n 		<td width='100%' height='15' colspan=2 bgcolor='#9C0204'></td>");
		sb.append("\n 	</tr>");
		sb.append("\n 	<tr>");
		sb.append("\n 		<td width='50%' height='55'>");
		sb.append("\n 			<img border='0' src='images/logoPapelDigital.png' width='242' height='47'>");
		sb.append("\n 		</td>");
		sb.append("\n 		<td width='50%' height='55' align='right' valign='top'>");
		sb.append("\n 			<font face='Trebuchet MS' size='2'>Consorcio Periodístico de Chile S.A.&nbsp;&nbsp;&nbsp; |&nbsp;&nbsp;&nbsp; Salir</font>&nbsp;&nbsp;");
		sb.append("\n 		</td>");
		sb.append("\n 	</tr>");
		sb.append("\n </table>");

		HtmlContainer northPanel = new HtmlContainer(sb.toString());

		BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 70);
		data.setMargins(new Margins(0, 0, 0, 0));
		viewport.add(northPanel, data);
		Registry.register(NORTH_PANEL, northPanel);
	}
	
	private void createSouth() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n <table style='opacity:0.4;filter:alpha(opacity=40)' border='1' width='100%' cellspacing='1' cellpadding='0' style='border-collapse: collapse' bgcolor='#EEEEEE'>");
		sb.append("\n 	<tr>");
		sb.append("\n 		<td height='45'><p align='center'><font face='Trebuchet MS' size='2'>Copesa® 2006-2009<br>Derechos Reservados. Prohibída su reproducción o copia.</font></td>");
		sb.append("\n 	</tr>");
		sb.append("\n </table>");

		HtmlContainer panel = new HtmlContainer(sb.toString());

		BorderLayoutData data = new BorderLayoutData(LayoutRegion.SOUTH, 50);
		data.setMargins(new Margins(0, 5, 3, 5));
		viewport.add(panel, data);
		Registry.register(SOUTH_PANEL, panel);
	}

}
