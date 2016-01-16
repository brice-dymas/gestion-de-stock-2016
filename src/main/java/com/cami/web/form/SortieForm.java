/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.form;

import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Operation;
import com.cami.web.util.ShrinkableLazyList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.collections.FactoryUtils;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public class SortieForm
{

    @Valid
    private Operation sortie;

    @Valid
    @NotEmpty(message = "{empty.message}")
    private List<LigneOperation> ligneOperations = ShrinkableLazyList.decorate(
            new ArrayList(), FactoryUtils.instantiateFactory(LigneOperation.class));

    public SortieForm()
    {
    }

    public Operation getSortie()
    {
        if (sortie != null)
        {
            sortie.setDateOperation(new Date());
            sortie.setLigneOperations(ligneOperations);
        }
        return sortie;
    }

    public void setSortie(Operation sortie)
    {
        this.sortie = sortie;
    }

    public List<LigneOperation> getLigneOperations()
    {
        return ligneOperations;
    }

    public void setLigneOperations(List<LigneOperation> ligneOperations)
    {
        this.ligneOperations = ligneOperations;
    }

}
