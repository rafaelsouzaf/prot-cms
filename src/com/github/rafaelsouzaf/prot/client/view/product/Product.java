package com.github.rafaelsouzaf.prot.client.view.product;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.github.rafaelsouzaf.prot.client.component.ProtFramePanel;
import com.github.rafaelsouzaf.prot.client.component.ProtMessage;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Product extends ProtFramePanel {

	private ProductServiceAsync service = GWT.create(ProductService.class);
	private FormBinding formBindings;
	private Grid<ProductModel> grid;
	private Window window;

	/**
	 * Fields
	 */
	LabelField id;
	TextField<String> code;
	TextField<String> name;
	TextField<String> description;
	TextField<String> url;

	@Override
	public Component execute() {

		ContentPanel cp = new ContentPanel();
		cp.setHeading("Lista de Productos");
		cp.setFrame(true);
		cp.setSize(800, 400);
		cp.setLayout(new RowLayout(Orientation.HORIZONTAL));

		grid = new Grid<ProductModel>(createStore(), createColumnModel());
		grid.getView().setEmptyText("");
		grid.setAutoExpandColumn("name");
		grid.setBorders(true);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<ProductModel>>() {
					public void handleEvent(SelectionChangedEvent<ProductModel> be) {
						if (be.getSelection().size() > 0) {
							formBindings.bind(be.getSelectedItem());
						} else {
							formBindings.unbind();
						}
					}
				});
		cp.add(grid, new RowData(1, 1));

		/**
		 * Barra superior y Butones
		 */
		Button buttonAdd = new Button("Agregar", "icon-add");
		buttonAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				grid.getSelectionModel().deselectAll();
				window.show();
			}
		});

		Button buttonEdit = new Button("Editar", "icon-edit");
		buttonEdit.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (grid.getSelectionModel().getSelectedItem() != null) {
					window.show();
				}
			}
		});

		Button buttonDelete = new Button("Borrar", "icon-delete");
		buttonDelete.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (grid.getSelectionModel().getSelectedItem() != null) {

					MessageBox.confirm("Confirm", "¿Está seguro que desea borrar?",new Listener<MessageBoxEvent>() {
						public void handleEvent(MessageBoxEvent be) {
							String resp = be.getButtonClicked().getText();
							if (resp.equalsIgnoreCase("YES")|| resp.equalsIgnoreCase("SI")) {

								service.delete(grid.getSelectionModel().getSelectedItem(),new AsyncCallback() {
									public void onFailure(Throwable caught) {
										MessageBox.alert("Error", "Error: "+ caught.getMessage(),null);
									}

									public void onSuccess(Object result) {
										grid.getStore().remove(grid.getSelectionModel().getSelectedItem());
										ProtMessage.Info("Dato borrado con exito.");
									}
								});

							}

						}
					});

				}
			}
		});

		ToolBar toolBar = new ToolBar();
		toolBar.add(buttonAdd);
		toolBar.add(new SeparatorToolItem());
		toolBar.add(buttonEdit);
		toolBar.add(new SeparatorToolItem());
		toolBar.add(buttonDelete);
		cp.setTopComponent(toolBar);

		createWindow();

		return cp;

	}

	private ColumnModel createColumnModel() {

		List<ColumnConfig> configs = new LinkedList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		column.setId("id");
		column.setHeader("ID");
		column.setWidth(25);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("code");
		column.setHeader("Código");
		column.setWidth(50);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("name");
		column.setHeader("Nombre");
		column.setWidth(75);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("description");
		column.setHeader("Descripción");
		column.setAlignment(HorizontalAlignment.LEFT);
		column.setWidth(200);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("url");
		column.setHeader("URL");
		column.setAlignment(HorizontalAlignment.LEFT);
		column.setWidth(200);
		configs.add(column);

		column = new ColumnConfig("createDate", "Fecha Creación", 90);
		column.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yy HH:mm"));
		column.setAlignment(HorizontalAlignment.CENTER);
		configs.add(column);

		column = new ColumnConfig("modifiedDate", "Fecha Modificación", 90);
		column.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yy HH:mm"));
		column.setAlignment(HorizontalAlignment.CENTER);
		configs.add(column);

		ColumnModel cm = new ColumnModel(configs);
		return cm;

	}
	
	private ListStore<ProductModel> createStore() {
		final ListStore<ProductModel> store = new ListStore<ProductModel>();
		store.setMonitorChanges(true);
		service.getAll(new AsyncCallback<List<ProductModel>>() {
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error", "Error: " + caught.getMessage(), null);
			}
			public void onSuccess(List<ProductModel> result) {
				store.add(result);
			}
		});
		return store;
	}

	private void createWindow() {

		/**
		 * Form
		 */
		final FormPanel formPanel = new FormPanel();
		formPanel.setHeaderVisible(false);

		id = new LabelField();
		id.setName("id");
		id.setFieldLabel("ID:");
		formPanel.add(id);

		code = new TextField<String>();
		code.setName("code");
		code.setFieldLabel("Código");
		code.setAllowBlank(false);
		formPanel.add(code);

		name = new TextField<String>();
		name.setName("name");
		name.setFieldLabel("Nombre");
		name.setAllowBlank(false);
		formPanel.add(name);

		description = new TextField<String>();
		description.setName("description");
		description.setFieldLabel("Descripción");
		formPanel.add(description);

		url = new TextField<String>();
		url.setName("url");
		url.setFieldLabel("URL");
		formPanel.add(url);

		formBindings = new FormBinding(formPanel, true);
		formBindings.setStore((Store<ProductModel>) grid.getStore());

		/**
		 * Window
		 */
		window = new Window();
		window.setSize(350, 230);
		window.setPlain(true);
		window.setModal(true);
		window.setBlinkModal(true);
		window.setHeading("Producto");
		window.setLayout(new FitLayout());
		window.add(formPanel, new RowData(.3, 1));
		window.addWindowListener(new WindowListener() {
			@Override
			public void windowDeactivate(WindowEvent we) {
				super.windowDeactivate(we);
				if (grid.getStore().getModifiedRecords().size() > 0) {
					grid.getStore().rejectChanges();
				}
			}
		});

		Button buttonSave = new Button("Grabar");
		buttonSave.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {

				if (!formPanel.isValid()) {
					ProtMessage.Info("Error. Datos incorrectos.");
					return;
				}

				if (id.getValue() == null || id.getValue().equals("")) {

					final ProductModel product = new ProductModel();
					product.setProductCode(code.getValue());
					product.setProductName(name.getValue());
					product.setProductDescription(description.getValue());
					product.setProductUrl(url.getValue());
					product.setProductCreateDate(new Date());
					product.setProductModifiedDate(new Date());

					service.insert(product, new AsyncCallback<Integer>() {
						public void onFailure(Throwable caught) {
							MessageBox.alert("Error", "Error: "+ caught.getMessage(), null);
						}

						public void onSuccess(Integer result) {
							product.setProductId(result);
							grid.getStore().add(product);
							grid.reconfigure(grid.getStore(), grid.getColumnModel());
							window.close();
							ProtMessage.Info("Dato ingresado con exito.");
						}
					});

				} else {

					ProductModel product = grid.getSelectionModel().getSelectedItem();
					product.setProductModifiedDate(new Date());

					service.edit(product, new AsyncCallback<Integer>() {
						public void onFailure(Throwable caught) {
							MessageBox.alert("Error", "Error: " + caught.getMessage(), null);
						}

						public void onSuccess(Integer result) {
							grid.getStore().commitChanges();
							window.close();
							ProtMessage.Info("Dato actualizado con exito.");
						}
					});

				}

			}
		});

		Button buttonCancel = new Button("Cancelar");
		buttonCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				grid.getStore().rejectChanges();
				window.close();
			}
		});

		window.addButton(buttonSave);
		window.addButton(buttonCancel);

	}

	@Override
	public String getHelpId() {
		return "ProductId";
	}

}
