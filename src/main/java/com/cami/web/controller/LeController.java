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
import com.cami.persistence.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class LeController
{

    @Autowired
    IRoleService roleService;

    @RequestMapping(value = "/welcome**", method = RequestMethod.GET)
    public ModelAndView welcomePage()
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        final Role user = roleService.retrieveAUser(name);
        final Role userConnected = roleService.retrieveAUser(name);
        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        model.addObject("userConnected", userConnected);
        model.setViewName("aboutMe");
        return model;

    }

//Spring Security see this : index.xhtml
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout)
    {

        ModelAndView model = new ModelAndView();
        if (error != null)
        {
            model.addObject("error", "Mot de Passe ou Nom d'Utilisateur Incorrect !");
        }

        if (logout != null)
        {
            model.addObject("error", "Deconnexion reussie");
        }
        model.setViewName("login");

        return model;

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {
        return "hello";
    }

    @RequestMapping(value = "/index.xhtml", method = RequestMethod.GET)
    public String homeAction(final ModelMap model)
    {

        return "redirect:/entree/";
    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied()
    {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
        {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;

    }

}
