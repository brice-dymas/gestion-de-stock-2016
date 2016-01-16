/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Categorie;
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
public interface ICategorieDao extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie>
{

    @Query("SELECT c FROM Categorie c WHERE c.intitule LIKE :intitule")
    public List<Categorie> retrieveByCategorie(@Param("intitule") String intitule);

    @Query("SELECT c FROM Categorie c WHERE c.intitule LIKE :intitule")
    Page<Categorie> searchByIntitutle(@Param("intitule") String intitule, Pageable pageable);

    @Query("SELECT c FROM Categorie c WHERE c.intitule LIKE :intitule")
    Page<Categorie> searchCategories(@Param("intitule") String intitule, Pageable pageable);
}
