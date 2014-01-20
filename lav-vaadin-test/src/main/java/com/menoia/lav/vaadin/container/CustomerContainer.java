package com.menoia.lav.vaadin.container;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.menoia.lav.vaadin.entity.Customer;

import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class CustomerContainer extends DefaultHbnContainer<Customer> {

    private static final long serialVersionUID = 1L;

    public CustomerContainer() {

        super(Customer.class);
    }

    public Customer getByName(String name) {

        return singleQuery("from Customer where name = ?", new Object[] { name });
    }
    
    @Override
    public List<Customer> listAll() {
    
        Criteria crit = getCriteria();
        crit.addOrder(Order.asc("name"));
        return crit.list();
    }

}
