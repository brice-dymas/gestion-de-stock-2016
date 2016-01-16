/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IDepartementDao;
import com.cami.persistence.dao.IEntreeDao;
import com.cami.persistence.dao.IFournitureDao;
import com.cami.persistence.dao.ILotDao;
import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.model.Departement;
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
//
//    @Autowired
//    IFournitureService fournitureService;

    @Autowired
    IFournitureDao fournitureDao;

    @Autowired
    IRoleDao roleDao;

    @Autowired
    IDepartementDao departementDao;

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
        System.out.println("in service method ...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("fetching user connected");
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        System.out.println("setting date entree");
        entity.setDateEntree(new Date());
        entity.setUser(userConnected);
        System.out.println("setting user =" + entity.getUser().getId());
        System.out.println("setting dept");
        entity.setDepartement(departementDao.findOne(entity.getDepartement().getId()));
        System.out.println("departement setted = " + entity.getDepartement().getId());
        System.out.println("saving entry");
        final Entree entree = entreeDao.save(entity);
        System.out.println("entree saved id=" + entity.getId());
        for (Lot lot : entity.getLots())
        {
            System.out.println("tour ligne 1");

            System.out.println("affichage du lot");
            Fourniture fourniture = fournitureDao.findOne(lot.getFourniture().getId());
//            fourniture.setQuantite(fourniture.getQuantite() + lot.getQuantite());
//            fourniture = fournitureDao.save(fourniture);
            System.out.println("Ligne lot correct");
            System.out.println("setting lot");
            lot.setDateEntree(new Date());
            lot.setTotalMontant(lot.getPrixUnitaire() * lot.getQuantite());
            lot.setEntree(entree);
            System.out.println("lot setted id =" + lot.getEntree().getId());
            System.out.println("setting fourniture");
            lot.setFourniture(fourniture);
            System.out.println("fourniture setted id=" + lot.getFourniture().getId());
            System.out.println("now saving");
            lotDao.save(lot);
            System.out.println("item saved id =" + lot.getId());
            System.out.println(lot);

        }

        System.out.println("returning lot saved");
        System.out.println("affichage des éléments ...");
        System.out.println(entity);
        return entree;
    }

    @Override
    public Entree update(Entree entity)
    {
        Fourniture fourniture = null;

        //On recupere tous les lots
        List<Lot> lotsToRemove = lotDao.findByEntreeIdForEdit(entity.getId());
//        List<Lot> lotsToRemove = lotDao.findByEntreeId(entity.getId());

        System.out.println("ToRemove = " + lotsToRemove.size());
        //On retire les anciennes quantités des lots
        for (Lot lot : lotsToRemove)
        {
            fourniture = lot.getFourniture();
            System.out.println("avant modif fourniture.qte=" + fourniture.getQuantite() + " et nom=" + fourniture.getDesignation());
            fourniture.setQuantite(fourniture.getQuantite() - lot.getQuantite());
            fournitureDao.save(fourniture);
            System.out.println("apres modif fourniture.qte=" + fourniture.getQuantite() + " et nom=" + fourniture.getDesignation());
            lotDao.delete(lot);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName());

        final Entree entreeToUpdate = entreeDao.findOne(entity.getId());

        entreeToUpdate.setDateEntree(new Date());
        entreeToUpdate.setUser(userConnected);
        entreeToUpdate.setDepartement(departementDao.findOne(entity.getDepartement().getId()));
        final Entree entree = entreeDao.save(entreeToUpdate);

        for (Lot lot : entity.getLots())
        {
            System.out.println("tour ligne 1");

            System.out.println("affichage du lot");
            fourniture = fournitureDao.findOne(lot.getFourniture().getId());
            System.out.println("autre boucle");
            System.out.println("\n avant creation des lots, frture.qte=" + fourniture.getQuantite() + " et nom=" + fourniture.getDesignation());
//            fourniture.setQuantite(fourniture.getQuantite() + lot.getQuantite());
//            fourniture = fournitureDao.save(fourniture);
            System.out.println("\n apres creation des lots, frture.qte=" + fourniture.getQuantite() + " et nom=" + fourniture.getDesignation());
            System.out.println("Ligne lot correct");
            System.out.println("setting lot");
            lot.setDateEntree(new Date());
            lot.setTotalMontant(lot.getPrixUnitaire() * lot.getQuantite());
            lot.setEntree(entree);
            System.out.println("lot setted id =" + lot.getEntree().getId());
            System.out.println("setting fourniture");
            lot.setFourniture(fourniture);
            System.out.println("fourniture setted id=" + lot.getFourniture().getId());
            System.out.println("now saving");
            lotDao.save(lot);
            System.out.println("item saved id =" + lot.getId());
            System.out.println(lot);

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
    public List<Entree> findByDepartement(Departement departement)
    {
        return entreeDao.findByDepartement(departement);
    }

    @Override
    public List<Entree> findByUser(User user)
    {
        return entreeDao.findByUser(user);
    }

    @Override
    public Page<Entree> findPaginated(String numero, Date dateOperation, String observation, int page, Integer size)
    {
        return entreeDao.findPaginated('%' + numero + '%', dateOperation, new PageRequest(page, size));
    }
    /**
     * public final String generateNumberOf(Object object) { String
     * numberGenerated = "COD", day = "", month = "", year = ""; Date today =
     * new Date(); day += today.getDate(); month += today.getMonth(); year +=
     * (today.getYear() + 1900); numberGenerated = numberGenerated + year +
     * month + day; if (object.getClass() == Lot.class) { Lot l = (Lot) object;
     * String prix = l.getPrixUnitaire() + ""; prix = prix.substring(0, 3);
     * numberGenerated += l.getFourniture().getId(); numberGenerated += prix;
     * numberGenerated += "LT"; } else { Entree e = (Entree) object;
     * numberGenerated += e.getDepartement().getId(); numberGenerated +=
     * e.getLots().size(); numberGenerated += "ET"; }
     *
     * return numberGenerated; }
     */
}
