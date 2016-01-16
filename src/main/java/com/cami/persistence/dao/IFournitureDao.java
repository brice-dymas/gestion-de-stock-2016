/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Fourniture;
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
public interface IFournitureDao extends JpaRepository<Fourniture, Long>, JpaSpecificationExecutor<Fourniture>
{

    @Query("SELECT f FROM Fourniture f WHERE f.categorie = :categorie")
    public List<Fourniture> findByCategorie(@Param("categorie") Categorie categorie);

    @Query("SELECT f FROM Fourniture f WHERE f.quantite>0")
    public List<Fourniture> findExisting();

    @Query("SELECT f FROM Fourniture f WHERE "
            + "f.reference LIKE :reference AND "
            + "f.designation LIKE :designation AND "
            + "f.quantite = :quantite AND "
            + "f.seuil = :seuil ")
    public Page<Fourniture> searchFournitures(
            @Param("reference") String reference,
            @Param("designation") String designation,
            @Param("quantite") int quantite,
            @Param("seuil") int seuil,
            Pageable pageable
    );
}
