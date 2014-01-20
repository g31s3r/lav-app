package com.menoia.lav.server.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * 
 * @author gmenoia
 * 
 */
@Entity
public class RecipeProcess extends EntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @Basic
    private String process;

    @Basic
    private String details;

    @Basic
    private Integer minutes;

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

    public String getProcess() {

        return process;
    }

    public void setProcess(String process) {

        this.process = process;
    }

    public String getDetails() {

        return details;
    }

    public void setDetails(String details) {

        this.details = details;
    }

    public Integer getMinutes() {

        return minutes;
    }

    public void setMinutes(Integer minutes) {

        this.minutes = minutes;
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
        RecipeProcess other = (RecipeProcess) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "RecipeProcess [id=" + id + ", version=" + version + ", process=" + process + ", details=" + details
            + ", minutes=" + minutes + "]";
    }

}
