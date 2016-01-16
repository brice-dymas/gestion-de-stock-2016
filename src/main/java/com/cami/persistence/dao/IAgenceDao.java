/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Agence;
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
public interface IAgenceDao extends JpaRepository<Agence, Long>, JpaSpecificationExecutor<Agence>
{

    @Query("SELECT a FROM Agence a WHERE a.code LIKE :code AND a.intitule LIKE :intitule "
            + " AND a.region LIKE :region ")
    Page<Agence> searchAgencesSuivant(@Param("code") String code,
            @Param("intitule") String intitule, @Param("region") String region,
            Pageable pageable);

}
