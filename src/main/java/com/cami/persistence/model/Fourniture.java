/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Entity
public class Fourniture extends EntityObject
{

    @NotBlank(message = "{blank.message}")
    private String reference;

    @NotBlank(message = "{blank.message}")
    private String designation;

    @NotNull(message = "{blank.message}")
    @NumberFormat
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int quantite;

    @NotNull(message = "{blank.message}")
    @NumberFormat
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int seuil;

    @NotNull(message = "{blank.message}")
    @NumberFormat
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int manque;     //Pour entree

    @NotNull(message = "{blank.message}")
    @NumberFormat
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int perte;      //Pour perte

    @ManyToOne(targetEntity = Categorie.class, optional = false, fetch = FetchType.EAGER)
    private Categorie categorie;

    public Fourniture()
    {
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public int getQuantite()
    {
        return quantite;
    }

    public void setQuantite(int quantite)
    {
        this.quantite = quantite;
    }

    public int getSeuil()
    {
        return seuil;
    }

    public void setSeuil(int seuil)
    {
        this.seuil = seuil;
    }

    public Categorie getCategorie()
    {
        return categorie;
    }

    public void setCategorie(Categorie categorie)
    {
        this.categorie = categorie;
    }

    public int getManque()
    {
        return manque;
    }

    public void setManque(int manque)
    {
        this.manque = manque;
    }

    public int getPerte()
    {
        return perte;
    }

    public void setPerte(int perte)
    {
        this.perte = perte;
    }

    @Override
    public String toString()
    {
        return "Fourniture{" + "reference=" + reference + ", designation=" + designation + ", quantite=" + quantite + ", seuil=" + seuil + ", categorie=" + categorie + '}';
    }

}
