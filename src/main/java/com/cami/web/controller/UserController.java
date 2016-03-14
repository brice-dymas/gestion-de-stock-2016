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
import org.springframework.security.access.annotation.Secured;
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
@Secured(
        {
            "ROLE_USER", "ROLE_ADMIN"
        })
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

        final User user = new User();
        user.setNom(nom);
        final Role role = new Role();
        role.setUser(user);

        final Page<Role> resultPage = roleService.retrieveUsers(nom, page, size);
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Role role = new Role();
        model.addAttribute("user", role);
        return "user/new";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Role role,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("user", role);
            return "user/new";
        }
        if (!role.getUser().getPassword().equals(role.getUser().getConfirmPassword())) {
            model.addAttribute("user", role);
            model.addAttribute("password.error", "password.error");
            return "user/new";
        }
        else {
            try {
                redirectAttributes.addFlashAttribute("info", "alert.success.new");
                role.getUser().setEnabled(true);
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Role role, final ModelMap model)
    {
        Role roleToDelete = roleService.findOne(role.getId());
        if (roleToDelete.getUser().isEnabled() == true) {
            roleToDelete.getUser().setEnabled(false);
        }
        else {
            roleToDelete.getUser().setEnabled(true);
        }
        roleService.updateUser(roleToDelete);
        return "redirect:/user/";
    }

    @Secured("ROLE_ADMIN")
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
        final Role roleUpdated = roleService.updateUser(role);
        return "redirect:/welcome";

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @PathVariable("id") final Long id,
            @Valid final Role role, final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("user", role);
            return "user/edit";
        }
        if (!role.getUser().getPassword().equals(role.getUser().getConfirmPassword())) {
            model.addAttribute("password.error", "password.error");
            model.addAttribute("user", role);
            return "user/edit";
        }
        else {
            try {

                redirectAttributes.addFlashAttribute("info", "alert.success.new");
                final Role roleUpdated = roleService.updateUser(role);
                return "redirect:/user/" + roleUpdated.getId() + "/show";
            }
            catch (Exception ex) {
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
