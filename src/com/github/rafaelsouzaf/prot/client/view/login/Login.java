package com.github.rafaelsouzaf.prot.client.view.login;

import com.github.rafaelsouzaf.prot.client.ProtCMS;
import com.github.rafaelsouzaf.prot.client.component.ProtMessage;
import com.github.rafaelsouzaf.prot.client.view.user.UserService;
import com.github.rafaelsouzaf.prot.client.view.user.UserServiceAsync;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Login extends FormPanel {

	private UserServiceAsync service = GWT.create(UserService.class);
	
	public Login() {

		setLayout(new FlowLayout());
		setShadow(true);
		setFrame(true);
		setHeading("Prot CMS");
		setLabelWidth(75);
		setButtonAlign(HorizontalAlignment.CENTER);
		setWidth(350);

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(75);
		
		FieldSet fieldSet = new FieldSet();
		fieldSet.setLayout(layout);
		fieldSet.setHeading("Ingrese sus Datos");

		final TextField<String> username = new TextField<String>();
		username.setFieldLabel("Usuario");
		username.focus();
		fieldSet.add(username);

		final TextField<String> password = new TextField<String>();
		password.setFieldLabel("Contraseña");
		password.setPassword(true);
		fieldSet.add(password);
		
		Button btnEnvia = new Button("Enviar");
		btnEnvia.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				
				if (username.getValue() == null || password.getValue() == null) {
					MessageBox.alert("Error", "Datos invalidos.", null);
					return;
				}
				
				String tmpUsername = username.getValue().trim();
				String tmpPassword = password.getValue().trim();
				if (tmpUsername.equals("") || tmpPassword.equals("")) {
					MessageBox.alert("Error", "Datos invalidos.", null);
					return;
				}
				
				/**
				 * Consulta datos
				 */
				service.isValid(username.getValue(), password.getValue(), new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						ProtMessage.Info("Error", "Error: " + caught.getMessage());
					}
					public void onSuccess(Boolean result) {
						if (result.booleanValue()) {
							ProtCMS.showSite();
							closeLogin();
						} else {
							ProtMessage.Info("Error", "Datos incorrectos.");
						}
					}
				});
				
			}
		});
		
		Button btnCancela = new Button("Cancelar");
		btnCancela.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				reset();
				username.focus();
			}
		});

		add(fieldSet);
		addButton(btnEnvia);
		addButton(btnCancela);
		
	}
	
	private void closeLogin() {
		super.removeFromParent();
	}
	
}