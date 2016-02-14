/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Entity
public class Entree extends EntityObject
{

    @Column(nullable = true)
    protected String numero;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    protected Date dateEntree;

    protected Long ligneAuditId;

    @ManyToOne(targetEntity = Categorie.class, fetch = FetchType.EAGER, optional = false)
    private Categorie categorie;

    @ManyToOne(targetEntity = Role.class, optional = false, fetch = FetchType.EAGER)
    protected Role user;

    @OneToMany(targetEntity = Lot.class, fetch = FetchType.LAZY, mappedBy = "entree")
    List<Lot> lots = new ArrayList<>();

    public Entree()
    {
    }

    public String getNumero()
    {
        return numero;
    }

    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    public Date getDateEntree()
    {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree)
    {
        this.dateEntree = dateEntree;
    }

    public Categorie getCategorie()
    {
        return categorie;
    }

    public void setCategorie(Categorie categorie)
    {
        this.categorie = categorie;
    }

    public Long getLigneAuditId()
    {
        return ligneAuditId;
    }

    public void setLigneAuditId(Long ligneAuditId)
    {
        this.ligneAuditId = ligneAuditId;
    }

    public Role getUser()
    {
        return user;
    }

    public void setUser(Role user)
    {
        this.user = user;
    }

    public void setLots(List<Lot> lots)
    {
        this.lots = lots;
    }

    public List<Lot> getLots()
    {
        return lots;
    }

    @Override

    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.numero);
        hash = 29 * hash + Objects.hashCode(this.dateEntree);
        hash = 29 * hash + Objects.hashCode(this.categorie);
        hash = 29 * hash + Objects.hashCode(this.user);
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
        final Entree other = (Entree) obj;
        if (!Objects.equals(this.numero, other.numero))
        {
            return false;
        }
        if (!Objects.equals(this.dateEntree, other.dateEntree))
        {
            return false;
        }
        if (!Objects.equals(this.categorie, other.categorie))
        {
            return false;
        }
        if (!Objects.equals(this.user, other.user))
        {
            return false;
        }
        return true;
    }

    @PostPersist
    public void updateNumero()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String str = format.format(date);
        String numero = "ET-";
        numero += str + "-";
        String id;
        if (this.id < 10)
        {
            id = "0" + this.id;
        }
        else
        {
            id = this.id + "";

        }

        id = id.substring(0, 2);
        numero += id;
        this.setNumero(numero);

    }

    @Override
    public String toString()
    {
        return "Entree{" + "numero=" + numero + ", dateEntree=" + dateEntree + ", categorie=" + categorie + ", user=" + user + '}';
    }

}
