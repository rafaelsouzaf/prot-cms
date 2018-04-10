package com.github.rafaelsouzaf.prot.client.view.edition;

import com.github.rafaelsouzaf.prot.client.component.ProtComboBoxProduct;
import com.github.rafaelsouzaf.prot.client.component.ProtFramePanel;
import com.github.rafaelsouzaf.prot.client.view.product.ProductModel;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DateTimePropertyEditor;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class EditionView extends ProtFramePanel {

//	private EditionServiceAsync service = GWT.create(EditionService.class);
	
	@Override
	public Component execute() {
		ContentPanel panel = new ContentPanel();
		panel.setBorders(false);
		panel.setBodyBorder(false);
		panel.setHeaderVisible(false);
		
		panel.add(showPanel1());
		panel.add(new Html("<br>"));
		panel.add(showPanel2());
		return panel;
	}
	
	public Component showPanel1() {
		
		/**
		 * Panel creacion
		 */
		ContentPanel panel = new ContentPanel();
		panel.setLayout(new FitLayout());
		panel.setCollapsible(true);
		panel.setHeaderVisible(true);
		panel.setHeading("Paso 1 de 6 - Crear Nueva Edición");
		panel.setHeight(200);
		panel.setFrame(true);
		
		/**
		 * Form
		 */
		final FormPanel formPanel = new FormPanel();
		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);

		final TextField<String> name = new TextField<String>();
		name.setName("name");
		name.setFieldLabel("Nombre Edición");
		name.setAllowBlank(false);
		formPanel.add(name);

		DateField date = new DateField();
		date.setPropertyEditor(new DateTimePropertyEditor("dd/MM/yyyy"));
		date.setName("date");
		date.setFieldLabel("Fecha Edición");
		date.setAllowBlank(false);
		formPanel.add(date);
		
		final LabelField urlProducto = new LabelField();
		
		ProtComboBoxProduct product = new ProtComboBoxProduct();
		date.setName("productId");
		product.setFieldLabel("Producto");
		product.setAllowBlank(false);
		product.addSelectionChangedListener(new SelectionChangedListener<ProductModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<ProductModel> se) {
				urlProducto.setValue(se.getSelectedItem().getProductUrl());
			}
		});
		formPanel.add(product);
		
		urlProducto.setName("");
		urlProducto.setValue("");
		urlProducto.setFieldLabel("Url Producto:");
		formPanel.add(urlProducto);

		Button buttonSave = new Button("Continuar");
		buttonSave.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!formPanel.isValid()) {
					MessageBox.alert("Error", "Datos incorrectos.", null);
				}
				
			}
		});

		panel.add(formPanel);
		panel.addButton(buttonSave);
		
		return panel;

	}
	
	public Component showPanel2() {
		
		/**
		 * Panel
		 */
		ContentPanel panel = new ContentPanel();
//		panel.setLayout(new FitLayout());
		panel.setCollapsible(true);
		panel.setHeaderVisible(true);
		panel.setHeading("Paso 2 de 6 - Buscar Archivos");
//		panel.setHeight(450);
		panel.setFrame(true);
		panel.setBodyBorder(false);
		panel.setBorders(false);
		
		/**
		 * Panel Ordenacion
		 */
		panel.add(new ImageOrganizerExample());
		
		Button button = new Button("Continuar");
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
			}
		});

		panel.addButton(button);
		return panel;

	}
	
	@Override
	public String getHelpId() {
		return "EditionNewId";
	}

}
