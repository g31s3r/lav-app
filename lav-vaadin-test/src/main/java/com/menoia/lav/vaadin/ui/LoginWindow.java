package com.menoia.lav.vaadin.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menoia.lav.vaadin.container.LavanderiaContainerFactory;
import com.menoia.lav.vaadin.container.UserContainer;
import com.menoia.lav.vaadin.entity.User;
import com.vaadin.Application;

import enterpriseapp.ui.window.AuthWindow;

public class LoginWindow extends AuthWindow {

    private static final long serialVersionUID = 1L;

    private static Logger LOGGER = LoggerFactory.getLogger(LoginWindow.class);

    public LoginWindow() {

        super(Constants.uiLogin, Constants.uiLogin, Constants.uiUserLogin, Constants.uiUserPassword, "admin", "admin");
        setClosable(false);
        getLoginTf().focus();
    }

    @Override
    public void buttonClicked() {

        // this method is called when the login button is clicked (you don't
        // say?)
        UserContainer userContainer = (UserContainer) LavanderiaContainerFactory.getInstance().getContainer(User.class);
        User user =
            userContainer.getByLoginAndPassword(getLoginTf().getValue().toString(), getPasswordTf().getValue()
                .toString());

        if (user != null) {
            login(getApplication(), user);
        } else {
            showError();
        }
    }

    private void login(Application application, User user) {

        LOGGER.info("User logged: " + getLoginTf().getValue());
        application.setUser(user);
        application.init();
    }

    private void showError() {

        LOGGER.info("Invalid credentials for login " + getLoginTf().getValue());
        label.setCaption(Constants.uiInvalidCredentials);
        panel.setVisible(true);
    }

}
