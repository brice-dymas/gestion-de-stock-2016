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
 * @author samuel   < smlfolong@gmail.com >
 */
public class PerteForm {

    @Valid
    private Operation perte;

    @Valid
    @NotEmpty(message = "{empty.message}")
    private List<LigneOperation> ligneOperations = ShrinkableLazyList.decorate(
            new ArrayList<>(), FactoryUtils.instantiateFactory(LigneOperation.class));

    public PerteForm() {
    }

    public Operation getPerte() {
        if (perte != null) {
            perte.setDateOperation(new Date());
            perte.setLigneOperations(ligneOperations);
        }
        return perte;
    }

    public void setPerte(Operation perte) {
        this.perte = perte;
    }

    public List<LigneOperation> getLigneOperations() {
        return ligneOperations;
    }

    public void setLigneOperations(List<LigneOperation> ligneOperations) {
        this.ligneOperations = ligneOperations;
    }

}
