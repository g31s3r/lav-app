package com.menoia.lav.vaadin.container;

import com.menoia.lav.vaadin.entity.Audit;
import com.menoia.lav.vaadin.entity.Brand;
import com.menoia.lav.vaadin.entity.Customer;
import com.menoia.lav.vaadin.entity.Prototype;
import com.menoia.lav.vaadin.entity.Sale;
import com.menoia.lav.vaadin.entity.User;

import enterpriseapp.hibernate.ContainerFactory;
import enterpriseapp.hibernate.DefaultHbnContainer;
import enterpriseapp.hibernate.dto.AuditLog;

public class LavanderiaContainerFactory extends ContainerFactory {

    @SuppressWarnings("rawtypes")
    @Override
    public DefaultHbnContainer getContainer(Class<?> clazz) {

        if (Customer.class.isAssignableFrom(clazz)) {
            return new CustomerContainer();
        } else if (Brand.class.isAssignableFrom(clazz)) {
            return new BrandContainer();
        } else if (User.class.isAssignableFrom(clazz)) {
            return new UserContainer();
        } else if (AuditLog.class.isAssignableFrom(clazz)) {
            return getDefaultFactory().getContainer(Audit.class);
        } else if(Sale.class.isAssignableFrom(clazz)){
            return new SaleContainer();
        } else if(Prototype.class.isAssignableFrom(clazz)) {
            return new PrototypeContainer();
        }

        return getDefaultFactory().getContainer(clazz);
    }
}
