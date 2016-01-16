/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ICategorieDao;
import com.cami.persistence.model.Categorie;
import com.cami.persistence.service.ICategorieService;
import com.cami.persistence.service.common.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Service("categorieService")
public class CategorieService extends AbstractService<Categorie> implements ICategorieService
{

    @Autowired
    private ICategorieDao iCategorieDao;

    @Override
    protected PagingAndSortingRepository<Categorie, Long> getDao()
    {
        return iCategorieDao;
    }

    @Override
    public Categorie create(Categorie entity)
    {
        return iCategorieDao.save(entity);
    }

    @Override
    public Categorie update(Categorie entity)
    {
        Categorie catToUpdate = iCategorieDao.findOne(entity.getId());
        catToUpdate.setIntitule(entity.getIntitule());
        return iCategorieDao.save(catToUpdate);
    }

    @Override
    public void delete(Categorie entity)
    {
        iCategorieDao.delete(entity);
    }

    @Override
    public void deleteById(long entityId)
    {
        iCategorieDao.delete(entityId);
    }

    @Override
    public List<Categorie> retrieveByCategorie(String intitule)
    {
        return iCategorieDao.retrieveByCategorie(intitule);
    }

    @Override
    public Page<Categorie> findPaginated(String intitule, int page, Integer size)
    {

        return iCategorieDao.searchCategories('%' + intitule + '%', new PageRequest(page, size));
    }
}
