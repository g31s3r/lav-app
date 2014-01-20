package com.menoia.lav.vaadin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.menoia.lav.server.injector.PersistenceModule;
import com.vaadin.Application;

public class MyServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        ServletModule module = new ServletModule() {

            @Override
            protected void configureServlets() {

                serve("/*").with(GuiceApplicationServlet.class);

                bind(Application.class).to(MyVaadinApplication.class).in(ServletScopes.SESSION);
                bindConstant().annotatedWith(Names.named("welcome")).to("This is my first Vaadin/Guice Application");
            }
        };
        
        Injector injector = Guice.createInjector(module, new PersistenceModule());
        return injector;
    }
}
