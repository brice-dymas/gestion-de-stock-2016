/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Agence;
import com.cami.persistence.model.Departement;
import com.cami.persistence.service.IAgenceService;
import com.cami.persistence.service.IDepartementService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Controller
@RequestMapping("/service")
public class DepartementController
{

    @Autowired
    private IDepartementService iDepartementService;

    @Autowired
    private IAgenceService iAgenceService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String showAction(@PathVariable("id") final Long id, final ModelMap model)
    {

        final Departement departement = iDepartementService.findOne(id);
        model.addAttribute("departement", departement);
        return "service/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Departement departement = new Departement();
        model.addAttribute("departement", departement);
        return "service/new";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Departement departement = iDepartementService.findOne(id);
        model.addAttribute("departement", departement);
        return "service/edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteAction(final Departement departement, final ModelMap model)
    {
        Departement departementToDisable = iDepartementService.findOne(departement.getId());
        iDepartementService.delete(departementToDisable);
        return "redirect:/departement";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        final String code = webRequest.getParameter("querycode") != null ? webRequest.getParameter("querycode") : "";
        final String intitule = webRequest.getParameter("queryintitule") != null ? webRequest.getParameter("queryintitule") : "";

        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 5;

        final Page<Departement> resultPage = iDepartementService.searchDepartements(code, intitule, page, size);

        final Departement departement = new Departement();
        departement.setCode(code);
        departement.setIntitule(intitule);
        model.addAttribute("departement", departement);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("departements", resultPage.getContent());

        return "service/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Departement departement, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes)
    {
        System.out.println("dans le controller");
        System.out.println("dans le controller");
        if (result.hasErrors())
        {
            System.out.println("dans le controller avec erreur");
            model.addAttribute("error", "error");
            model.addAttribute("departement", departement);
            return "service/new";
        }
        else
        {
            System.out.println("dans le controller sans erreur");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iDepartementService.create(departement);
            return "redirect:/service/" + departement.getId() + "/show";
        }
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(@Valid final Departement departement, final ModelMap model,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("error", "error");
            model.addAttribute("departement", departement);
            return "service/edit";
        }
        else
        {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iDepartementService.update(departement);
            return "redirect:/service/" + departement.getId() + "/show";
        }
    }

    @ModelAttribute("listAgence")
    public Map<Long, String> populateAgenceFile()
    {

        final Map<Long, String> map = new HashMap<>();

        final List<Agence> agences = iAgenceService.findAll();

        for (Agence agence : agences)
        {
            map.put(agence.getId(), agence.getIntitule());
        }

        return map;
    }
}
