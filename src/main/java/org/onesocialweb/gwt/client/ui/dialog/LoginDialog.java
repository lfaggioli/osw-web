/*
 *  Copyright 2010 Vodafone Group Services Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  2011-01-18 Modified by Luca Faggioli Copyright 2011 Openliven S.r.l
 *  added fields for Name and Confirm Password in the registration form
 *    
 */
package org.onesocialweb.gwt.client.ui.dialog;

import org.onesocialweb.gwt.client.OswClient;
import org.onesocialweb.gwt.client.handler.LoginHandler;
import org.onesocialweb.gwt.client.i18n.CustomValidation;
import org.onesocialweb.gwt.client.i18n.CustomValidationMessages;
import org.onesocialweb.gwt.client.i18n.UserInterfaceMessages;
import org.onesocialweb.gwt.client.i18n.UserInterfaceText;
import org.onesocialweb.gwt.client.ui.widget.ErrorLabel;
import org.onesocialweb.gwt.client.ui.widget.FieldLabel;
import org.onesocialweb.gwt.client.ui.widget.StyledLabel;
import org.onesocialweb.gwt.client.validation.CustomLabelTextAction;
import org.onesocialweb.gwt.model.GwtFormField;
import org.onesocialweb.gwt.service.ERequestCallback;
import org.onesocialweb.gwt.service.GwtDataForm;
import org.onesocialweb.gwt.service.OswServiceFactory;
import org.onesocialweb.gwt.service.RequestCallback;

import com.google.code.gwt.storage.client.Storage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.validators.StringLengthValidator;
import eu.maydu.gwt.validation.client.validators.multifield.MultiStringsEqualsValidator;
import eu.maydu.gwt.validation.client.validators.standard.NotEmptyValidator;
import eu.maydu.gwt.validation.client.validators.strings.EmailValidator;

public class LoginDialog extends AbstractDialog {

	// internationalization
	private UserInterfaceText uiText = (UserInterfaceText) GWT.create(UserInterfaceText.class);
	private UserInterfaceMessages uiMessages = (UserInterfaceMessages) GWT.create(UserInterfaceMessages.class);
	
	// private final LoginHandler loginHandler;
	private static LoginDialog instance;
	private TabPanel tabpanel = new TabPanel();
	private int currentTab = 0;
	private Button buttonLogin = new Button(uiText.Login());
	private Button buttonRegister = new Button(uiText.Register());
	private CustomValidationMessages customValidationMessages = new CustomValidationMessages();
	private ValidationProcessor validator = new DefaultValidationProcessor(
			new CustomValidation(customValidationMessages));

	private final ErrorLabel loginError = new ErrorLabel();
	private final TextBox usernameText = new TextBox();
	private final PasswordTextBox passwordText = new PasswordTextBox();
	private final CheckBox rememberme = new CheckBox(uiText.RememberMe());
	private final FlowPanel registerflow = new FlowPanel();

	private LoginHandler handler;

	public static LoginDialog getInstance() {
		if (instance == null) {
			instance = new LoginDialog();
		}
		return instance;
	}

	public void showDialog(LoginHandler handler) {
		this.handler = handler;
		usernameText.setText("");
		passwordText.setText("");
		center();
		usernameText.setFocus(true);
	}

	protected void setCurrentTab(int index) {
		currentTab = index;

		if (currentTab == 0) {
			buttonRegister.setVisible(false);
			buttonLogin.setVisible(true);
			center();
		} else {
			buttonRegister.setVisible(true);
			buttonLogin.setVisible(false);
			center();
		}
	}

	protected void complete() {

		handler.handleLogin(true);
		hide();
	}

