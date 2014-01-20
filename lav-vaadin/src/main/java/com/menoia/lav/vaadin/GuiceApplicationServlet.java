package com.menoia.lav.vaadin;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Provider;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@Singleton
public class GuiceApplicationServlet extends AbstractApplicationServlet {

    private static final long serialVersionUID = -9155208482000355632L;

    protected final Provider<Application> applicationProvider;

    @Inject
    public GuiceApplicationServlet(Provider<Application> applicationProvider) {

        this.applicationProvider = applicationProvider;
    }

    @Override
    protected Class<Application> getApplicationClass() throws ClassNotFoundException {

        return Application.class;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {

        return applicationProvider.get();
    }

}
