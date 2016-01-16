/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Perte;
import java.util.Date;
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
public interface IPerteDao extends JpaRepository<Perte, Long>, JpaSpecificationExecutor<Perte> {

    @Query("SELECT p FROM Perte p WHERE p.ligneOperation.id = :ligneAuditId")
    public Perte findPerteByLigneAudit(@Param("ligneAuditId") Long ligneAuditId);

    @Query("SELECT p FROM Perte p WHERE p.numero LIKE :numero AND "
           + "p.quantite = :quantite AND "
           + "p.datePerte <= :datePerte")
    Page<Perte> findPagenated(
            @Param("numero") String numero,
            @Param("quantite") int quantite,
            @Param("datePerte") Date datePerte,
            Pageable pageable
    );
}
