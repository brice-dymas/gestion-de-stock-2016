/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Entity
public class Journal extends EntityObject
{

    private int quantiteRetirer;

    @ManyToOne
    private LigneOperation ligneOperation;

    @ManyToOne
    private Lot lot;

    public Journal()
    {
    }

    public int getQuantiteRetirer()
    {
        return quantiteRetirer;
    }

    public void setQuantiteRetirer(int quantiteRetirer)
    {
        this.quantiteRetirer = quantiteRetirer;
    }

    public LigneOperation getLigneOperation()
    {
        return ligneOperation;
    }

    public void setLigneOperation(LigneOperation ligneOperation)
    {
        this.ligneOperation = ligneOperation;
    }

    public Lot getLot()
    {
        return lot;
    }

    public void setLot(Lot lot)
    {
        this.lot = lot;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 43 * hash + this.quantiteRetirer;
        hash = 43 * hash + Objects.hashCode(this.ligneOperation);
        hash = 43 * hash + Objects.hashCode(this.lot);
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
        final Journal other = (Journal) obj;
        if (this.quantiteRetirer != other.quantiteRetirer)
        {
            return false;
        }
        if (!Objects.equals(this.ligneOperation, other.ligneOperation))
        {
            return false;
        }
        return Objects.equals(this.lot, other.lot);
    }

    @Override
    public String toString()
    {
        return "Journal{" + "quantiteRetirer=" + quantiteRetirer + ", ligneOperation=" + ligneOperation + ", lot=" + lot + '}';
    }

}
