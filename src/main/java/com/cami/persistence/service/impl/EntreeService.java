/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ICategorieDao;
import com.cami.persistence.dao.IEntreeDao;
import com.cami.persistence.dao.IFournitureDao;
import com.cami.persistence.dao.ILotDao;
import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Entree;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Lot;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.User;
import com.cami.persistence.service.IEntreeService;
import com.cami.persistence.service.common.AbstractService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("entreeService")
@Transactional
public class EntreeService extends AbstractService<Entree> implements IEntreeService
{

    @Autowired
    ILotDao lotDao;

    @Autowired
    IFournitureDao fournitureDao;

    @Autowired
    IRoleDao roleDao;

    @Autowired
    ICategorieDao categorieDao;

    @Autowired
    IEntreeDao entreeDao;

    @Override
    protected PagingAndSortingRepository<Entree, Long> getDao()
    {
        return entreeDao;
    }

    @Override
    @Transactional
    public Entree create(Entree entity)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        entity.setDateEntree(new Date());
        entity.setUser(userConnected);
        entity.setCategorie(categorieDao.getCategorie(entity.getCategorie().getIntitule()));
        final Entree entree = entreeDao.save(entity);
        for (Lot lot : entity.getLots())
        {
            Fourniture fourniture = fournitureDao.findOne(lot.getFourniture().getId());
            lot.setDateEntree(new Date());
            lot.setTotalMontant(lot.getPrixUnitaire() * lot.getQuantite());
            lot.setEntree(entree);
            lot.setFourniture(fourniture);
            lotDao.save(lot);

            //Ajout de laquantite manquante
            if (entree.getLigneAuditId() != null)
            {
                fourniture.setManque(fourniture.getManque() - lot.getQuantite());
                fournitureDao.save(fourniture);
            }

        }
        return entree;
    }

    @Override
    public Entree update(Entree entity)
    {
        Fourniture fourniture = null;

        //On recupere tous les lots
        List<Lot> lotsToRemove = lotDao.findByEntreeIdForEdit(entity.getId());

        //On retire les anciennes quantités des lots
        for (Lot lot : lotsToRemove)
        {
            fourniture = lot.getFourniture();
            fourniture.setQuantite(fourniture.getQuantite() - lot.getQuantite());

            //Retrait de la quantité manquante
            if (entity.getLigneAuditId() != null)
            {
                fourniture.setManque(fourniture.getManque() + lot.getQuantite());
            }
            fournitureDao.save(fourniture);
            lotDao.delete(lot);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName());
        final Entree entreeToUpdate = entreeDao.findOne(entity.getId());
        entreeToUpdate.setDateEntree(new Date());
        entreeToUpdate.setUser(userConnected);
        entreeToUpdate.setCategorie(categorieDao.getCategorie(entity.getCategorie().getIntitule()));
        final Entree entree = entreeDao.save(entreeToUpdate);

        for (Lot lot : entity.getLots())
        {
            fourniture = fournitureDao.findOne(lot.getFourniture().getId());
            lot.setDateEntree(new Date());
            lot.setTotalMontant(lot.getPrixUnitaire() * lot.getQuantite());
            lot.setEntree(entree);
            lot.setFourniture(fourniture);

            //Ajout de laquantite manquante
            if (entree.getLigneAuditId() != null)
            {
                fourniture.setManque(fourniture.getManque() - lot.getQuantite());
                fournitureDao.save(fourniture);
            }

            lotDao.save(lot);

        }

        return entree;
    }

    public int isInside(List<Lot> lots, Lot lot)
    {
        int i = 0;
        for (; i < lots.size() && lots.get(i).getId().equals(lot.getId()); i++)
        {
        }

        return i;
    }

    @Override
    public List<Entree> findByCategorie(Categorie categorie)
    {
        return entreeDao.findByCategorie(categorie);
    }

    @Override
    public List<Entree> findByUser(User user)
    {
        return entreeDao.findByUser(user);
    }

    @Override
    public Page<Entree> findPaginated(long categorieID, Date dateOperation, String designation, int page, Integer size)
    {
        if (categorieID == -1)
        {
            return entreeDao.findPaginated(dateOperation, '%' + designation + '%', new PageRequest(page, size));
        }
        else
        {
            return entreeDao.findPaginated(categorieID, dateOperation, '%' + designation + '%', new PageRequest(page, size));
        }
    }
}
