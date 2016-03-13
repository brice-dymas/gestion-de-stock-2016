/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ICategorieDao;
import com.cami.persistence.dao.IFournitureDao;
import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.common.AbstractService;
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
 * @author samuel   < smlfolong@gmail.com >
 */
@Service("fournitureService")
public class FournitureService extends AbstractService<Fourniture> implements IFournitureService
{

    @Autowired
    private IFournitureDao iFournitureDao;

    @Autowired
    private ICategorieDao iCategorieDao;

    @Override
    protected PagingAndSortingRepository<Fourniture, Long> getDao()
    {
        return iFournitureDao;
    }

    @Override
    public List<Fourniture> findExisting()
    {
        return iFournitureDao.findExisting();
    }

    @Override
    public Fourniture create(Fourniture entity)
    {
        entity.setCategorie(iCategorieDao.findOne(entity.getCategorie().getId()));
        return iFournitureDao.save(entity);
    }

    @Override
    public Fourniture update(Fourniture entity)
    {
        Fourniture fournitureToUpdate = iFournitureDao.findOne(entity.getId());
        fournitureToUpdate.setReference(entity.getReference());
        fournitureToUpdate.setDesignation(entity.getDesignation());
//        fournitureToUpdate.setQuantite(entity.getQuantite());
        fournitureToUpdate.setSeuil(entity.getSeuil());
        fournitureToUpdate.setCategorie(iCategorieDao.findOne(entity.getCategorie().getId()));
        return iFournitureDao.save(fournitureToUpdate);
    }

    @Override
    public void delete(Fourniture entity)
    {
        iFournitureDao.delete(entity);
    }

    @Override
    public void deleteById(long entityId)
    {
        iFournitureDao.delete(entityId);
    }

    @Override
    public List<Fourniture> findByCategorie(Categorie categorie)
    {
        return iFournitureDao.findByCategorie(categorie);
    }

    @Override
    public Map<Long, String> findByCategorieName(String categorie)
    {
        final List<Fourniture> fournitures = iFournitureDao.findByCategorieName('%' + categorie + '%');
        Map<Long, String> listMap = new HashMap<>();
        for (Fourniture fourniture : fournitures) {
            listMap.put(fourniture.getId(),
                    fourniture.getDesignation());
        }
        return listMap;
    }

    @Override
    public Page<Fourniture> findPaginated(Long Id, String designation, String reference, int nombrePage, Integer size)
    {
        if (Id == -1) {
            return iFournitureDao.findPaginated('%' + designation + '%', '%' + reference + '%', new PageRequest(nombrePage, size));
        }
        else {
            return iFournitureDao.findPaginated(Id, '%' + designation + '%', '%' + reference + '%', new PageRequest(nombrePage, size));
        }

    }
}
