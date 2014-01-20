package com.menoia.lav.vaadin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enterpriseapp.hibernate.dto.Dto;

@Entity
public class Sale extends Dto {

    private static final long serialVersionUID = 6716067119535676603L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Prototype prototype;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Long rol;

    @Column
    private String observation;

    @Column
    private Date delivered;

    @Column
    @Temporal(TemporalType.DATE)
    private Date billing;

    public Sale() {

        this.date = new Date();
    }

    @Override
    public Object getId() {

        return id;
    }

    @Override
    public void setId(Object id) {

        this.id = (Long) id;
    }

    public Date getBilling() {

        return billing;
    }

    public void setBilling(Date billing) {

        this.billing = billing;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public Long getRol() {

        return rol;
    }

    public void setRol(Long rol) {

        this.rol = rol;
    }

    public Long getQuantity() {

        return quantity;
    }

    public void setQuantity(Long quantity) {

        this.quantity = quantity;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    public Date getDelivered() {

        return delivered;
    }

    public void setDelivered(Date delivered) {

        this.delivered = delivered;
    }

    public String getObservation() {

        return observation;
    }

    public void setObservation(String observation) {

        this.observation = observation;
    }

    public Prototype getPrototype() {

        return prototype;
    }

    public void setPrototype(Prototype prototype) {

        this.prototype = prototype;
    }

}
