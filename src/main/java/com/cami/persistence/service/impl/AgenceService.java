/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IAgenceDao;
import com.cami.persistence.model.Agence;
import com.cami.persistence.service.IAgenceService;
import com.cami.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Service("agenceService")
public class AgenceService extends AbstractService<Agence> implements IAgenceService
{

    @Autowired
    private IAgenceDao iAgenceDao;

    @Override
    protected PagingAndSortingRepository<Agence, Long> getDao()
    {
        return iAgenceDao;
    }

    @Override
    public Agence update(Agence entity)
    {
        Agence agence = iAgenceDao.findOne(entity.getId());
        agence.setCode(entity.getCode());
        agence.setIntitule(entity.getIntitule());
        agence.setRegion(entity.getRegion());
        return iAgenceDao.save(agence);
    }

    @Override
    public Page<Agence> findPaginated(String code, String intitule, String region, int nombrePage, Integer size)
    {
        return iAgenceDao.searchAgencesSuivant('%' + code + '%', '%' + intitule + '%', '%' + region + '%', new PageRequest(nombrePage, size));
    }

}
