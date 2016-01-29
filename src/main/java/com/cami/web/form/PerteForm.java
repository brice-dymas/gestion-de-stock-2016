/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.form;

import com.cami.persistence.model.Perte;
import com.cami.web.util.ShrinkableLazyList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.collections.FactoryUtils;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
public class PerteForm
{

    @Valid
    @NotEmpty(message = "{empty.message}")
    private List<Perte> listPertes = ShrinkableLazyList.decorate(
            new ArrayList<>(), FactoryUtils.instantiateFactory(Perte.class));

    public PerteForm()
    {
    }

    public List<Perte> getPertes()
    {
        if (listPertes.size() > 0)
        {
            for (Perte perte : listPertes)
            {
                perte.setDatePerte(new Date());
            }
        }
        return listPertes;
    }

    public List<Perte> getListPertes()
    {
        return listPertes;
    }

    public void setListPertes(List<Perte> listPertes)
    {
        this.listPertes = listPertes;
    }

}
