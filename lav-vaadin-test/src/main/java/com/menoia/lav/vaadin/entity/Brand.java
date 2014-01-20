package com.menoia.lav.vaadin.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;

@Entity
@CrudTable(filteringPropertyName = "name")
public class Brand extends Dto {

    private static final long serialVersionUID = 5278538901279226615L;

    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String name;

    @Override
    public Object getId() {

        return id;
    }

    @Override
    public void setId(Object id) {

        this.id = (Long) id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        return name;
    }

}
