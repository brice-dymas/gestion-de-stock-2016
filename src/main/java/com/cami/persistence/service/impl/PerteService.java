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
import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Lot;
import com.cami.persistence.model.Perte;
import com.cami.persistence.service.IPerteService;
import com.cami.persistence.service.common.AbstractService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Perte entity)
    {
        if (entity == null)
        {
            System.out.println("Cet objet n'existe pas...");
        }
        else
        {
            System.out.println("Initialisation de l'objet avent la suppression...");
            entity.setLigneOperation(null);
            entity.setLot(null);
            System.out.println("Debut de la suppression de l'objet");
            perteDao.delete(entity);
            System.out.println("Suppression de l'element effectuee avec success...");
        }
    }

    @Override
    public Perte create(Perte entity)
    {

        Lot lot = lotDao.findOne(entity.getLot().getId());
        LigneOperation ligneOperation = ligneOperationDao.findOne(entity.getLigneOperation().getId());
        entity.setDatePerte(new Date());
        entity.setLigneOperation(ligneOperation);
        entity.setLot(lot);
        perteDao.save(entity);

        lot.setLigneOperation(ligneOperation);
        lot.setQuantite(lot.getQuantite() - entity.getQuantite());
        lotDao.save(lot);

        // Mettre à jour la fourniture qui avait été marquée comme contenant une perte.
        final Fourniture fourniture = fournitureDao.findOne(entity.getLigneOperation().getFourniture().getId());
        System.out.println("Dans Perte Service Quantite perte = " + entity.getQuantite());
        fourniture.setPerte(fourniture.getPerte() - entity.getQuantite());
        System.out.println("Dans Perte Service Quantite fourniture venant de la bd = " + fourniture.getQuantite());
        fourniture.setQuantite(fourniture.getQuantite() - entity.getQuantite());
        int resteACombler = fourniture.getPerte() - entity.getQuantite();
//        int manque = entity.getLigneOperation().getQuantiteEcart() - entity.getQuantite();
        System.out.println("Dans Perte Service resteACombler = " + resteACombler);
        if (resteACombler >= 0)
        {
            fourniture.setPerte(resteACombler);
        }
        fournitureDao.save(fourniture);
        return entity;
    }

    @Override
    public List<Perte> create(List<Perte> pertes)
    {
        List<Perte> saved = new ArrayList<>();
        for (Perte perte : pertes)
        {
            Lot lot = lotDao.findOne(perte.getLot().getId());
            LigneOperation ligneOperation = ligneOperationDao.findOne(perte.getLigneOperation().getId());
            perte.setDatePerte(new Date());
            perte.setLigneOperation(ligneOperation);
            perte.setLot(lot);
            perteDao.save(perte);

            // Mettre à jour le lot contenant une perte.
            lot.setLigneOperation(ligneOperation);
            lot.setQuantite(lot.getQuantite() - perte.getQuantite());
            lotDao.save(lot);

            // Mettre à jour la fourniture qui avait été marquée comme contenant une perte.
            final Fourniture fourniture = fournitureDao.findOne(perte.getLigneOperation().getFourniture().getId());
            System.out.println("Dans Perte Service Quantite perte = " + perte.getQuantite());
            fourniture.setPerte(fourniture.getPerte() - perte.getQuantite());
            System.out.println("Dans Perte Service Quantite fourniture venant de la bd = " + fourniture.getQuantite());
            fourniture.setQuantite(fourniture.getQuantite() - perte.getQuantite());
            int resteACombler = fourniture.getPerte() - perte.getQuantite();
//        int manque = entity.getLigneOperation().getQuantiteEcart() - entity.getQuantite();
            System.out.println("Dans Perte Service resteACombler = " + resteACombler);
            if (resteACombler >= 0)
            {
                fourniture.setPerte(resteACombler);
            }
            fournitureDao.save(fourniture);
            saved.add(perte);
        }
        return saved;
    }

    @Override
    @Transactional
    public List<Perte> update(Long id, List<Perte> pertes)
    {
        System.out.println("Dans perte service debut perte id = " + id);
        List<Perte> updated = new ArrayList<>();
        Perte perteToUpdate = perteDao.findOne(id);
        System.out.println("Dans perte service perte to update id = " + perteToUpdate.getId());
        System.out.println("Dans perte service perte to update numero = " + perteToUpdate.getNumero());
        final LigneOperation tmp = ligneOperationDao.findOne(perteToUpdate.getLigneOperation().getId());
        System.out.println("Dans perte service ligneOperation tmp = " + tmp.getFourniture().getDesignation());

        //Mise a jour de la fourniture concerne
        final Fourniture fourniture = fournitureDao.findOne(perteToUpdate.getLigneOperation().getFourniture().getId());
        System.out.println("Dans perte service fourniture id = " + fourniture.getId());
        fourniture.setQuantite(fourniture.getQuantite() + perteToUpdate.getQuantite());
        fourniture.setPerte(fourniture.getPerte() + perteToUpdate.getQuantite());
        Fourniture saved = fournitureDao.save(fourniture);
        System.out.println("Dans perte service apres save fourniture quantite + perte = " + saved.getQuantite() + " + " + saved.getPerte());

        //Mise a jour du lot concerne
        final Lot lot = lotDao.findOne(perteToUpdate.getLot().getId());
        System.out.println("Dans perte service lot id = " + lot.getId());
        lot.setLigneOperation(null);
        Lot savedLot = lotDao.save(lot);
        System.out.println("Dans perte service lot ligneOperation = " + lot.getLigneOperation());
        lot.setQuantite(lot.getQuantite() + perteToUpdate.getQuantite());
        System.out.println("Dans perte controller apres save lot ligneOperation + quantite = " + savedLot.getLigneOperation() + " + " + savedLot.getQuantite());

        //Suppression de la perte dans la bd
        perteToUpdate.setLigneOperation(null);
        System.out.println("Dans perte to update ligneOperation = " + perteToUpdate.getLigneOperation());
        perteToUpdate.setLot(null);
        System.out.println("Dans perte to update lot = " + perteToUpdate.getLot());
        perteDao.delete(perteToUpdate);
        System.out.println("Dans perte service perte to update suppression avec succes");

        for (Perte perte : pertes)
        {
            System.out.println("Dans perte service debut save...");
            Lot newLot = lotDao.findOne(perte.getLot().getId());
            System.out.println("Dans perte service lot id = " + newLot.getId());
            LigneOperation newLigneOperation = ligneOperationDao.findOne(tmp.getId());
            System.out.println("Dans perte service ligneOperation id = " + newLigneOperation.getId());
            perte.setDatePerte(new Date());
            System.out.println("Dans perte service date perte = " + perte.getDatePerte());
            perte.setLigneOperation(newLigneOperation);
            System.out.println("Dans perte service ligneOperation = " + perte.getLigneOperation().getId());
            perte.setLot(newLot);
            System.out.println("Dans perte service lot = " + perte.getLot().getId());
            perteDao.save(perte);

            // Mettre à jour le lot contenant une perte.
            newLot.setLigneOperation(newLigneOperation);
            newLot.setQuantite(lot.getQuantite() - perte.getQuantite());
            lotDao.save(newLot);

            // Mettre à jour la fourniture qui avait été marquée comme contenant une perte.
            final Fourniture newFourniture = fournitureDao.findOne(perte.getLigneOperation().getFourniture().getId());
            System.out.println("Dans Perte Service Quantite perte = " + perte.getQuantite());
            newFourniture.setPerte(newFourniture.getPerte() - perte.getQuantite());
            System.out.println("Dans Perte Service Quantite fourniture venant de la bd = " + newFourniture.getQuantite());
            newFourniture.setQuantite(newFourniture.getQuantite() - perte.getQuantite());
            int resteACombler = newFourniture.getPerte() - perte.getQuantite();

            // int manque = entity.getLigneOperation().getQuantiteEcart() - entity.getQuantite();
            System.out.println("Dans Perte Service resteACombler = " + resteACombler);
            if (resteACombler >= 0)
            {
                newFourniture.setPerte(resteACombler);
            }
            fournitureDao.save(newFourniture);
            updated.add(perte);
        }
        return updated;
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
