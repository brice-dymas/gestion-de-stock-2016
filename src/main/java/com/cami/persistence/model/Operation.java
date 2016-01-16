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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Entity
public class Operation extends EntityObject
{

    protected String numero;

    @NotBlank(message = "{blank.message}")
    protected String observation;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    protected Date dateOperation;

    @ManyToOne(targetEntity = Departement.class, fetch = FetchType.EAGER, optional = false)
    private Departement departement;

    @ManyToOne(targetEntity = Role.class, optional = false, fetch = FetchType.EAGER)
    protected Role user;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = LigneOperation.class, mappedBy = "operation")
    private List<LigneOperation> ligneOperations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = TypeOperation.class)
    private TypeOperation typeOperation;

    public Operation()
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

    public String getObservation()
    {
        return observation;
    }

    public void setObservation(String observation)
    {
        this.observation = observation;
    }

    public Date getDateOperation()
    {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation)
    {
        this.dateOperation = dateOperation;
    }

    public Departement getDepartement()
    {
        return departement;
    }

    public void setDepartement(Departement departement)
    {
        this.departement = departement;
    }

    public Role getUser()
    {
        return user;
    }

    public void setUser(Role user)
    {
        this.user = user;
    }

    public List<LigneOperation> getLigneOperations()
    {
        return ligneOperations;
    }

    public void setLigneOperations(List<LigneOperation> ligneOperations)
    {
        this.ligneOperations = ligneOperations;
    }

    public TypeOperation getTypeOperation()
    {
        return typeOperation;
    }

    public void setTypeOperation(TypeOperation typeOperation)
    {
        this.typeOperation = typeOperation;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.numero);
        hash = 97 * hash + Objects.hashCode(this.observation);
        hash = 97 * hash + Objects.hashCode(this.dateOperation);
        hash = 97 * hash + Objects.hashCode(this.departement);
        hash = 97 * hash + Objects.hashCode(this.user);
        hash = 97 * hash + Objects.hashCode(this.ligneOperations);
        hash = 97 * hash + Objects.hashCode(this.typeOperation);
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
        final Operation other = (Operation) obj;
        if (!Objects.equals(this.numero, other.numero))
        {
            return false;
        }
        if (!Objects.equals(this.observation, other.observation))
        {
            return false;
        }
        if (!Objects.equals(this.dateOperation, other.dateOperation))
        {
            return false;
        }
        if (!Objects.equals(this.departement, other.departement))
        {
            return false;
        }
        if (!Objects.equals(this.user, other.user))
        {
            return false;
        }
        if (!Objects.equals(this.ligneOperations, other.ligneOperations))
        {
            return false;
        }
        if (!Objects.equals(this.typeOperation, other.typeOperation))
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
        String numeroStr = "OP-";
        numeroStr += this.typeOperation.getIntitule().substring(0, 3).toUpperCase() + "-";
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

    @Override
    public String toString()
    {
        return "Operation{" + "numero=" + numero + ", observation=" + observation + ", dateOperation=" + dateOperation + ", departement=" + departement + ", user=" + user + ", typeOperation=" + typeOperation + '}';
    }

}
