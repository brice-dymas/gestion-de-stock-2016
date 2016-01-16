/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
import com.cami.persistence.model.Role;
import com.cami.persistence.model.User;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.IUserService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController
{

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    // read - all
    /**
     *
     * @param model
     * @param webRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        final String nom = webRequest.getParameter("querynom") != null ? webRequest.getParameter("querynom") : "";
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;

        System.out.println("querynom = " + nom);

        final User user = new User();
        user.setNom(nom);
        final Role role = new Role();
        role.setUser(user);

        final Page<Role> resultPage = roleService.retrieveUsers(nom, page, size);
        System.out.println("taille users =" + resultPage.getContent().size());
        model.addAttribute("page", page);
        model.addAttribute("user", role);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("users", resultPage.getContent());
        return "user/index";
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Role role = roleService.findOne(id);
        model.addAttribute("user", role);
        return "user/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Role role = new Role();
        model.addAttribute("user", role);
        return "user/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Role role,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("nous somme dans le USER_controlleur  ");
        if (result.hasErrors()) {
            System.out.println("il ya ereur");
            model.addAttribute("error", "error");
            model.addAttribute("user", role);
            return "user/new";
        }
        if (!role.getUser().getPassword().equals(role.getUser().getConfirmPassword())) {
            System.out.println("mots de passe not identiques: password=" + role.getUser().getPassword() + " et confirm=" + role.getUser().getConfirmPassword());
            model.addAttribute("user", role);
            model.addAttribute("password.error", "password.error");
            return "user/new";
        }
        else {

            try {
                redirectAttributes.addFlashAttribute("info", "alert.success.new");
                role.getUser().setEnabled(true);
                System.out.println("depuis controller: role=" + role.getRole());
                roleService.createRole(role);
                return "redirect:/user/" + role.getId() + "/show";
            }
            catch (Exception ex) {
                model.addAttribute("exist", "exist");
                model.addAttribute("user", role);
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                return "user/new";

            }

        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Role role, final ModelMap model)
    {
        System.out.println("in delete action for user with ID=" + role.getId());
        Role roleToDelete = roleService.findOne(role.getId());
        if (roleToDelete.getUser().isEnabled() == true) {
            roleToDelete.getUser().setEnabled(false);
        }
        else {
            roleToDelete.getUser().setEnabled(true);
        }

        System.out.println("deleteAction of a user =" + roleToDelete.getId() + " -Role=" + roleToDelete.getRole() + " username=" + roleToDelete.getUser().getUsername() + " enabled=" + roleToDelete.getUser().isEnabled());
        roleService.updateUser(roleToDelete);
        return "redirect:/user/";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        final Role userConnected = roleService.retrieveAUser(name);
        final Role role = roleService.findOne(id);
        if (userConnected.getId() == role.getId() | userConnected.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("fonction_user", userConnected.getRole());
            model.addAttribute("user", role);
            return "user/edit";
        }
        else {
            return "redirect:/403";
        }

    }

    @RequestMapping(value = "{id}/editSimpleUser", method = RequestMethod.GET)
    public String editSimpleUser(@PathVariable("id") final Long id, final ModelMap model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        final Role userConnected = roleService.retrieveAUser(name);
        final Role role = roleService.findOne(id);
        if (userConnected.getId() == role.getId() | userConnected.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("fonction_user", userConnected.getRole());
            model.addAttribute("user", role);
            return "user/sedit";
        }
        else {
            return "redirect:/403";
        }

    }

    @RequestMapping(value = "/{id}/updateSimpleUser", method = RequestMethod.POST)
    public String updateSimpleUserAction(final ModelMap model, @PathVariable("id") final Long id,
            final Role role, final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("info", "alert.success.new");
        System.out.println("in controller user role= " + role.getRole());
        final Role roleUpdated = roleService.updateUser(role);
        System.out.println("là c sur tout va bien et le role nouveau c " + roleUpdated.getRole());
        return "redirect:/welcome";

    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @PathVariable("id") final Long id,
            @Valid final Role role, final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("here we are in the controller update method");
        System.out.println("the role= " + role.getRole() + " -P=" + role.getUser().getPassword() + " -U" + role.getUser().getUsername());
        if (result.hasErrors()) {
            System.out.println("erreur lors de l'update");
            model.addAttribute("error", "error");
            model.addAttribute("user", role);
            return "user/edit";
        }
        if (!role.getUser().getPassword().equals(role.getUser().getConfirmPassword())) {
            System.out.println("mots de passe not identiques: password=" + role.getUser().getPassword() + " et confirm=" + role.getUser().getConfirmPassword());
            model.addAttribute("password.error", "password.error");
            model.addAttribute("user", role);
            return "user/edit";
        }
        else {
            System.out.println("tout va bien ... ou presque! ");
            try {

                redirectAttributes.addFlashAttribute("info", "alert.success.new");
                System.out.println("in controller user role= " + role.getRole());
                final Role roleUpdated = roleService.updateUser(role);
                System.out.println("là c sur tout va bien et le role nouveau c " + roleUpdated.getRole());
                return "redirect:/user/" + roleUpdated.getId() + "/show";
            }
            catch (Exception ex) {
                System.out.println("le username choisi existant");
                model.addAttribute("exist", "exist");
                model.addAttribute("user", role);
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                return "user/edit";

            }
        }
    }

    @ModelAttribute("roles")
    public Map<Long, String> populateRolesFields()
    {
        final Map<Long, String> results = new HashMap();
        results.put(1L, "ADMINISTRATEUR");
        results.put(2L, "TRESORIER");
        results.put(3L, "COMMERCIAL");
        return results;
    }

}
