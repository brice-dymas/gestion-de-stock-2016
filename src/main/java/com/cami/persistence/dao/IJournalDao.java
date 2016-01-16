/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Journal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IJournalDao extends JpaSpecificationExecutor<Journal>, JpaRepository<Journal, Long>
{

    @Query("SELECT j FROM Journal j WHERE j.lot.id = :id")
    public List<Journal> findByLotID(@Param("id") long idLot);

    @Query("SELECT j FROM Journal j WHERE j.ligneOperation.id = :id")
    public List<Journal> findByLigneOperationID(@Param("id") long idLigneOperation);

    @Query("SELECT j FROM Journal j WHERE j.quantiteRetirer>0")
    public List<Journal> findExisting();

}
