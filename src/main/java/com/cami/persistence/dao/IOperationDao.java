/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Departement;
import com.cami.persistence.model.Operation;
import com.cami.persistence.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IOperationDao extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation>
{

    @Query("SELECT e FROM Operation e WHERE e.numero LIKE :numero")
    public Operation findBynumero(@Param("numero") String numero);

    @Query("SELECT e FROM Operation e WHERE e.departement = :departement")
    public List<Operation> findByDepartement(@Param("departement") Departement departement);

    @Query("SELECT e FROM Operation e WHERE e.user = :user")
    public List<Operation> findByUser(@Param("user") User user);

    @Query("SELECT e FROM Operation e WHERE e.dateOperation >= :dateOperation AND "
            + "e.departement.id= :id AND e.typeOperation.intitule LIKE :intitule")
    Page<Operation> findPaginated(
            @Param("id") long departementID,
            @Param("dateOperation") Date dateOperation,
            Pageable pageable
    );

    @Query("SELECT e FROM Operation e WHERE "
            + "e.typeOperation.intitule LIKE :intitule")
    Page<Operation> findPaginated(
            @Param("intitule") String intitule,
            Pageable pageable
    );
}
