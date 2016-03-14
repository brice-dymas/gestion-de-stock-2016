/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.dao.IUserDao;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.User;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.common.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("roleService")
public class RoleService extends AbstractService<Role> implements IRoleService
{

    @Autowired
    IRoleDao roleDao;

    @Autowired
    IUserDao userDao;

    @Override
    protected PagingAndSortingRepository<Role, Long> getDao()
    {
        return roleDao;
    }

    @Override
    public Role findByUserParam(User user)
    {
        return roleDao.findByUserParam(user);
    }

    @Override
    public Role findByUser(User user)
    {
        return roleDao.findByUser(user);
    }

    @Override
    public Role createRole(final Role role)
    {
        User user = role.getUser();
        user.setEnabled(true);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setRole(getTheRealRoleOf(role.getRole()));
        user = userDao.save(user);
        role.setUser(user);
        return roleDao.save(role);
    }

    @Override
    public Role updateUser(final Role role)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        final Role roleToUpdate = roleDao.findOne(role.getId());
        User userToUpdate;

        if (!userConnected.getRole().equals("ROLE_ADMIN")) {
            userToUpdate = userDao.findByUsername(userConnected.getUser().getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(role.getUser().getPassword()));
            userToUpdate = userDao.save(userToUpdate);
            roleToUpdate.setUser(userToUpdate);
            return roleDao.save(roleToUpdate);
        }
        else {
            userToUpdate = role.getUser();
            userToUpdate.setEnabled(role.getUser().isEnabled());
            userToUpdate.setNom(role.getUser().getNom());
            userToUpdate.setEmail(role.getUser().getEmail());
            userToUpdate.setUsername(role.getUser().getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(role.getUser().getPassword()));
            userToUpdate = userDao.save(userToUpdate);

            final String vraiRole = getTheRealRoleOf(role.getRole());
            roleToUpdate.setUser(userToUpdate);
            roleToUpdate.setRole(vraiRole);
            Role r = roleDao.save(roleToUpdate);
            return r;
        }

    }

    @Override
    public Page<Role> findPaginated(String nom, int page, Integer size)
    {
        if (nom.length() < 1) {
            return roleDao.findAll(new PageRequest(page, size, Sort.Direction.ASC, "id"));
        }
        else {
            return roleDao.findPaginated('%' + nom + '%', new PageRequest(page, size, Sort.Direction.ASC, "id"));
        }
    }

    /**
     * on ne doit pas supprimer un utilisateur car on doit garder son historique
     * aussi cette méthode va juste crypter le username de façon à ce que
     * l'utilisateur que l'on veut supprimer ne puisse plus avoir accès à son
     * compte (puisqu'il ne connaitra plus son username car celui est encrypté)
     * à moins qu'un administrateur ne modifie son compte pour cela
     *
     * @param id: the id of the user to delete
     */
    @Override
    public void deleteRole(final long id)
    {
        Role roleToDelete = roleDao.findOne(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        roleToDelete.getUser().setUsername(passwordEncoder.encode(roleToDelete.getUser().getUsername()));
        roleDao.save(roleToDelete);
    }

    private String getTheRealRoleOf(String roleToBuildFrom)
    {
        String role = "ros";
        if (roleToBuildFrom.equals("2") | roleToBuildFrom.equals("ROLE_TRESORIER")) {
            role = "ROLE_TRESORIER";
        }
        if (roleToBuildFrom.equals("1") | roleToBuildFrom.equals("ROLE_ADMIN")) {
            role = "ROLE_ADMIN";
        }
        if (roleToBuildFrom.equals("3") | roleToBuildFrom.equals("ROLE_COMMERCIAL")) {
            role = "ROLE_COMMERCIAL";
        }
        return role;
    }

    @Override
    public Page<Role> retrieveUsers(String nom, int page, Integer size)
    {
        if (nom.length() < 1) {
            return roleDao.findAll(new PageRequest(page, size, Sort.Direction.ASC, "role"));
        }
        else {
            return roleDao.retrieveUsers('%' + nom + '%', new PageRequest(page, size, Sort.Direction.ASC, "role"));
        }
    }

    @Override
    public List<Role> retrieveCommerciaux()
    {
        return roleDao.retrieveCommerciaux();
    }

    @Override
    public Role retrieveAUser(String username)
    {
        return roleDao.retrieveAUser(username);
    }

    @Override
    public void disableEntity(Role entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
