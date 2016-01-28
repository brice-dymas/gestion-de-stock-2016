/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Lot;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface ILotDao extends JpaRepository<Lot, Long>, JpaSpecificationExecutor<Lot>
{

    @Query("SELECT l FROM Lot l WHERE l.fourniture.id = :idFourniture AND l.quantite>0 ORDER BY l.id ASC")
    public List<Lot> findByFournitureForFifo(@Param("idFourniture") long id);

    @Query("SELECT l FROM Lot l WHERE l.fourniture.id = :idFourniture")
    public List<Lot> findByFourniture(@Param("idFourniture") long id);

    @Query("SELECT l FROM Lot l WHERE l.fourniture.id = :idFourniture AND l.quantite > 0")
    public List<Lot> findByFournitureForPerte(@Param("idFourniture") long id);

    @Query("SELECT l FROM Lot l WHERE l.entree.id= :id ")
    public List<Lot> findByEntreeId(@Param("id") long id);

    @Query("SELECT F FROM Lot l, Fourniture F WHERE l.entree.id= :id AND "
            + " l.entree.categorie.id=F.categorie.id AND l.modifiable=true")
    public List<Fourniture> getEntreeFournitures(@Param("id") long id);

    @Query("SELECT l FROM Lot l WHERE l.fourniture.id = :id")
    public List<Lot> filterByLigneAudit(@Param("id") long id);

    @Query("SELECT l FROM Lot l WHERE l.ligneOperation.id = :idLigneOperation")
    public Lot findOneByLigneAudit(@Param("idLigneOperation") long id);

    @Query("SELECT l FROM Lot l WHERE l.entree.id= :id AND l.modifiable=true")
    public List<Lot> findByEntreeIdForEdit(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Lot l WHERE l.entree.id= :id")
    public void deleteByEntreeId(@Param("id") long id);

    @Query("SELECT l FROM Lot l WHERE l.numero LIKE :numero AND "
            + "l.dateEntree <= :dateEntree AND "
            + "l.prixUnitaire = :prixUnitaire AND "
            + "l.prixVenteUnitaire = :prixVenteUnitaire AND "
            + "l.quantite = :quantite AND "
            + "l.totalMontant = :totalMontant AND "
            + "l.etat LIKE :etat ")
    Page<Lot> searchLots(
            @Param("numero") String numero,
            @Param("dateEntree") Date dateEntree,
            @Param("prixUnitaire") float prixUnitaire,
            @Param("prixVenteUnitaire") float prixVenteUnitaire,
            @Param("quantite") int quantite,
            @Param("totalMontant") float totalMontant,
            @Param("etat") String etat,
            Pageable pageable
    );

}
