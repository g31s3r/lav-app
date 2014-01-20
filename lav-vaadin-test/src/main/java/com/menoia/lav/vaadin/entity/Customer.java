package com.menoia.lav.vaadin.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;

@Entity
@CrudTable(filteringPropertyName = "name")
public class Customer extends Dto {

    private static final long serialVersionUID = -3528873500689783793L;

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Basic
    private String name;

    @Basic
    private String city;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @CrudField(embedded = true, forceRequired = true)
    private Set<Brand> brands;

    @Override
    public Object getId() {

        return id;
    }

    @Override
    public void setId(Object id) {

        this.id = (Long) id;
    }

    public Integer getVersion() {

        return version;
    }

    public void setVersion(Integer version) {

        this.version = version;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public Set<Brand> getBrands() {

        return brands;
    }

    public void setBrands(Set<Brand> brands) {

        this.brands = brands;
    }

    @Override
    public String toString() {

        return name;
    }

}
