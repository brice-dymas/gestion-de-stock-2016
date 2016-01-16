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
        System.out.println("Role Service et role of this user befor save= " + role.getRole());
        user = userDao.save(user);
//        System.out.println("Role Service et role of this user befor save= " + role.getRole());
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
        System.out.println("updating user with ID " + role.getId());
        System.out.println("in updateUser service method ...");

        if (!userConnected.getRole().equals("ROLE_ADMIN"))
        {
            System.out.println("userConected is not admin launching his update of password ...");
            userToUpdate = userDao.findByUsername(userConnected.getUser().getUsername());
            System.out.println("his username is " + userToUpdate.getUsername());
            System.out.println("encrypting his password ...");
            userToUpdate.setPassword(passwordEncoder.encode(role.getUser().getPassword()));
            System.out.println(" password encrypted  \n Saving new configuration ....");
            userToUpdate = userDao.save(userToUpdate);
            System.out.println("configuration saved");
            roleToUpdate.setUser(userToUpdate);
            System.out.println("updating cache ....");
            return roleDao.save(roleToUpdate);
        }
        else
        {
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
            System.out.println("in update service user role= " + roleToUpdate.getRole());
            System.out.println("updating ... ");
            Role r = roleDao.save(roleToUpdate);
            System.out.println("update finished");
            System.out.println("userToUpdate's username is " + r.getUser().getUsername());
            System.out.println("\n \n \n \n in updateUser service method displaying user updated ");
            System.out.println("deleteAction of a user =" + role.getId() + " -Role=" + role.getRole() + " username=" + role.getUser().getUsername() + " enabled=" + role.getUser().isEnabled());

            return r;
        }

    }

    @Override
    public Page<Role> findPaginated(String nom, int page, Integer size)
    {
        System.out.println("debut find");
        if (nom.length() < 1)
        {
            System.out.println("find sans param");
            return roleDao.findAll(new PageRequest(page, size, Sort.Direction.ASC, "id"));
        }
        else
        {
            System.out.println("find- avec nomParam=" + nom);
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
        System.out.println("in the getTheRealRoleOf method and roleToBuildFrom is " + roleToBuildFrom);
        if (roleToBuildFrom.equals("2") | roleToBuildFrom.equals("ROLE_TRESORIER"))
        {
            role = "ROLE_TRESORIER";
            System.out.println("roleToBuildFrom=2 donc role =" + role);
        }
        if (roleToBuildFrom.equals("1") | roleToBuildFrom.equals("ROLE_ADMIN"))
        {
            role = "ROLE_ADMIN";
            System.out.println("roleToBuildFrom=1 donc role =" + role);
        }
        if (roleToBuildFrom.equals("3") | roleToBuildFrom.equals("ROLE_COMMERCIAL"))
        {
            role = "ROLE_COMMERCIAL";
            System.out.println("roleToBuildFrom=3 donc role =" + role);
        }
        return role;
    }

    @Override
    public Page<Role> retrieveUsers(String nom, int page, Integer size)
    {
        if (nom.length() < 1)
        {
            System.out.println("find sans param");
            return roleDao.findAll(new PageRequest(page, size, Sort.Direction.ASC, "role"));
        }
        else
        {
            System.out.println("find- avec nomParam=" + nom);
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

//    public boolean exists(User user)
//    {
//        return roleDao.retrieveAUser(user.getUsername()) instanceof Role;
//    }
    @Override
    public void disableEntity(Role entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
