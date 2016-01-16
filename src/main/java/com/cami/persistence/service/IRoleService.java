/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.User;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IRoleService extends IOperations<Role>
{

    public Role findByUserParam(User user);

    public Role createRole(final Role role);

    public void deleteRole(final long id);

    public Role findByUser(User user);

    public Role updateUser(Role role);

    public void disableEntity(Role entity);

    Page<Role> findPaginated(String nom, int page, Integer size);

    Page<Role> retrieveUsers(String nom, int page, Integer size);

    public Role retrieveAUser(String username);

    public List<Role> retrieveCommerciaux();
}
