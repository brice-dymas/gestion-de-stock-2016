/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@MappedSuperclass
public abstract class EntityObject implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Version
    protected Long version;

    public EntityObject()
    {
    }

    public EntityObject(Long id, long version)
    {
        this.id = id;
        this.version = version;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        String class1 = this.getClass().getName();
        String class2 = obj.getClass().getName();
        if (!class2.equals(class1))
        {
            return false;
        }
        EntityObject other = (EntityObject) obj;
        return this.id == other.id;
    }
}
