package com.menoia.lav.server.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * 
 * @author gmenoia
 * 
 */
@Entity
public class Recipe extends EntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Basic
    private Date date;

    @Basic
    private BigDecimal price;

    @OneToMany
    private List<RecipeProcess> processes;

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

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public List<RecipeProcess> getProcesses() {

        return processes;
    }

    public void setProcesses(List<RecipeProcess> processes) {

        this.processes = processes;
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
        Recipe other = (Recipe) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Recipe [id=" + id + ", version=" + version + ", date=" + date + ", price=" + price + ", processes="
            + processes + "]";
    }

}
