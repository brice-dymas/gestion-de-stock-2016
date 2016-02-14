/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Lot;
import com.cami.persistence.model.Perte;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILigneOperationService;
import com.cami.persistence.service.ILotService;
import com.cami.persistence.service.IOperationService;
import com.cami.persistence.service.IPerteService;
import com.cami.web.form.PerteForm;
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
        final LigneOperation ligneOperation = ligneOperationService.findOne(perte.getLigneOperation().getId());
        final PerteForm perteForm = new PerteForm();
        Map<Long, String> lots = lotService.getFournituresForPerte(perte.getLigneOperation().getFourniture().getId());
        model.addAttribute("ligneOperation", ligneOperation);
        model.addAttribute("perte", perte);
        model.addAttribute("perteForm", perteForm);
        model.addAttribute("lots", lots);
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
        Map<Long, String> lots = lotService.getFournituresForPerte(ligneOperation.getFourniture().getId());
        PerteForm perteForm = new PerteForm();
        model.addAttribute("perteForm", perteForm);
        model.addAttribute("ligneOperation", ligneOperation);
        model.addAttribute("lots", lots);
        return "perte/new";
    }

    @RequestMapping(value = "/{id}/create", method = RequestMethod.POST)
    public String createAction(@PathVariable("id") final long id, @Valid PerteForm perteForm,
            final ModelMap model, final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        final LigneOperation ligneOperation = ligneOperationService.findOne(id);
        List<Perte> pertes = perteForm.getListPertes();
        int totalPerte = 0;
        for (Perte perte : pertes)
        {
            perte.setLigneOperation(ligneOperation);
            totalPerte += perte.getQuantite();
        }

        if (result.hasErrors() | totalPerte > ligneOperation.getQuantiteEcart())
        {
            System.out.println("Dans Perte controller erreur" + result.getFieldError());
            Map<Long, String> lots = lotService.getFournituresForPerte(ligneOperation.getFourniture().getId());
            model.addAttribute("perte", perteForm);
            model.addAttribute("ligneOperation", ligneOperation);
            model.addAttribute("lots", lots);
            model.addAttribute("error", "error");
        }
        else
        {
            System.out.println("Dans Perte Controller sans erreur debut...");
//            perteService.create(equilibre);
            perteService.create(pertes);
            System.out.println("Dans perte controller fin...");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
        }
        return "redirect:/perte/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Perte perte, final ModelMap model)
    {
        Perte perteToDelete = perteService.findOne(perte.getId());
        System.out.println("deleteAction of a perte =" + perteToDelete.getId());

        perteService.delete(perteToDelete);
        return "redirect:/perte/";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(@PathVariable("id") final long id, final ModelMap model, @Valid PerteForm perteForm,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {

        final Perte perte = perteService.findOne(id);
        final LigneOperation ligneOperation = ligneOperationService.findOne(perte.getLigneOperation().getId());
        System.out.println("Dans perte controller ligneOperation fourniture = " + ligneOperation.getFourniture().getDesignation());
        List<Perte> pertes = perteForm.getListPertes();

        if (result.hasErrors())
        {
            Map<Long, String> lots = lotService.getFournituresForPerte(ligneOperation.getFourniture().getId());
            model.addAttribute("error", "error");
            model.addAttribute("perteForm", perteForm);
            model.addAttribute("perte", perte);
            model.addAttribute("ligneOperation", ligneOperation);
            model.addAttribute("lots", lots);
            return "perte/edit";
        }
        else
        {
            perteService.update(id, pertes);
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            return "redirect:/perte/";
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
