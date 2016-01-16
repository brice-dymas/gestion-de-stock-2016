/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Lot;
import com.cami.persistence.service.ICategorieService;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILotService;
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
@RequestMapping("/fourniture")
public class FournitureController
{

    @Autowired
    ILotService lotService;
    @Autowired
    private IFournitureService iFournitureService;

    @Autowired
    private ICategorieService iCategorieService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String showAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Fourniture fourniture = iFournitureService.findOne(id);
        List<Lot> listeLots = lotService.findByFourniture(id);
        model.addAttribute("lots", listeLots);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Fourniture fourniture = new Fourniture();
        fourniture.setQuantite(0);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/new";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Fourniture fourniture = iFournitureService.findOne(id);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteAction(@PathVariable("id") final Long id, final RedirectAttributes redirectAttributes)
    {
        final Fourniture fournitureToDisable = iFournitureService.findOne(id);
        iFournitureService.delete(fournitureToDisable);
        return "redirect:/fourniture/";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

//        final String designation = webRequest.getParameter("designation") != null ? webRequest.getParameter("designation") : "";
//        final String reference = webRequest.getParameter("reference") != null ? webRequest.getParameter("reference") : "";
//        final Integer quantite = webRequest.getParameter("quantite") != null ? Integer.valueOf(webRequest.getParameter("quantite")) : 0;
//        final Integer seuil = webRequest.getParameter("seuil") != null ? Integer.valueOf(webRequest.getParameter("seuil")) : 0;
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;

        final Page<Fourniture> resultPage = iFournitureService.findPaginated(page, size);

//        final Fourniture fourniture = new Fourniture();
//        fourniture.setReference(reference);
//        fourniture.setDesignation(designation);
//        fourniture.setQuantite(quantite);
//        fourniture.setSeuil(seuil);
//        model.addAttribute("fourniture", fourniture);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("fournitures", resultPage.getContent());
        return "fourniture/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Fourniture fourniture, final ModelMap model,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {
        System.out.println("dans le controller");
        if (result.hasErrors())
        {
            System.out.println("dans le controller avec erreur");
            model.addAttribute("error", "error");
            model.addAttribute("fourniture", fourniture);
            return "fourniture/new";
        }
        else
        {
            System.out.println("dans le controller sans erreur");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iFournitureService.create(fourniture);
            return "redirect:/fourniture/" + fourniture.getId() + "/show";
        }
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(@Valid final Fourniture fourniture, final ModelMap model,
            @PathVariable("id") final Long id,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("error", "error");
            return "fourniture/edit";
        }
        else
        {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iFournitureService.update(fourniture);
            return "redirect:/fourniture/" + fourniture.getId() + "/show";
        }
    }

    @ModelAttribute("categories")
    public Map<Long, String> getCategorie()
    {
        Map<Long, String> results = new HashMap<>();
        final List<Categorie> categories = iCategorieService.findAll();
        for (Categorie category : categories)
        {
            results.put(category.getId(), category.getIntitule());
        }
        return results;
    }

}
