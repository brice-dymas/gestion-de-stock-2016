/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Entree;
import com.cami.persistence.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IEntreeService extends IOperations<Entree>
{

    public List<Entree> findByCategorie(Categorie categorie);

    public List<Entree> findByUser(User user);

    Page<Entree> findPaginated(String numero, Date dateOperation,
            String observation, int nombrePage, Integer size);
}
