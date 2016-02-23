/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Departement;
import com.cami.persistence.model.Operation;
import com.cami.persistence.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IOperationService extends IOperations<Operation>
{

    public Operation findBynumero(String numero);

    public Operation createAudit(final Operation entity);

    public Operation updateAudit(final Operation entity);

    public List<Operation> findByDepartement(Departement departement);

    public List<Operation> findByUser(User user);

    Page<Operation> findPaginated(String intitule, int page, Integer size);

    public void restoreInitialBdState(Operation operation);

    Page<Operation> findPaginated(long departementID, Date dateOperation,
            String intitule, String designation, int page, Integer size);
}
