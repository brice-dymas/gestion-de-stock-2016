/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.util.Objects;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Entity
public class TypeOperation extends EntityObject
{

    @NotBlank(message = "{blank.message}")
    private String intitule;

    public TypeOperation()
    {
    }

    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }

    public String getIntitule()
    {
        return intitule;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.intitule);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final TypeOperation other = (TypeOperation) obj;
        if (!Objects.equals(this.intitule, other.intitule))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "TypeOperation{" + "intitule=" + intitule + '}';
    }

}
