/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IAgenceDao;
import com.cami.persistence.dao.IDepartementDao;
import com.cami.persistence.model.Departement;
import com.cami.persistence.service.IDepartementService;
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
@Service("departementService")
public class DepartementService extends AbstractService<Departement> implements IDepartementService
{

    @Autowired
    private IDepartementDao iDepartementDao;

    @Autowired
    private IAgenceDao iAgenceDao;

    public DepartementService()
    {
        super();
    }

    @Override
    public Departement create(Departement entity)
    {
        entity.setAgence(iAgenceDao.findOne(entity.getAgence().getId()));
        return iDepartementDao.save(entity);
    }

    @Override
    public Departement update(Departement entity)
    {
        Departement dept = iDepartementDao.findOne(entity.getId());
        dept.setCode(entity.getCode());
        dept.setIntitule(entity.getIntitule());
        dept.setAgence(iAgenceDao.findOne(entity.getAgence().getId()));
        return iDepartementDao.save(dept);
    }

    @Override
    public void delete(Departement entity)
    {
        iDepartementDao.delete(entity);
    }

    @Override
    public void deleteById(long entityId)
    {
        iDepartementDao.delete(entityId);
    }

    @Override
    public Departement findOne(long id)
    {
        return iDepartementDao.findOne(id);
    }

    @Override
    public List<Departement> findAll()
    {
        return iDepartementDao.findAll();
    }

    @Override
    protected PagingAndSortingRepository<Departement, Long> getDao()
    {
        return iDepartementDao;
    }

    @Override
    public List<Departement> filterDepartementByAgenceId(long agenceId)
    {
        List<Departement> services;
        services = iDepartementDao.filterDepartementByAgenceId(agenceId);
        return services;
    }

    @Override
    public Page<Departement> searchDepartements(String code, String intitule, int nombrePage, Integer size)
    {
        return iDepartementDao.searchDepartements('%' + code + '%', '%' + intitule + '%', new PageRequest(nombrePage, size));
    }

}
