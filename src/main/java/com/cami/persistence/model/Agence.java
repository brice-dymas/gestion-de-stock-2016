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
public class Agence extends EntityObject
{

    @NotBlank(message = "{blank.message}")
    @Size(min = 3, max = 100, message = "{size.message}")
    private String code;

    @NotBlank(message = "{blank.message}")
    @Size(min = 3, max = 100, message = "{size.message}")
    private String intitule;

    @NotBlank(message = "{blank.message}")
    @Size(min = 3, max = 100, message = "{size.message}")
    private String region;

    public Agence()
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

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.code);
        hash = 23 * hash + Objects.hashCode(this.intitule);
        hash = 23 * hash + Objects.hashCode(this.region);
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
        final Agence other = (Agence) obj;
        if (!Objects.equals(this.code, other.code))
        {
            return false;
        }
        if (!Objects.equals(this.intitule, other.intitule))
        {
            return false;
        }
        if (!Objects.equals(this.region, other.region))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Agence{" + "code=" + code + ", intitule=" + intitule + ", region=" + region + '}';
    }

}
