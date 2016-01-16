/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Entity
public class Categorie extends EntityObject
{

    @NotBlank(message = "{blank.message}")
    @Size(min = 3, max = 100, message = "{size.message}")
    private String intitule;

    public Categorie()
    {
    }

    public String getIntitule()
    {
        return intitule;
    }

    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.intitule);
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
        final Categorie other = (Categorie) obj;
        if (!Objects.equals(this.intitule, other.intitule))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Categorie{" + "intitule=" + intitule + '}';
    }

}
