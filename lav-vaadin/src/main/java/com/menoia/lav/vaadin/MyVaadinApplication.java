package com.menoia.lav.vaadin;

import javax.inject.Inject;
import javax.inject.Named;

import com.menoia.lav.server.dao.CustomerDao;
import com.menoia.lav.server.entity.Customer;
import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class MyVaadinApplication extends Application {

    @Inject
    @Named("welcome")
    protected String text;

    @Inject
    private CustomerDao dao;

    @Override
    public void init() {

        Window window = new Window();
        window.addComponent(new Label(text));

        Customer c = new Customer();
        c.setName("bla");
        c.setBrand("kkk");
        c.setLocation("ble");
        dao.save(c);
        window.addComponent(new Label("New customer id is " + c.getId()));

        setMainWindow(window);
    }

}
