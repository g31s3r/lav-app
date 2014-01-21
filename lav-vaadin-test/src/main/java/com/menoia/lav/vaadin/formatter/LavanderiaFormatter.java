package com.menoia.lav.vaadin.formatter;

import com.vaadin.data.Property;

import enterpriseapp.Utils;
import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.ui.crud.CrudTable;
import enterpriseapp.ui.crud.DefaultPropertyFormatter;

public class LavanderiaFormatter extends DefaultPropertyFormatter {

    @Override
    public String formatPropertyValue(Object rowId, Object colId, Property property, Object bean,
                                      Object propertyObject, Class<?> returnType, CrudField crudFieldAnnotation,
                                      CrudTable<?> crudTable) {

        String value = null;

        // Check if is enum type
        if (returnType.isEnum()) {
            value = formatEnumeratorConstant(returnType, property.getValue());
        }
        // This is needed if we want to use Enterprise App default formatting
        else {
            value =
                super.formatPropertyValue(rowId, colId, property, bean, propertyObject, returnType,
                    crudFieldAnnotation, crudTable);
        }

        value = (value == null && property.getValue() != null) ? property.getValue().toString() : value;

        return value != null ? value.toUpperCase() : value;
    }

    private String formatEnumeratorConstant(Class<?> type, Object value) {

        return Utils.getPropertyLabel(type.getSimpleName(), value.toString());
    }

}
