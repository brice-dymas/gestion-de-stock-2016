/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Departement;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
public interface IDepartementDao extends JpaRepository<Departement, Long>, JpaSpecificationExecutor<Departement>
{

    @Query("SELECT d FROM Departement d WHERE d.code LIKE :code AND d.intitule LIKE :intitule ")
    Page<Departement> searchDepartements(@Param("code") String code, @Param("intitule") String intitule, Pageable pageable);

    @Query("SELECT d FROM Departement d WHERE d.agence.id = :agenceId")
    List<Departement> filterDepartementByAgenceId(@Param("agenceId") long agenceId);
}
