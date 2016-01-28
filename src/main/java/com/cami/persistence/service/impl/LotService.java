/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IEntreeDao;
import com.cami.persistence.dao.IFournitureDao;
import com.cami.persistence.dao.ILotDao;
import com.cami.persistence.model.Entree;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Lot;
import com.cami.persistence.service.ILotService;
import com.cami.persistence.service.common.AbstractService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("lotService")
public class LotService extends AbstractService<Lot> implements ILotService
{

    @Autowired
    private ILotDao iLotDao;

    @Autowired
    private IEntreeDao iEntreeDao;

    @Autowired
    private IFournitureDao iFournitureDao;

    @Override
    protected PagingAndSortingRepository<Lot, Long> getDao()
    {
        return iLotDao;
    }

    @Override
    public Lot create(Lot entity)
    {
        Entree entree = iEntreeDao.findOne(entity.getEntree().getId());
        Fourniture fourniture = iFournitureDao.findOne(entity.getFourniture().getId());
        entity.setEntree(entree);
        entity.setFourniture(fourniture);
        return iLotDao.save(entity);
    }

    @Override
    public Lot update(Lot entity)
    {
        System.out.println("in lot service");
        System.out.println("updating lot");
        Lot lotToUpdate = iLotDao.findOne(entity.getId());
        System.out.println("lot fetched");
//        Entree entree = iLotDao.findByEntreeId(lotToUpdate.getEntree().getId());
        System.out.println("entree feteched");
        Fourniture fourniture = iFournitureDao.findOne(entity.getFourniture().getId());
        System.out.println("fourniture fetched");
        lotToUpdate.setDateEntree(entity.getDateEntree());
        System.out.println("date feteched");
        lotToUpdate.setNumero(entity.getNumero());
        System.out.println("numero fetched");
        lotToUpdate.setPrixUnitaire(entity.getPrixUnitaire());
        System.out.println("PU fetched");
        lotToUpdate.setPrixVenteUnitaire(entity.getPrixVenteUnitaire());
        System.out.println("PUV fetched");
        lotToUpdate.setQuantite(entity.getQuantite());
        System.out.println("Qte fetched");
        lotToUpdate.setTotalMontant(entity.getTotalMontant());
        System.out.println("montant T fetched");
        lotToUpdate.setEtat(entity.getEtat());
        System.out.println("etat fetched");
        lotToUpdate.setModifiable(entity.isModifiable());
        System.out.println("modifiable setted");
        lotToUpdate.setFourniture(fourniture);
        System.out.println("fourniture setted");
        System.out.println("now launching the ultimate update ....");
        return iLotDao.save(lotToUpdate);
    }

    @Override
    public void delete(Lot entity)
    {
        iLotDao.delete(entity);
    }

    @Override
    public void deleteById(long entityId)
    {
        iLotDao.delete(entityId);
    }

    @Override
    public List<Lot> findByFourniture(long id)
    {
        return iLotDao.findByFourniture(id);
    }

    @Override
    public Page<Lot> searchLots(String numero, Date dateEntree, float prixUnitaire, float prixVenteUnitaire, int quantite, float totalMontant, String etat, int page, Integer size)
    {
        return iLotDao.searchLots('%' + numero + '%', dateEntree, prixUnitaire, prixVenteUnitaire, quantite, totalMontant, '%' + etat + '%', new PageRequest(page, size));
    }

    @Override
    public List<Lot> findByEntreeId(long id)
    {
        return iLotDao.findByEntreeId(id);
    }

    @Override
    public List<Lot> findByEntreeIdForEdit(long id)
    {
        return iLotDao.findByEntreeIdForEdit(id);
    }

    @Override
    public List<Lot> findLotsForFifo(long id)
    {
        return iLotDao.findByFournitureForFifo(id);
    }

    @Override
    public List<Lot> filterByLigneAudit(long id)
    {
        return iLotDao.filterByLigneAudit(id);
    }

    @Override
    public Lot findOneByLigneAudit(long id)
    {
        return iLotDao.findOneByLigneAudit(id);
    }

    @Override
    public Map<Long, String> getEntreeFournitures(long id)
    {
        List<Fourniture> fournitures = iLotDao.getEntreeFournitures(id);
        Map<Long, String> listMap = new HashMap<>();
        for (Fourniture fourniture : fournitures)
        {
            listMap.put(fourniture.getId(),
                    fourniture.getDesignation());
        }
        return listMap;
    }

    @Override
    public Map<Long, String> getFournituresForPerte(long id)
    {
        List<Lot> lots = iLotDao.findByFournitureForPerte(id);
        Map<Long, String> listMap = new HashMap<>();
        for (Lot lot : lots)
        {
            listMap.put(lot.getId(), lot.getNumero() + " - " + lot.getDateEntree());
        }
        return listMap;
    }

}
