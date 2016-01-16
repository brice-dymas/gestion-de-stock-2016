/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Categorie;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
public interface ICategorieService extends IOperations<Categorie>
{

    List<Categorie> retrieveByCategorie(String intitule);

    Page<Categorie> findPaginated(String intitule, int page, Integer size);
}