	private LoginDialog() {

		// overall widgets
		VerticalPanel vpanel1 = new VerticalPanel();
		HorizontalPanel buttoncontainer = new HorizontalPanel();

		// tab contents
		FieldLabel username = new FieldLabel(uiText.YourUsername());
		FieldLabel password = new FieldLabel(uiText.YourPassword());
		FlowPanel loginflow = new FlowPanel();

		FlowPanel registerflow = new FlowPanel();
		
		FieldLabel registerCodeReqMessage = new FieldLabel(
				OswClient.getInstance().getPreference("registration_code_req_message"));
		registerCodeReqMessage.setStyleName("important");

		
		FieldLabel usernameRegister = new FieldLabel(uiText.ChooseUsername());
		FieldLabel nameRegister = new FieldLabel(uiText.EnterYourName());
		FieldLabel passwordRegister = new FieldLabel(uiText.ChoosePassword());
		FieldLabel confirmPasswordRegister = new FieldLabel(uiText.ConfirmPassword());

		FieldLabel emailRegister = new FieldLabel(uiText.EnterYourEmail());
		FieldLabel codeRegister = new FieldLabel(uiText.EnterCode());
		final TextBox usernameTextRegister = new TextBox();
		final PasswordTextBox passwordTextRegister = new PasswordTextBox();
		final PasswordTextBox confirmPasswordTextRegister = new PasswordTextBox();

		final TextBox nameTextRegister = new TextBox();
		final TextBox emailTextRegister = new TextBox();
		final TextBox codeTextRegister = new TextBox();
		ErrorLabel usernameRegisterError = new ErrorLabel();
		ErrorLabel passwordRegisterError = new ErrorLabel();
		ErrorLabel confirmPasswordRegisterError = new ErrorLabel();
		ErrorLabel nameRegisterError = new ErrorLabel();
		ErrorLabel emailRegisterError = new ErrorLabel();
		ErrorLabel codeRegisterError = new ErrorLabel();
		

		registerflow.setStyleName("tabcontentflowlayout");
		loginflow.setStyleName("tabcontentflowlayout");

		tabpanel.add(loginflow, uiText.Login());
		
		// if registration is enabled
		if (OswClient.getInstance().getPreference("registration_allowed").equals("true")) {			
				tabpanel.add(registerflow, uiText.Register());			
		}
		
		rememberme.addStyleName("checkbox");

		loginflow.add(loginError);
		loginflow.add(username);
		loginflow.add(usernameText);
		loginflow.add(password);
		loginflow.add(passwordText);
		loginflow.add(rememberme);

		if(!"".equals(registerCodeReqMessage.getText().trim()))
			registerflow.add(registerCodeReqMessage);
		registerflow.add(usernameRegister);
		registerflow.add(usernameTextRegister);
		registerflow.add(usernameRegisterError);
		registerflow.add(passwordRegister);
		registerflow.add(passwordTextRegister);
		registerflow.add(passwordRegisterError);

		registerflow.add(confirmPasswordRegister);
		registerflow.add(confirmPasswordTextRegister);
		registerflow.add(confirmPasswordRegisterError);

		registerflow.add(nameRegister);
		registerflow.add(nameTextRegister);
		registerflow.add(nameRegisterError);
		registerflow.add(emailRegister);
		registerflow.add(emailTextRegister);
		registerflow.add(emailRegisterError);
		
		StyledLabel requestLabel = new StyledLabel("replies-link", "  "+ uiText.RequestCode());
		FlowPanel requestLabelPanel = new FlowPanel();
		requestLabelPanel.add(requestLabel);
				
		
		
		// if registration via a registration code only is enabled
		if (OswClient.getInstance().getPreference("registration_code").equals("true")) {
			registerflow.add(codeRegister);
			registerflow.add(codeTextRegister);
			registerflow.add(codeRegisterError);
		}
		
		if (OswClient.getInstance().getPreference("registration_email").equals("true")) {
			registerflow.add(requestLabelPanel);
		}

		buttoncontainer.add(buttonLogin);
		buttoncontainer.add(buttonRegister);
		buttoncontainer.setStyleName("dialogButtons");

		vpanel1.add(tabpanel);
		vpanel1.add(buttoncontainer);
		tabpanel.selectTab(0);
		setCurrentTab(0);
		setWidget(vpanel1);

		setWidth("300px");
		tabpanel.setWidth(Integer.toString(this.getOffsetWidth() - 20));

		this.setAutoHideEnabled(false);

		// this.handler = handler;
		// build and init dialog
		setText(uiMessages.WelcomeToServer(OswClient.getInstance().getPreference("service_name")));
		// center();

		tabpanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				setCurrentTab(event.getSelectedItem());
			}
		});

		passwordText.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent arg0) {

				char keyCode = arg0.getCharCode();

				if (keyCode == KeyCodes.KEY_ENTER) {
					processLogin();
				}

			}

		});

		usernameText.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent arg0) {

				char keyCode = arg0.getCharCode();

				if (keyCode == KeyCodes.KEY_ENTER) {
					processLogin();
				}

			}

		});

		buttonRegister.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {			

				boolean success = validator.validate();
				
				GwtDataForm form = new GwtDataForm();
				
				GwtFormField field = new GwtFormField("username");								
				field.addValue(usernameTextRegister.getText());
				form.addField(field);
				
				field = new GwtFormField("password");		
				field.addValue(passwordTextRegister.getText());
				form.addField(field);
				
				field = new GwtFormField("name");		
				field.addValue(nameTextRegister.getText());
				form.addField(field);
				
				
				field = new GwtFormField("email");		
				field.addValue(emailTextRegister.getText());
				form.addField(field);
				
				field = new GwtFormField("code");		
				field.addValue(codeTextRegister.getText());
				form.addField(field);

				if (success) {
					// No validation errors found. We can submit the data to the
					// server!
					
					OswServiceFactory.getService().register(form, new ERequestCallback<Object>() {

								@Override
								public void onFailure(Object error) {
									if (error==null) {
										AlertDialog.getInstance().showDialog(uiText.RegistrationFailureDetails(), uiText.RegistrationFailure());
									}
									else {
										AlertDialog.getInstance().showDialog(error.toString(), uiText.RegistrationFailure());
									}
								}

								@Override
								public void onSuccess(Object result) {

									//say success and change to login tab...
									AlertDialog.getInstance().showDialog(
											uiText.PleaseLogin(),
											uiText.RegistrationSuccess());
									tabpanel.selectTab(0);
								}								
							});
					
				} else {
					// One (or more) validations failed. The actions will have
					// been
					// already invoked by the validator.validate() call.
					center();
				}
			}
		});

		buttonLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				processLogin();
			}
		});
		
		requestLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RequestCodeDialog codeDialog = new RequestCodeDialog();
				codeDialog.show();
			}
		});
		
		

		validator.addValidators("Username", new StringLengthValidator(
				usernameTextRegister, 4, 25).addActionForFailure(
				new StyleAction("validationFailed")).addActionForFailure(
				new CustomLabelTextAction(usernameRegisterError, false)));

		validator.addValidators("Password", new StringLengthValidator(
				passwordTextRegister, 4, 25).addActionForFailure(
				new StyleAction("validationFailed")).addActionForFailure(
				new CustomLabelTextAction(passwordRegisterError, false)));
		
		validator.addValidators("Password2", new MultiStringsEqualsValidator(new TextBoxBase[] {
				passwordTextRegister, confirmPasswordTextRegister
        }).addActionForFailure(
				new StyleAction("validationFailed")).addActionForFailure(
				new CustomLabelTextAction(confirmPasswordRegisterError, false)));
		
		validator.addValidators("Name", new StringLengthValidator(
				nameTextRegister, 1, 25).addActionForFailure(
				new StyleAction("validationFailed")).addActionForFailure(
				new CustomLabelTextAction(nameRegisterError, false)));

		validator.addValidators("Email", new EmailValidator(emailTextRegister)
				.addActionForFailure(new StyleAction("validationFailed"))
				.addActionForFailure(
						new CustomLabelTextAction(emailRegisterError, false)));
		
		validator.addValidators("Name", new NotEmptyValidator(nameTextRegister)
		.addActionForFailure(new StyleAction("validationFailed"))
		.addActionForFailure(new CustomLabelTextAction(nameRegisterError, false)));
		
		if (OswClient.getInstance().getPreference("registration_code").equals("true")) {
			validator.addValidators("Code", new NotEmptyValidator(codeTextRegister)
			.addActionForFailure(new StyleAction("validationFailed"))
			.addActionForFailure(new CustomLabelTextAction(codeRegisterError, false)));
		}
		

	}

	private void processLogin() {
		loginError.setText("");
		loginError.setVisible(false);

		OswServiceFactory.getService().login(usernameText.getValue(),
				passwordText.getValue(), new RequestCallback<Object>() {

					@Override
					public void onFailure() {
						loginError
								.setText(uiText.LoginFailure());
						loginError.setVisible(true);
					}

					@Override
					public void onSuccess(Object result) {

						// store the username and password in the local storage
						// if remember me is selected
						if (Storage.isSupported()
								&& rememberme.getValue() == true) {
							Storage localStorage = Storage.getLocalStorage();
							localStorage.setItem("username", usernameText
									.getValue());
							localStorage.setItem("password", passwordText
									.getValue());
						}

						complete();
					}

				});
	}

	@Override
	protected void onHide() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub

	}

}
