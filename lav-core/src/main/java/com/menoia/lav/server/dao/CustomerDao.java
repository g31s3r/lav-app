package com.menoia.lav.server.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.menoia.lav.server.entity.Customer;

public class CustomerDao extends DaoBase<Customer> {

    @Inject
    public CustomerDao(EntityManager em) {

        super(em, Customer.class);
    }

}
