/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IFournitureDao;
import com.cami.persistence.dao.ILigneOperationDao;
import com.cami.persistence.dao.ILotDao;
import com.cami.persistence.dao.IPerteDao;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Perte;
import com.cami.persistence.service.IPerteService;
import com.cami.persistence.service.common.AbstractService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Service("perteService")
public class PerteService extends AbstractService<Perte> implements IPerteService
{

    @Autowired
    IPerteDao perteDao;

    @Autowired
    ILotDao lotDao;

    @Autowired
    IFournitureDao fournitureDao;

    @Autowired
    ILigneOperationDao ligneOperationDao;

    @Override
    protected PagingAndSortingRepository<Perte, Long> getDao()
    {
        return perteDao;
    }

    @Override
    public Perte create(Perte entity)
    {
        System.out.println("Dans Perte Service fourniture Id = " + entity.getLigneOperation().getFourniture().getId());
        final Fourniture fourniture = fournitureDao.findOne(entity.getLigneOperation().getFourniture().getId());
        System.out.println("Dans Perte Service Quantite perte = " + entity.getQuantite());
        fourniture.setPerte(entity.getQuantite());
        System.out.println("Dans Perte Service Quantite fourniture venant de la bd = " + fourniture.getQuantite());
        fourniture.setQuantite(fourniture.getQuantite() - entity.getQuantite());
        int manque = entity.getLigneOperation().getQuantiteEcart() - entity.getQuantite();
        System.out.println("Dans Perte Service manque = " + manque);
        if (manque >= 0)
        {
            fourniture.setManque(manque);
        }
        fournitureDao.save(fourniture);
        return entity;
    }

    @Override
    public Page<Perte> findPagenated(String numero, int quantite, Date datePerte, int page, Integer size)
    {
        return perteDao.findPagenated('%' + numero + '%', quantite, datePerte, new PageRequest(page, size));
    }

    @Override
    public Perte findPerteByLigneAudit(Long ligneAuditId)
    {
        return perteDao.findPerteByLigneAudit(ligneAuditId);
    }

}
