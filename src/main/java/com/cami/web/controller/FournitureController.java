/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Lot;
import com.cami.persistence.service.ICategorieService;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILigneOperationService;
import com.cami.persistence.service.ILotService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@RequestMapping("/fourniture")
public class FournitureController
{

    @Autowired
    ILigneOperationService ligneOperationService;

    private static Logger logger = Logger.getLogger(FournitureController.class.getName());
    @Autowired
    ILotService lotService;

    @Autowired
    private IFournitureService iFournitureService;

    @Autowired
    private ICategorieService iCategorieService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String showAction(@PathVariable("id") final Long id, final ModelMap model, WebRequest webRequest)
    {
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;
        final Fourniture fourniture = iFournitureService.findOne(id);
        Page<Lot> resultPage = lotService.findByFourniture(id, page, size);
        Page<LigneOperation> ligneOperations = ligneOperationService.findByFourniture(id, page, size);
        model.addAttribute("lots", resultPage.getContent());
        model.addAttribute("ligneOperations", ligneOperations.getContent());
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/show";
    }

    @RequestMapping(value = "/{id}/search", method = RequestMethod.GET)
    public String searchAction(@PathVariable("id") final Long id, final ModelMap model, WebRequest webRequest)
    {
        System.out.println("inside controller" + webRequest.getParameter("debut"));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;
        final Integer quantite = webRequest.getParameter("quantite") != null && !webRequest.getParameter("quantite").equals("") ? Integer.valueOf(webRequest.getParameter("quantite")) : null;
        final String debut = webRequest.getParameter("debut") != null && !webRequest.getParameter("debut").equals("") ? webRequest.getParameter("debut") : "1970/12/31";
        final String fin = webRequest.getParameter("fin") != null && !webRequest.getParameter("fin").equals("") ? webRequest.getParameter("fin") : "2999/12/31";
        System.out.println("debut =" + debut);
        Date dateDebut = parsedDateFrom(debut, "1970/12/31", dateFormatter);
        Date dateFin = parsedDateFrom(fin, "2999/12/31", dateFormatter);
        final Fourniture fourniture = iFournitureService.findOne(id);
        System.out.println("Debut = " + dateDebut.toString() + " Fin = " + dateFin.toString());
        Page<Lot> resultPage = lotService.search(id, dateDebut, dateFin, quantite, page, size);
        List<Lot> lots = new ArrayList<>();
        for (Lot lot : resultPage.getContent())
        {
            lot.setEntree(null);
            lot.setFourniture(null);
            lots.add(lot);
        }
        model.addAttribute("lots", lots);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/show";
    }

    @RequestMapping(value = "/{id}/searchAudits", method = RequestMethod.GET)
    public String searchAuditsAction(@PathVariable("id") final Long id, final ModelMap model, WebRequest webRequest)
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;
        final Integer quantite = webRequest.getParameter("quantite") != null && !webRequest.getParameter("quantite").equals("") ? Integer.valueOf(webRequest.getParameter("quantite")) : null;
        final String debut = webRequest.getParameter("debut") != null && !webRequest.getParameter("debut").equals("") ? webRequest.getParameter("debut") : "1970/12/31";
        final String fin = webRequest.getParameter("fin") != null && !webRequest.getParameter("fin").equals("") ? webRequest.getParameter("fin") : "2999/12/31";
        System.out.println("debut =" + debut);
        Date dateDebut = parsedDateFrom(debut, "1970/12/31", dateFormatter);
        Date dateFin = parsedDateFrom(fin, "2999/12/31", dateFormatter);
        final Fourniture fourniture = iFournitureService.findOne(id);
        System.out.println("Debut = " + dateDebut.toString() + " Fin = " + dateFin.toString());
        Page<Lot> resultPage = lotService.search(id, dateDebut, dateFin, quantite, page, size);
        List<Lot> lots = new ArrayList<>();
        for (Lot lot : resultPage.getContent())
        {
            lot.setEntree(null);
            lot.setFourniture(null);
            lots.add(lot);
        }
        model.addAttribute("lots", lots);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
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

        final Long categorieID = (webRequest.getParameter("querycategorie") != null && !webRequest.getParameter("querycategorie").equals(""))
                ? Long.valueOf(webRequest.getParameter("querycategorie"))
                : -1;
        final String designation = webRequest.getParameter("querydesignation") != null ? webRequest.getParameter("querydesignation") : "";
        final String reference = webRequest.getParameter("queryreference") != null ? webRequest.getParameter("queryreference") : "";
        final Integer nombrePage = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 5;

        final Page<Fourniture> resultPage = iFournitureService.findPaginated(categorieID, designation, reference, nombrePage, size);

        final Fourniture fourniture = new Fourniture();
        fourniture.setReference(reference);
        fourniture.setDesignation(designation);
        Categorie ct = new Categorie();
        ct.setId(categorieID);
        fourniture.setCategorie(ct);
        model.addAttribute("fourniture", fourniture);
        model.addAttribute("page", nombrePage);
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

    /**
     * Pour Convertir une chaine de charactères en java.util.Date suivant un
     * format donné
     *
     * @exception ParseException
     * @param dateFormat : le format de sortie de la date
     * @param dateString : la chaine de charactères à convertir en date
     * @return result: la java.util.Date obtenue après conversion
     */
    private Date parsedDateFrom(String dateString, String dateLimite, SimpleDateFormat dateFormat)
    {
        Date result = new Date();
        SimpleDateFormat dateFormatter = dateFormat;
        try
        {
            result = dateFormatter.parse(dateString);
        }
        catch (ParseException ex)
        {
            try
            {
                result = dateFormatter.parse(dateLimite);
            }
            catch (ParseException ex1)
            {
                Logger.getLogger(FournitureController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

}
