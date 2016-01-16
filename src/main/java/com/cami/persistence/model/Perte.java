/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Entity
public class Perte extends EntityObject
{

    private String numero;

    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int quantite;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date datePerte;

    @ManyToOne
    private Lot lot;

    @ManyToOne(targetEntity = LigneOperation.class)
    private LigneOperation ligneOperation;

    public Perte()
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

    public int getQuantite()
    {
        return quantite;
    }

    public void setQuantite(int quantite)
    {
        this.quantite = quantite;
    }

    public Date getDatePerte()
    {
        return datePerte;
    }

    public void setDatePerte(Date datePerte)
    {
        this.datePerte = datePerte;
    }

    public Lot getLot()
    {
        return lot;
    }

    public void setLot(Lot lot)
    {
        this.lot = lot;
    }

    public LigneOperation getLigneOperation()
    {
        return ligneOperation;
    }

    public void setLigneOperation(LigneOperation ligneOperation)
    {
        this.ligneOperation = ligneOperation;
    }

    @PostPersist
    public void updateNumero()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = dateFormat.format(date);
        String numeroStr = "";
        numeroStr += this.getClass().getName().substring(0, 3).toUpperCase() + "-";
        numeroStr += dateStr + "-";
        String idStr;
        if (this.id < 10)
        {
            idStr = "0" + this.id;
        }
        else
        {
            idStr = this.id + "";
        }

        idStr = idStr.substring(0, 2);
        numeroStr += idStr;
        this.setNumero(numeroStr);
    }
}
