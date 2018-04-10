package com.github.rafaelsouzaf.prot.client.component;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.fx.Draggable;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

public abstract class ProtFramePanel extends LayoutContainer {

	private Component frameComponent;
	private ContentPanel panel = new ContentPanel();
	private ContentPanel helpPanel;

    public ProtFramePanel() {
    	setLayout(new BorderLayout());
    }
    
    public void reload() {
    	panel.remove(frameComponent);
        frameComponent = execute();
        panel.add(frameComponent);
        super.doLayout();
    }
    
	@Override
	protected void onRender(Element parent, int pos) {
		
		Button buttonUpdate = new Button("Actualizar");
		buttonUpdate.setIconStyle("icon-refresh");
		buttonUpdate.setToolTip("Recarga la página.");
		buttonUpdate.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				reload();
			}
		});

		ToggleButton buttonHelp = new ToggleButton("¿Como funciona?");
		buttonHelp.toggle(true);
		buttonHelp.setIconStyle("icon-warning");
		buttonHelp.setToolTip("Muestra/Esconde el panel de instrucciones.");
		buttonHelp.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (helpPanel != null) {
					if (helpPanel.isVisible()) {
						helpPanel.hide();
					} else {
						helpPanel.show();
					}
				}
			}
		});
		
		Button buttonLogout = new Button("Salir");
		buttonLogout.setIconStyle("icon-refresh");
		buttonLogout.setToolTip("Salir");
		buttonLogout.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				
			}
		});
		
		ToolBar toolbar = new ToolBar();
		toolbar.add(new SeparatorToolItem());
		toolbar.add(buttonUpdate);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(new FillToolItem());
		toolbar.add(buttonHelp);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(buttonLogout);
		toolbar.add(new SeparatorToolItem());
		add(toolbar, new BorderLayoutData(LayoutRegion.NORTH, 27));
		
		/**
		 * Agrega panel principal
		 */
        frameComponent = execute();
		panel.setBodyStyle("padding: 7px;");
		panel.setHeaderVisible(false);
		panel.setBorders(false);
		panel.setBodyBorder(false);
		panel.setScrollMode(Scroll.AUTO);
        panel.add(frameComponent);
        add(panel, new BorderLayoutData(LayoutRegion.CENTER));
        
        /**
         * Agrega panel de ayuda
         */
        addHelpPanel();
        
		super.onRender(parent, pos);
		
	}
	
	private void addHelpPanel() {

		String help = getHelpId();
		if (help == null || help.equals("")) {
			return;
		}
		
		ContentPanel rightPanel = new ContentPanel();
        rightPanel = new ContentPanel();
        rightPanel.setBodyStyle("padding: 7px;");
        rightPanel.setHeaderVisible(false);
        rightPanel.setBorders(false);
        rightPanel.setBodyBorder(false);

		helpPanel = new ContentPanel();
		helpPanel.setBodyStyle("padding: 7px;");
		helpPanel.setCollapsible(true);
		helpPanel.setLayout(new FitLayout());
		helpPanel.setHeading("¿Como Funciona?");
		helpPanel.setHeaderVisible(true);
		helpPanel.setFrame(true);
		helpPanel.addText("Blah blah blah blah blah blah blah blah blah blah blah blah blah blah");
		
		Draggable d = new Draggable(helpPanel, helpPanel.getHeader());
		d.setContainer(this);
		d.setUseProxy(false);
		
        rightPanel.add(helpPanel);
        
        add(rightPanel, new BorderLayoutData(LayoutRegion.EAST));

	}
	
    /**
     * En este metodo debe quedar la estructura principal
     * de la pagina que se esta creando.
     */
    public abstract Component execute();
    
    /**
     * Id del panel de ayuda ubicado al lado derecho de la pagina.
     * @return
     */
    public abstract String getHelpId();
        
}
