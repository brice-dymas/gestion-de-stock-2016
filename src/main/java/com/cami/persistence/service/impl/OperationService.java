/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IFournitureDao;
import com.cami.persistence.dao.IJournalDao;
import com.cami.persistence.dao.ILigneOperationDao;
import com.cami.persistence.dao.ILotDao;
import com.cami.persistence.dao.IOperationDao;
import com.cami.persistence.dao.IPerteDao;
import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.dao.ITypeOperationDao;
import com.cami.persistence.model.Departement;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Journal;
import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Lot;
import com.cami.persistence.model.Operation;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.User;
import com.cami.persistence.service.IDepartementService;
import com.cami.persistence.service.ILotService;
import com.cami.persistence.service.IOperationService;
import com.cami.persistence.service.common.AbstractService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("operationService")
@Transactional
public class OperationService extends AbstractService<Operation> implements IOperationService
{

    @Autowired
    IFournitureDao fournitureDao;

    @Autowired
    IJournalDao journalDao;

    @Autowired
    IOperationDao operationDao;

    @Autowired
    ITypeOperationDao typeOperationDao;

    @Autowired
    ILigneOperationDao ligneOperationDao;

    @Autowired
    ILotService lotService;

    @Autowired
    IDepartementService departementService;

    @Autowired
    ILotDao iLotDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    IPerteDao perteDao;

    @Override
    protected PagingAndSortingRepository<Operation, Long> getDao()
    {
        return operationDao;
    }

