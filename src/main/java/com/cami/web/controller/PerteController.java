/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Equilibre;
import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Lot;
import com.cami.persistence.model.Perte;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILigneOperationService;
import com.cami.persistence.service.ILotService;
import com.cami.persistence.service.IOperationService;
import com.cami.persistence.service.IPerteService;
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
@RequestMapping("/perte")
public class PerteController
{

    @Autowired
    IPerteService perteService;

    @Autowired
    ILotService lotService;

    @Autowired
    IFournitureService fournitureService;

    @Autowired
    IOperationService operationService;

    @Autowired
    ILigneOperationService ligneOperationService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Perte perte = perteService.findOne(id);
        model.addAttribute("perte", perte);
        return "perte/show";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Perte perte = perteService.findOne(id);
        model.addAttribute("perte", perte);
        return "perte/edit";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 5;
        final Page<Perte> resultPage = perteService.findPaginated(page, size);
        final Perte perte = new Perte();
        model.addAttribute("perte", perte);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("pertes", resultPage.getContent());
        return "perte/index";
    }

    @RequestMapping(value = "/{id}/new", method = RequestMethod.GET)
    public String newAction(@PathVariable("id") final Long id,
            final ModelMap model)
    {

        final LigneOperation ligneOperation = ligneOperationService.findOne(id);
        System.out.println("Dans perte controller fourniture id = " + ligneOperation.getFourniture().getId());
        Perte perte = new Perte();
        Equilibre equilibre = new Equilibre();
        perte.setLigneOperation(ligneOperation);
        model.addAttribute("perte", perte);
        model.addAttribute("equilibre", equilibre);
        System.out.println("Dans perte controller quantite ligne operation = " + perte.getLigneOperation().getQuantitePhysique());
        System.out.println("Ligne Operation = " + perte.getLigneOperation().getId());
        return "perte/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(final ModelMap model, Equilibre equilibre,
            final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            System.out.println("Dans Perte controller erreur" + result.getFieldError());
            model.addAttribute("error", "error");
            return "perte/new";
        }
        else
        {
            System.out.println("Dans Perte Controller sans erreur debut...");
            perteService.create(equilibre);
            System.out.println("Dans perte controller fin...");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            return "redirect:/audit/";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Perte perte, final ModelMap model)
    {
        Perte perteToDelete = perteService.findOne(perte.getId());
        System.out.println("deleteAction of a perte =" + perteToDelete.getId());

        perteService.delete(perteToDelete);
        return "redirect:/perte/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @Valid Perte perte,
            final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("error", "error");
            model.addAttribute("perte", perte);
            return "perte/edit";
        }
        else
        {
            perteService.update(perte);
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            return "redirect:/perte/" + perte.getId() + "/show";
        }
    }

    @ModelAttribute("lots")
    public Map<Long, String> getLots()
    {
        Map<Long, String> map = new HashMap<>();
        List<Lot> lots = lotService.findAll();
//        List<Lot> lots = lotService.filterByLigneAudit(id);
        for (Lot lot : lots)
        {
            map.put(lot.getId(), "Lot --> " + lot.getNumero() + " Fourniture --> " + lot.getFourniture().getDesignation());
        }
        return map;
    }
}
