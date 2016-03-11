/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Lot;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface ILotService extends IOperations<Lot>
{

    public Page<Lot> findByFourniture(final long id, int nombrePage, int size);

    public List<Lot> findLotsForFifo(final long id);

    public List<Lot> findByEntreeId(final long id);

    public Map<Long, String> getEntreeFournitures(final long id);

    public Map<Long, String> getFournituresForPerte(final long id);

    public List<Lot> filterByLigneAudit(final long id);

    public Lot findOneByLigneAudit(final long id);

    public List<Lot> findByEntreeIdForEdit(final long id);

    Page<Lot> searchLots(String numero, Date dateEntree, float prixUnitaire, float prixVenteUnitaire,
            int quantite, float totalMontant, String etat, int page, Integer size);

    Page<Lot> search(long id, Date debut, Date fin, Integer quantite, int page, Integer size);
}