    @Override
    @Transactional
    public Operation create(Operation operation)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        operation.setUser(userConnected);
        operation.setDepartement(departementService.findOne(operation.getDepartement().getId()));
        operation.setTypeOperation(typeOperationDao.findByIntitule("sortie"));
        operation = operationDao.save(operation);
        for (final LigneOperation ligneOperation : operation.getLigneOperations()) {
            if (ligneOperation == null) {
                Logger.getLogger(OperationService.class.getName()).log(Level.INFO, "ligne d'opération nulle");
            }
            else {
                Logger.getLogger(OperationService.class.getName()).log(Level.INFO, "ligne d'opération correcte");
                ligneOperation.setOperation(operation);
                Fourniture fourniture = fournitureDao.findOne(ligneOperation.getFourniture().getId());
                ligneOperation.setFourniture(fourniture);
                List<Lot> lotsToUpdate = doFifo(ligneOperation);
                fourniture.setQuantite(fourniture.getQuantite() - ligneOperation.getQuantite());
                fournitureDao.save(fourniture);
                for (Lot lotsToUpdate1 : lotsToUpdate) {
                    lotService.update(lotsToUpdate1);
                }
                ligneOperationDao.save(ligneOperation);
            }
        }
        return operation;
    }

    @Transactional
    @Override
    public Operation createAudit(Operation operation)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName());
        operation.setUser(userConnected);
        operation.setDepartement(departementService.findOne(operation.getDepartement().getId()));
        operation.setTypeOperation(typeOperationDao.findByIntitule("audit"));
        Operation audit = operationDao.save(operation);

        for (final LigneOperation ligneOperation : audit.getLigneOperations()) {
            if (ligneOperation == null) {
                Logger.getLogger(OperationService.class.getName()).log(Level.INFO, "ligne d'opération nulle");
            }
            else {
                Fourniture fourniture = fournitureDao.findOne(ligneOperation.getFourniture().getId());
                Logger.getLogger(OperationService.class.getName()).log(Level.INFO, "ligne d'opération  non nulle");
                ligneOperation.setOperation(audit);
                ligneOperation.setFourniture(fourniture);
                LigneOperation lp = ligneOperationDao.save(ligneOperation);

                if (lp.getQuantiteEcart() > 0) {
                    fourniture.setPerte(lp.getQuantiteEcart());
                    fournitureDao.save(fourniture);
                }
            }
        }

        return audit;
    }

    @Override
    @Transactional
    public void delete(Operation entity)
    {
        List<LigneOperation> ligneOperations = ligneOperationDao.filterByOperationId(entity.getId());
        for (LigneOperation ligneOperation : ligneOperations) {
            ligneOperationDao.delete(ligneOperation);
        }
        operationDao.delete(entity);
    }

    @Override
    @Transactional
    public Operation update(final Operation operation)
    {
//        restoreBDState(operation);

        Fourniture fourniture = null;

        Operation editOperation = operationDao.findOne(operation.getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user

        editOperation.setNumero(operation.getNumero());
        editOperation.setUser(userConnected);
        editOperation = operationDao.save(editOperation);

        for (final LigneOperation ligneOperation : operation.getLigneOperations()) {
            if (ligneOperation == null) {
                Logger.getLogger(OperationService.class.getName()).log(Level.INFO, "ligne d'opération nulle");
            }
            else {
                ligneOperation.setId(null);
                ligneOperation.setOperation(editOperation);
                fourniture = fournitureDao.findOne(ligneOperation.getFourniture().getId());
                ligneOperation.setFourniture(fourniture);
                List<Lot> lotsToUpdate = doFifoForUpdate(ligneOperation);
                fourniture.setQuantite(fourniture.getQuantite() - ligneOperation.getQuantite());
                fournitureDao.save(fourniture);
                for (Lot lotsToUpdate1 : lotsToUpdate) {
                    lotService.update(lotsToUpdate1);
                }
                ligneOperationDao.save(ligneOperation);
            }
        }
        return editOperation;
    }

    @Override
    @Transactional
    public Operation updateAudit(Operation operation)
    {
        List<LigneOperation> ligneOperations = ligneOperationDao.filterByOperationId(operation.getId());
        for (LigneOperation ligneOp : ligneOperations) {
            ligneOperationDao.delete(ligneOp);
        }

        final Operation auditToUpdate = operationDao.findOne(operation.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName());
        auditToUpdate.setUser(userConnected);
        final Operation audit = operationDao.save(auditToUpdate);

        for (LigneOperation ligneOperation : operation.getLigneOperations()) {
            ligneOperation.setOperation(audit);
            ligneOperation.setDateRegulation(ligneOperation.getDateRegulation());
            ligneOperation.setObservation(ligneOperation.getObservation());
            ligneOperation.setFourniture(fournitureDao.findOne(ligneOperation.getFourniture().getId()));
            ligneOperationDao.save(ligneOperation);
        }
        return auditToUpdate;
    }

    @Override
    public Operation findBynumero(String numero)
    {
        return operationDao.findBynumero("%" + numero + "%");
    }

    @Override
    public List<Operation> findByDepartement(Departement departement)
    {
        return operationDao.findByDepartement(departement);
    }

    @Override
    public List<Operation> findByUser(User user)
    {
        return operationDao.findByUser(user);
    }

    @Override
    public void restoreInitialBdState(final Operation operation)
    {
        Fourniture fourniture;
        Lot lot;
        //On recupere tous les lots
        List<LigneOperation> ligneOperationsToRemove = ligneOperationDao.filterByOperationId(operation.getId());

        //On retire les anciennes quantités des lots
        for (LigneOperation ligneOperation : ligneOperationsToRemove) {
            fourniture = ligneOperation.getFourniture();
            fourniture.setQuantite(fourniture.getQuantite() + ligneOperation.getQuantite());
            fournitureDao.save(fourniture);
            List<Journal> journals = journalDao.findByLigneOperationID(ligneOperation.getId());
            for (Journal journal : journals) {
                lot = journal.getLot();
                lot.setQuantite(lot.getQuantite() + journal.getQuantiteRetirer());
                iLotDao.save(lot);
                journalDao.delete(journal);
            }
            ligneOperationDao.delete(ligneOperation);
        }

    }

    public List<Lot> doFifo(LigneOperation ligneOperation)
    {
        final List<Lot> listLots = lotService.findLotsForFifo(ligneOperation.getFourniture().getId());
        List<Lot> lotsModifier = new ArrayList<>();
        int quantiteRetrait = ligneOperation.getQuantite();
        for (int i = 0; i < listLots.size() && quantiteRetrait > 0; i++) {
            Journal journal = new Journal();
            journal.setLigneOperation(ligneOperation);

            if (listLots.get(i).getQuantite() <= quantiteRetrait) {
                quantiteRetrait -= listLots.get(i).getQuantite();
                journal.setLot(listLots.get(i));
                journal.setQuantiteRetirer(listLots.get(i).getQuantite());
                listLots.get(i).setQuantite(0);
            }
            if (listLots.get(i).getQuantite() > quantiteRetrait) {
                listLots.get(i).setQuantite(listLots.get(i).getQuantite() - quantiteRetrait);
                journal.setLot(listLots.get(i));
                journal.setQuantiteRetirer(quantiteRetrait);
                quantiteRetrait = 0;
            }
            listLots.get(i).setModifiable(false);
            lotsModifier.add(listLots.get(i));
            journalDao.save(journal);
        }
        return lotsModifier;
    }

    // Pour l'update
    public List<Lot> doFifoForUpdate(LigneOperation ligneOperation)
    {
        final List<Lot> listLots = lotService.findLotsForFifo(ligneOperation.getFourniture().getId());
        List<Lot> lotsModifier = new ArrayList<>();
        int quantiteRetrait = ligneOperation.getQuantite();
        for (int i = 0; i < listLots.size() && quantiteRetrait > 0; i++) {
            Journal journal = new Journal();
            journal.setLigneOperation(ligneOperation);
            if (listLots.get(i).getQuantite() <= quantiteRetrait) {
                quantiteRetrait -= listLots.get(i).getQuantite();
                journal.setLot(listLots.get(i));
                journal.setQuantiteRetirer(listLots.get(i).getQuantite());
                listLots.get(i).setQuantite(0);
            }
            if (listLots.get(i).getQuantite() > quantiteRetrait) {
                listLots.get(i).setQuantite(listLots.get(i).getQuantite() - quantiteRetrait);

                journal.setLot(listLots.get(i));
                journal.setQuantiteRetirer(quantiteRetrait);
                quantiteRetrait = 0;
            }
            listLots.get(i).setModifiable(false);
            lotsModifier.add(listLots.get(i));
            journalDao.save(journal);
        }
        return lotsModifier;
    }

    @Override
    public Page<Operation> findPaginated(String intitule, int page, Integer size)
    {
        return operationDao.findPaginated('%' + intitule + '%', new PageRequest(page, size));
    }

    @Override
    public Page<Operation> findPaginated(long departementID, Date dateOperation, String intitule, String designation, int page, Integer size)
    {
        if (departementID == -1) {
            return operationDao.findPaginated(dateOperation, '%' + intitule + '%', '%' + designation + '%', new PageRequest(page, size));
        }
        else {
            return operationDao.findPaginated(departementID, dateOperation, '%' + intitule + '%', '%' + designation + '%', new PageRequest(page, size));
        }
    }
}
