/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IGererCategorieDao;
import com.cami.persistence.model.GererCategorie;
import com.cami.persistence.service.IGererCategorieService;
import com.cami.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("gererCategorieService")
public class GererCategorieService extends AbstractService<GererCategorie> implements IGererCategorieService
{

    @Autowired
    IGererCategorieDao gererCategorieDao;

    @Override
    protected PagingAndSortingRepository<GererCategorie, Long> getDao()
    {
        return gererCategorieDao;
    }
}
