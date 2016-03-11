/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ILigneOperationDao;
import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.service.ILigneOperationService;
import com.cami.persistence.service.common.AbstractService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("ligneOperationService")
public class LigneOperationService extends AbstractService<LigneOperation> implements ILigneOperationService
{

    @Autowired
    ILigneOperationDao ligneOperationDao;

    @Override
    protected PagingAndSortingRepository<LigneOperation, Long> getDao()
    {
        return ligneOperationDao;
    }

    @Override
    public List<LigneOperation> filterByOperation(final Long id)
    {
        return ligneOperationDao.filterByOperationId(id);
    }

    @Override
    public Page<LigneOperation> findByFourniture(long id, String typeOperation, Date debut, Date fin, int page, int size)
    {
        return ligneOperationDao.filterByFournitureId(id, '%' + typeOperation + '%', debut, fin, new PageRequest(page, size));
    }

    @Override
    public Page<LigneOperation> findByFourniture(long id, Date debut, Date fin, int page, int size)
    {
        return ligneOperationDao.filterByFournitureId(id, debut, fin, new PageRequest(page, size));
    }

    @Override
    public Page<LigneOperation> findByFourniture(long id, int page, int size)
    {
        return ligneOperationDao.filterByFournitureId(id, new PageRequest(page, size));
    }

}
