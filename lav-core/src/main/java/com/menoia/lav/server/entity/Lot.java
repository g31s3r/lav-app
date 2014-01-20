package com.menoia.lav.server.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * 
 * @author gmenoia
 * 
 */
@Entity
public class Lot extends EntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Basic
    private Date date;

    @Basic
    private Date entry;

    @Basic
    private Date departure;

    @Basic
    private Long quantity;

    @Basic
    private String description;

    @Basic
    private String details;

    @ManyToOne
    private Recipe recipe;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @Override
    public Integer getVersion() {

        return version;
    }

    public void setVersion(Integer version) {

        this.version = version;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public Date getEntry() {

        return entry;
    }

    public void setEntry(Date entry) {

        this.entry = entry;
    }

    public Date getDeparture() {

        return departure;
    }

    public void setDeparture(Date departure) {

        this.departure = departure;
    }

    public Long getQuantity() {

        return quantity;
    }

    public void setQuantity(Long quantity) {

        this.quantity = quantity;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getDetails() {

        return details;
    }

    public void setDetails(String details) {

        this.details = details;
    }

    public Recipe getRecipe() {

        return recipe;
    }

    public void setRecipe(Recipe recipe) {

        this.recipe = recipe;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Lot other = (Lot) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Lot [id=" + id + ", version=" + version + ", date=" + date + ", entry=" + entry + ", departure="
            + departure + ", quantity=" + quantity + ", description=" + description + ", details=" + details
            + ", recipe=" + recipe + "]";
    }

}
