package com.menoia.lav.vaadin.field;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

import enterpriseapp.Utils;

public class EnumField extends CustomField {

    private static final long serialVersionUID = 1L;

    private Class<?> type;
    private VerticalLayout layout;
    private ComboBox box;

    public EnumField(Class<?> type) {

        this.type = type;

        box = new ComboBox();
        box.setSizeFull();
        box.setImmediate(true);

        Object[] constants = type.getEnumConstants();

        for (Object constant : constants) {
            box.addItem(constant);
            box.setItemCaption(constant, Utils.getPropertyLabel(type.getSimpleName(), constant.toString()));
        }

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.addComponent(box);
        layout.setExpandRatio(box, 1);

        setCompositionRoot(layout);
    }

    @Override
    public Class<?> getType() {

        return type;
    }
    
    @Override
    public void setValue(Object newValue) {
        box.setValue(newValue);
    }
    
    @Override
    public Object getValue() {
        return box.getValue();
    }
    

}
