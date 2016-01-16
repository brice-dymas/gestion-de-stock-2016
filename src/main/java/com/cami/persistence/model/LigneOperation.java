/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author samuel
 */
@Entity
public class LigneOperation extends EntityObject
{

    @NumberFormat
    @Column(nullable = true)
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int quantitePhysique;

    @NumberFormat
    @Column(nullable = true)
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int quantite;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dateRegulation;

    @NumberFormat
    @Column(nullable = true)
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int quantiteEcart; // Pour Audit

    @NotBlank(message = "{blank.message}")
    private String observation;

    @NumberFormat
    @Column(nullable = true)
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int ecartValeur; // Pour Audit

    @ManyToOne(targetEntity = Fourniture.class, optional = false, fetch = FetchType.EAGER)
    private Fourniture fourniture;

    @ManyToOne(targetEntity = Operation.class, optional = false)
    private Operation operation;

    public LigneOperation()
    {
    }

    public int getQuantitePhysique()
    {
        return quantitePhysique;
    }

    public void setQuantitePhysique(int quantitePhysique)
    {
        this.quantitePhysique = quantitePhysique;
    }

    public Date getDateRegulation()
    {
        return dateRegulation;
    }

    public void setDateRegulation(Date dateRegulation)
    {
        this.dateRegulation = dateRegulation;
    }

    public int getQuantiteEcart()
    {
        return quantiteEcart;
    }

    public void setQuantiteEcart(int quantiteEcart)
    {
        this.quantiteEcart = quantiteEcart;
    }

    public String getObservation()
    {
        return observation;
    }

    public void setObservation(String observation)
    {
        this.observation = observation;
    }

    public int getEcartValeur()
    {
        return ecartValeur;
    }

    public void setEcartValeur(int ecartValeur)
    {
        this.ecartValeur = ecartValeur;
    }

    public int getQuantite()
    {
        return quantite;
    }

    public void setQuantite(int quantite)
    {
        this.quantite = quantite;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public void setOperation(Operation operation)
    {
        this.operation = operation;
    }

    public Fourniture getFourniture()
    {
        return fourniture;
    }

    public void setFourniture(Fourniture fourniture)
    {
        this.fourniture = fourniture;
    }

    @Override
    public String toString()
    {
        return "LigneOperation{" + "quantitePhysique=" + quantitePhysique + ", quantite=" + quantite + ", dateRegulation=" + dateRegulation + ", quantiteEcart=" + quantiteEcart + ", observation=" + observation + ", ecartValeur=" + ecartValeur + ", fourniture=" + fourniture + ", operation=" + operation + '}';
    }

    @PrePersist
    public void calculQuantiteEcart()
    {
        System.out.println("dans la préPersist de ligne operation");
        if (this.operation.getTypeOperation().getIntitule().equalsIgnoreCase("audit"))
        {
            System.out.println("dans prePersist ligne operation");
            int valeurEcart;
            System.out.println("Quantite fourniture = " + this.fourniture.getQuantite());
            System.out.println("Quantite Physique fourniture = " + this.quantitePhysique);
            valeurEcart = this.fourniture.getQuantite() - this.quantitePhysique;
            System.out.println("ecart = " + valeurEcart);
            this.setQuantiteEcart(valeurEcart);
        }
    }

}
