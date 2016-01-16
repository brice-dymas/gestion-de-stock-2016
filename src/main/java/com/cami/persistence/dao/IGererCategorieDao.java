/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.GererCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IGererCategorieDao extends JpaRepository<GererCategorie, Long>, JpaSpecificationExecutor<GererCategorie>
{

}
