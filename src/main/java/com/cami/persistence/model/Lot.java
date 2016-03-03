/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author samuel
 */
@Entity
public class Lot extends EntityObject
{

    private String numero;

    private boolean modifiable;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateEntree;

    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int quantite = 0;

    @Min(value = 1000, message = "{min.message}")
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digits.message}")
    private int prixUnitaire = 0;

    @Min(value = 1000, message = "{min.message}")
    private long prixVenteUnitaire;

    private int totalMontant;

    @NotBlank(message = "{blank.message}")
    private String etat;

    @ManyToOne
    private LigneOperation ligneOperation;

    @JsonIgnore
    @ManyToOne
    private Entree entree;

    @ManyToOne
    private Fourniture fourniture;

    public Lot()
    {
        this.quantite = 0;
        this.prixUnitaire = 0;
        this.prixVenteUnitaire = 0;
        this.modifiable = true;
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

    public int getQuantite()
    {
        return quantite;
    }

    public LigneOperation getLigneOperation()
    {
        return ligneOperation;
    }

    public void setLigneOperation(LigneOperation ligneOperation)
    {
        this.ligneOperation = ligneOperation;
    }

    public void setQuantite(int quantite)
    {
        this.quantite = quantite;
    }

    public Fourniture getFourniture()
    {
        return fourniture;
    }

    public void setFourniture(Fourniture fourniture)
    {
        this.fourniture = fourniture;
    }

    public int getPrixUnitaire()
    {
        return prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire)
    {
        this.prixUnitaire = prixUnitaire;
    }

    public int getTotalMontant()
    {
        return totalMontant;
    }

    public void setTotalMontant(int totalMontant)
    {
        this.totalMontant = totalMontant;
    }

    public String getEtat()
    {
        return etat;
    }

    public void setEtat(String etat)
    {
        this.etat = etat;
    }

    public Entree getEntree()
    {
        return entree;
    }

    public void setEntree(Entree entree)
    {
        this.entree = entree;
    }

    public long getPrixVenteUnitaire()
    {
        return prixVenteUnitaire;
    }

    public void setPrixVenteUnitaire(long prixVenteUnitaire)
    {
        this.prixVenteUnitaire = prixVenteUnitaire;
    }

    public boolean isModifiable()
    {
        return modifiable;
    }

    public void setModifiable(boolean modifiable)
    {
        this.modifiable = modifiable;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.numero);
        hash = 97 * hash + Objects.hashCode(this.dateEntree);
        hash = 97 * hash + this.quantite;
        hash = 97 * hash + this.prixUnitaire;
        hash = 97 * hash + this.totalMontant;
        hash = 97 * hash + Objects.hashCode(this.etat);
        hash = 97 * hash + Objects.hashCode(this.entree);
        hash = 97 * hash + Objects.hashCode(this.fourniture);
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
        final Lot other = (Lot) obj;
        if (!Objects.equals(this.numero, other.numero))
        {
            return false;
        }
        if (!Objects.equals(this.dateEntree, other.dateEntree))
        {
            return false;
        }
        if (this.quantite != other.quantite)
        {
            return false;
        }
        if (this.prixUnitaire != other.prixUnitaire)
        {
            return false;
        }
        if (this.prixVenteUnitaire != other.prixVenteUnitaire)
        {
            return false;
        }
        if (this.totalMontant != other.totalMontant)
        {
            return false;
        }
        if (!Objects.equals(this.etat, other.etat))
        {
            return false;
        }
        if (!Objects.equals(this.entree, other.entree))
        {
            return false;
        }
        if (!Objects.equals(this.fourniture, other.fourniture))
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
        String dateStr = format.format(date);
        String numeroStr = "LT-";
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

        //Ajout de la quantité du lot à la fourniture
        this.getFourniture().setQuantite(this.getFourniture().getQuantite() + this.quantite);

    }

    @Override
    public String toString()
    {
        return "Lot{" + "numero=" + numero + ", modifiable=" + modifiable + ", dateEntree=" + dateEntree + ", quantite=" + quantite + ", prixUnitaire=" + prixUnitaire + ", prixVenteUnitaire=" + prixVenteUnitaire + ", totalMontant=" + totalMontant + ", etat=" + etat + ", entree=" + entree + ", fourniture=" + fourniture + '}';
    }
}
