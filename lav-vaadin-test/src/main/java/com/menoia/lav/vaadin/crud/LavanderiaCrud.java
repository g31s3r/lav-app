package com.menoia.lav.vaadin.crud;

import com.menoia.lav.vaadin.container.LavanderiaContainerFactory;
import com.menoia.lav.vaadin.field.LavanderiaFieldFactory;
import com.menoia.lav.vaadin.formatter.LavanderiaFormatter;

import enterpriseapp.hibernate.dto.Dto;
import enterpriseapp.ui.crud.CrudComponent;

public class LavanderiaCrud<T extends Dto> extends CrudComponent<T> {

    private static final long serialVersionUID = 1L;

    public LavanderiaCrud(Class<T> clazz, boolean showForm) {

        super(clazz, LavanderiaContainerFactory.getInstance().getContainer(clazz), new LavanderiaFieldFactory(), null,
            null, true, true, true, true, true, false, false, false, 0);

        // if form is not shown, make the component read only, so
        // "import from clipboard" option, won't be available
        setReadOnly(!showForm);

        getSplit().setSplitPosition(70);

        // use a PropertyFormatter for our table
        getTable().setPropertyFormatter(new LavanderiaFormatter());
        getTable().updateTable();

        // we want to hide filters for this component
        // filterLayout.setVisible(false);
    }

}
