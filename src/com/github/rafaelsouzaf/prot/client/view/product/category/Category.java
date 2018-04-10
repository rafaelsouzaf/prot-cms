package com.github.rafaelsouzaf.prot.client.view.product.category;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.github.rafaelsouzaf.prot.client.component.ProtFramePanel;
import com.github.rafaelsouzaf.prot.client.component.ProtMessage;
import com.github.rafaelsouzaf.prot.client.component.ProtPagingToolBar;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
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
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Category extends ProtFramePanel {

	private CategoryServiceAsync service = GWT.create(CategoryService.class);
	private Grid<CategoryModel> grid;
	private PagingToolBar toolBarPag;
	private FormBinding formBindings;
	private Window window;
	
	@Override
	public Component execute() {
		
		/**
		 * Componentes de Paginacion
		 */
		RpcProxy<PagingLoadResult<CategoryModel>> proxy = new RpcProxy<PagingLoadResult<CategoryModel>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<CategoryModel>> callback) {
				service.getAll((PagingLoadConfig) loadConfig, callback);
			}
		};
		
		final BasePagingLoader<PagingLoadResult<CategoryModel>> loader = new BasePagingLoader<PagingLoadResult<CategoryModel>>(proxy);  
		loader.setRemoteSort(true);  
		
		final ListStore<CategoryModel> store = new ListStore<CategoryModel>(loader);
		store.setMonitorChanges(true);
		
		toolBarPag = new ProtPagingToolBar(20);
		toolBarPag.bind(loader);
		
		/**
		 * Panel y Grid
		 */
		grid = new Grid<CategoryModel>(store, createColumnModel());
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.addListener(Events.Attach, new Listener<GridEvent<CategoryModel>>() {
			public void handleEvent(GridEvent<CategoryModel> be) {
				loader.load();
			}
		});
		grid.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<CategoryModel>>() {
					public void handleEvent(SelectionChangedEvent<CategoryModel> be) {
						if (be.getSelection().size() > 0) {
							formBindings.bind(be.getSelectedItem());
						} else {
							formBindings.unbind();
						}
					}
				});
		
		ContentPanel cp = new ContentPanel();
		cp.setHeading("Lista de Categorias");
		cp.setFrame(true);
		cp.setSize(800, 400);
		cp.setLayout(new RowLayout(Orientation.HORIZONTAL));
		cp.setBottomComponent(toolBarPag);
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
								
								service.delete(grid.getSelectionModel().getSelectedItem(), new AsyncCallback() {
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
		
		/**
		 * Crea window form
		 */
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
		column.setId("category");
		column.setHeader("Categoria");
		column.setWidth(70);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("description");
		column.setHeader("Descripción");
		column.setWidth(70);
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
	
	private void createWindow() {

		/**
		 * Form
		 */
		final FormPanel formPanel = new FormPanel();
		formPanel.setHeaderVisible(false);

		final LabelField id = new LabelField();
		id.setName("id");
		id.setFieldLabel("ID:");
		formPanel.add(id);

		final TextField<String> category = new TextField<String>();
		category.setName("category");
		category.setFieldLabel("Categoria");
		category.setAllowBlank(false);
		formPanel.add(category);

		final TextField<String> description = new TextField<String>();
		description.setName("description");
		description.setFieldLabel("Descripción");
		formPanel.add(description);

		/**
		 * FormBinding
		 */
		formBindings = new FormBinding(formPanel, true);
		formBindings.setStore((Store<CategoryModel>) grid.getStore());

		/**
		 * Window
		 */
		window = new Window();
		window.setSize(390, 290);
		window.setPlain(true);
		window.setModal(true);
		window.setBlinkModal(true);
		window.setHeading("Usuario");
		window.setLayout(new FitLayout());
		window.add(formPanel, new RowData(1, 1));
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

					final CategoryModel model = new CategoryModel();
					model.setCategoryName(category.getValue());
					model.setCategoryDescription(description.getValue());
					model.setCategoryCreateDate(new Date());
					model.setCategoryModifiedDate(new Date());

					service.insert(model, new AsyncCallback<Integer>() {
						public void onFailure(Throwable caught) {
							MessageBox.alert("Error", "Error: "+ caught.getMessage(), null);
						}

						public void onSuccess(Integer result) {
							model.setCategoryId(result);
							grid.getStore().add(model);
							toolBarPag.refresh();
							window.close();
							ProtMessage.Info("Dato ingresado con exito.");
						}
					});

				} else {
					
					CategoryModel model = grid.getSelectionModel().getSelectedItem();
					model.setCategoryModifiedDate(new Date());

					service.edit(model, new AsyncCallback<Integer>() {
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
		return "ProductCategoryId";
	}

}
