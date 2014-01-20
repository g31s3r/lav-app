package com.menoia.lav.vaadin.field;

import com.menoia.lav.vaadin.entity.Prototype;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

import enterpriseapp.Utils;
import enterpriseapp.ui.crud.DefaultCrudFieldFactory;

public class LavanderiaFieldFactory extends DefaultCrudFieldFactory {

    private static final long serialVersionUID = 1L;

    public Field createCustomField(Object bean, Item item, String pid, Component uiContext, Class<?> propertyType) {

        // Check if it's enum
        if (propertyType.isEnum()) {
            ComboBox box = new ComboBox();
            Object[] constants = propertyType.getEnumConstants();

            for (Object constant : constants) {
                box.addItem(constant);
                box.setItemCaption(constant, Utils.getPropertyLabel(propertyType.getSimpleName(), constant.toString()));

            }
            return box;
        }
        // If prototype class, create field
        else if (propertyType.isAssignableFrom(Prototype.class)) {
            return new PrototypeField();
        }

        return null;
    }

}
