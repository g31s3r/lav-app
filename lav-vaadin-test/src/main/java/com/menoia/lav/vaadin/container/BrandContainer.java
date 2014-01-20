package com.menoia.lav.vaadin.container;

import com.menoia.lav.vaadin.entity.Brand;

import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class BrandContainer extends DefaultHbnContainer<Brand> {

    private static final long serialVersionUID = 1L;

    public BrandContainer() {

        super(Brand.class);
    }

    public Brand getByName(String name) {

        return singleQuery("from Brand where name = ?", new Object[] { name });
    }

}
