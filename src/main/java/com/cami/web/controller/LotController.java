/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.Lot;
import com.cami.persistence.service.IEntreeService;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILotService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/lot")
public class LotController
{

    @Autowired
    private ILotService iLotService;

    @Autowired
    private IFournitureService iFournitureService;

    @Autowired
    private IEntreeService iEntreeService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String showAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Lot lot = iLotService.findOne(id);
        model.addAttribute("lot", lot);
        return "/lot/show";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Lot lot = iLotService.findOne(id);
        model.addAttribute("lot", lot);
        return "/lot/edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteAction(@PathVariable("id") final Long id, final RedirectAttributes redirectAttributes)
    {
        final Lot lotToDisable = iLotService.findOne(id);
        iLotService.delete(lotToDisable);
        return "redirect:/lot/";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Lot lot = new Lot();
        model.addAttribute("lot", lot);
        return "/lot/new";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        final String numero = webRequest.getParameter("numero") != null ? webRequest.getParameter("numero") : "";
        final String etat = webRequest.getParameter("etat") != null ? webRequest.getParameter("etat") : "";
        final String dateEntree = webRequest.getParameter("dateEntree") != null ? webRequest.getParameter("dateEntree") : "";

        final int prixUnitaire = webRequest.getParameter("prixUnitaire") != null ? Integer.valueOf(webRequest.getParameter("prixUnitaire")) : 0;
        final int prixVenteUnitaire = webRequest.getParameter("prixVenteUnitaire") != null ? Integer.valueOf(webRequest.getParameter("prixVenteUnitaire")) : 0;
        final int totalMontant = webRequest.getParameter("totalMontant") != null ? Integer.valueOf(webRequest.getParameter("totalMontant")) : 0;

        final int quantite = webRequest.getParameter("quantite") != null ? Integer.valueOf(webRequest.getParameter("quantite")) : 0;
        final int page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;

        Date date = new Date();
        try
        {
            date = dateFormatter.parse(dateEntree);
        }
        catch (ParseException pe)
        {
            try
            {
                date = dateFormatter.parse("31/12/1975");
            }
            catch (Exception e)
            {
                Logger.getLogger(EntreeController.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        final Page<Lot> resultPage = iLotService.searchLots('%' + numero + '%', date, prixUnitaire, prixVenteUnitaire, quantite, totalMontant, '%' + etat + '%', page, size);

        final Lot lot = new Lot();
        lot.setNumero(numero);
        lot.setPrixUnitaire(prixUnitaire);
        lot.setDateEntree(date);
        lot.setEtat(etat);
        lot.setQuantite(quantite);
        lot.setTotalMontant(totalMontant);
        lot.setPrixVenteUnitaire(prixVenteUnitaire);
        model.addAttribute("lot", lot);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("lots", resultPage.getContent());
        return "lot/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Lot lot, final ModelMap model,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("error", "error");
            model.addAttribute("lot", lot);
            return "lot/new";
        }
        else
        {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iLotService.create(lot);
            return "redirect:/lot/" + lot.getId() + "/show";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(@Valid final Lot lot, final ModelMap model,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("error", "error");
            model.addAttribute("lot", lot);
            return "lot/edit";
        }
        else
        {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iLotService.update(lot);
            return "redirect:/lot/" + lot.getId() + "/show";
        }
    }

    @ModelAttribute("listFourniture")
    public Map<Long, String> populateFournitureFile()
    {

        final Map<Long, String> map = new HashMap<>();

        final List<Fourniture> fournitures = iFournitureService.findAll();

        for (Fourniture fourniture : fournitures)
        {
            map.put(fourniture.getId(), fourniture.getDesignation());
        }

        return map;
    }

//    @ModelAttribute("listEntree")
//    public Map<Long, Long> populateEntreeFile()
//    {
//
//        final Map<Long, Long> map = new HashMap<>();
//
//        final List<Entree> entrees = iEntreeService.findAll();
//
//        for (Entree entree : entrees)
//        {
//            map.put(entree.getId(), entree.getNumero());
//        }
//
//        return map;
//    }
}
