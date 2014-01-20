package com.menoia.lav.vaadin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;

@Entity
@CrudTable(filteringPropertyName = "seal")
public class Prototype extends Dto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private Long seal;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne
    private Brand brand;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Process> processes;

    @Column
    private String reference;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column
    private BigDecimal suggestedPrice;

    @Column(name = "entranceDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "leaveDate")
    @Temporal(TemporalType.DATE)
    private Date leave;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public Object getId() {

        return id;
    }

    @Override
    public void setId(Object id) {

        this.id = (Long) id;
    }

    public Long getSeal() {

        return seal;
    }

    public void setSeal(Long seal) {

        this.seal = seal;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public Brand getBrand() {

        return brand;
    }

    public void setBrand(Brand brand) {

        this.brand = brand;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getReference() {

        return reference;
    }

    public void setReference(String reference) {

        this.reference = reference;
    }

    public BigDecimal getUnitPrice() {

        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {

        this.unitPrice = unitPrice;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public Date getLeave() {

        return leave;
    }

    public void setLeave(Date leave) {

        this.leave = leave;
    }

    public Set<Process> getProcesses() {

        return processes;
    }

    public void setProcesses(Set<Process> processes) {

        this.processes = processes;
    }

    public BigDecimal getSuggestedPrice() {

        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice) {

        this.suggestedPrice = suggestedPrice;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    @Override
    public String toString() {

        return seal.toString();
    }

}
