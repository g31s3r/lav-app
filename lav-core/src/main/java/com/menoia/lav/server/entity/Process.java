package com.menoia.lav.server.entity;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import com.menoia.lav.server.entity.enumerator.TypeProcess;

/**
 * 
 * @author gmenoia
 *
 */
@Entity
public class Process extends EntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Basic
    private String name;

    @Basic
    private BigDecimal cost;

    @Basic
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TypeProcess type;

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

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BigDecimal getCost() {

        return cost;
    }

    public void setCost(BigDecimal cost) {

        this.cost = cost;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
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
        Process other = (Process) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Process [id=" + id + ", version=" + version + ", name=" + name + ", cost=" + cost + ", price=" + price
            + "]";
    }

}
