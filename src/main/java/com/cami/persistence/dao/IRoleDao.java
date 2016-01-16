/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Role;
import com.cami.persistence.model.User;
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
public interface IRoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>
{

    @Query("SELECT R FROM Role R WHERE R.user= :user")
    public Role findByUserParam(@Param("user") User user);

    @Query("SELECT r FROM Role r WHERE r.user.username = :username")
    public Role retrieveAUser(@Param("username") String username);

    @Query("SELECT R FROM Role R WHERE R.role = 'ROLE_COMMERCIAL'")
    public List<Role> retrieveCommerciaux();

    @Query("SELECT r FROM Role r WHERE r.user.nom LIKE :nom")
    Page<Role> findPaginated(@Param("nom") String nom, Pageable pageable);

    public Role findByUser(User user);

    @Query("SELECT r FROM Role r WHERE r.user.nom LIKE :nom OR r.user.username LIKE :nom")
    Page<Role> retrieveUsers(@Param("nom") String nom, Pageable pageable);
}
