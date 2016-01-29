/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Perte;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
public interface IPerteService extends IOperations<Perte>
{

    public List<Perte> create(List<Perte> pertes);

    public Perte findPerteByLigneAudit(Long ligneAuditId);

    Page<Perte> findPagenated(String numero, int quantite, Date datePerte, int page, Integer size);
}
