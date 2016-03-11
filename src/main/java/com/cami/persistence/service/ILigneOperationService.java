/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.LigneOperation;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface ILigneOperationService extends IOperations<LigneOperation>
{

    public List<LigneOperation> filterByOperation(Long id);

    public Page<LigneOperation> findByFourniture(final long id, int page, int size);

    public Page<LigneOperation> findByFourniture(final long id, final String typeOperation, Date debut, Date fin, int page, int size);

    public Page<LigneOperation> findByFourniture(final long id, Date debut, Date fin, int page, int size);

}
