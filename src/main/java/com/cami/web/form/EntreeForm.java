/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.form;

import com.cami.persistence.model.Entree;
import com.cami.persistence.model.Lot;
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
public class EntreeForm
{

    @Valid
    private Entree entree;

    @Valid
    @NotEmpty(message = "{empty.message}")
    private List<Lot> listeLots = ShrinkableLazyList.decorate(
            new ArrayList(), FactoryUtils.instantiateFactory(Lot.class));

    public EntreeForm()
    {
    }

    public Entree getEntree()
    {
        if (entree != null)
        {
            entree.setDateEntree(new Date());
            entree.setLots(listeLots);
        }

        return entree;
    }

    public void setEntree(Entree entree)
    {
        this.entree = entree;
    }

    public List<Lot> getListeLots()
    {
        return listeLots;
    }

    public void setListeLots(List<Lot> lots)
    {
        this.listeLots = lots;
    }

}
