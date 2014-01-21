package com.menoia.lav.vaadin.field;

import com.google.common.base.Joiner;
import com.menoia.lav.vaadin.container.LavanderiaContainerFactory;
import com.menoia.lav.vaadin.container.PrototypeContainer;
import com.menoia.lav.vaadin.entity.Prototype;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import enterpriseapp.Utils;
import enterpriseapp.ui.crud.EntityField;

public class PrototypeField extends EntityField implements ValueChangeListener {

    private static final long serialVersionUID = 1L;

    private VerticalLayout vertical;
    private TextField textCustomer;
    private TextField textProcesses;

    public PrototypeField() {

        super(Prototype.class, null);

        textCustomer = new TextField();
        textCustomer.setValue(Utils.getPropertyLabel("prototype", "customer"));
        textCustomer.setEnabled(false);
        textCustomer.setVisible(false);
        textCustomer.setWidth("100%");

        textProcesses = new TextField();
        textProcesses.setValue(Utils.getPropertyLabel("property", "processes"));
        textProcesses.setEnabled(false);
        textProcesses.setVisible(false);
        textProcesses.setWidth("100%");

        layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.addComponent(select);
        layout.setExpandRatio(select, 1);
        layout.addComponent(refreshButton);

        vertical = new VerticalLayout();
        vertical.setSizeFull();
        vertical.addComponent(layout);
        vertical.setExpandRatio(layout, 1);
        vertical.addComponent(textCustomer);
        // vertical.addComponent(textProcesses);

        addListener(this);

        update();
        setCompositionRoot(vertical);
    }

    @Override
    public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {

        Prototype prototype = (Prototype) event.getProperty().getValue();

        PrototypeContainer container =
            (PrototypeContainer) LavanderiaContainerFactory.getInstance().getContainer(Prototype.class);
        prototype = container.refresh(prototype);

        if (prototype != null) {
            vertical.setSpacing(true);
            textCustomer.setValue(prototype.getCustomer().toString());
            textCustomer.setVisible(true);
            textProcesses.setValue(Joiner.on(", ").join(prototype.getProcesses()));
            textProcesses.setVisible(true);
        } else {
            vertical.setSpacing(false);
            textCustomer.setValue(null);
            textCustomer.setVisible(false);
            textProcesses.setValue(null);
            textProcesses.setVisible(false);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {

        super.setReadOnly(readOnly);
        textCustomer.setEnabled(readOnly);
        textCustomer.setReadOnly(readOnly);
        textProcesses.setEnabled(readOnly);
        textProcesses.setReadOnly(readOnly);
    }

}
