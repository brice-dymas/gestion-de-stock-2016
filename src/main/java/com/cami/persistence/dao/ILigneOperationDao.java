/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.LigneOperation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface ILigneOperationDao extends JpaRepository<LigneOperation, Long>, JpaSpecificationExecutor<LigneOperation>
{

    @Query("SELECT L FROM LigneOperation L WHERE L.operation.id= :id")
    List<LigneOperation> filterByOperationId(@Param("id") final long id);
}
