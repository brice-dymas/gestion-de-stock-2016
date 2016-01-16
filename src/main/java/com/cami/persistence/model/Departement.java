/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Entity
public class Departement extends EntityObject
{

    @NotBlank(message = "{blank.message}")
    private String code;

    @NotBlank(message = "{blank.message}")
    private String intitule;

    @ManyToOne(targetEntity = Agence.class, optional = false, fetch = FetchType.EAGER)
    private Agence agence;

    public Departement()
    {
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getIntitule()
    {
        return intitule;
    }

    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }

    public Agence getAgence()
    {
        return agence;
    }

    public void setAgence(Agence agence)
    {
        this.agence = agence;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.code);
        hash = 37 * hash + Objects.hashCode(this.intitule);
        hash = 37 * hash + Objects.hashCode(this.agence);
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
        final Departement other = (Departement) obj;
        if (!Objects.equals(this.code, other.code))
        {
            return false;
        }
        if (!Objects.equals(this.intitule, other.intitule))
        {
            return false;
        }
        if (!Objects.equals(this.agence, other.agence))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Departement{" + "code=" + code + ", intitule=" + intitule + ", agence=" + agence + '}';
    }

}
