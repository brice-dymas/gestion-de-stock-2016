/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Departement;
import com.cami.persistence.model.Fourniture;
import com.cami.persistence.model.LigneOperation;
import com.cami.persistence.model.Operation;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.TypeOperation;
import com.cami.persistence.service.IDepartementService;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILigneOperationService;
import com.cami.persistence.service.ILotService;
import com.cami.persistence.service.IOperationService;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.ITypeOperationService;
import com.cami.web.form.SortieForm;
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
import org.springframework.security.access.annotation.Secured;
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
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Controller
@Secured(
        {
            "ROLE_USER", "ROLE_ADMIN"
        })
@RequestMapping("/sortie")
public class SortieController
{

    @Autowired
    ITypeOperationService itos;

    @Autowired
    IFournitureService fournitureService;
    @Autowired
    ILotService lotService;
    @Autowired
    IDepartementService departementService;
    @Autowired
    IRoleService roleService;
    @Autowired
    ILigneOperationService ligneOperationService;
    @Autowired
    IOperationService sortieService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id,
            final ModelMap model)
    {
        final Operation sortie;
        sortie = sortieService.findOne(id);
        final List<LigneOperation> ligneOperations = ligneOperationService.filterByOperation(sortie.getId());
        Role role = roleService.findOne(sortie.getUser().getId());
        sortie.setLigneOperations(ligneOperations);
        sortie.setUser(role);
        model.addAttribute("sortie", sortie);
        model.addAttribute("user", role);
        model.addAttribute("ligneOperations", ligneOperations);
        return "sortie/show";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id,
            final ModelMap model)
    {
        final Operation sortie = sortieService.findOne(id);
        final List<LigneOperation> ligneOperations = ligneOperationService.filterByOperation(sortie.getId());
        final SortieForm sortieForm = new SortieForm();
        sortieForm.setSortie(sortie);
        sortieForm.setLigneOperations(ligneOperations);
        model.addAttribute("sortieForm", sortieForm);
        return "sortie/edit";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        final long departementID = webRequest.getParameter("querydepartement") != null
                && !webRequest.getParameter("querydepartement").equals("")
                        ? Long.valueOf(webRequest.getParameter("querydepartement"))
                        : -1;
        final Integer page = webRequest.getParameter("page") != null
                ? Integer.valueOf(webRequest.getParameter("page"))
                : 0;
        final Integer size = webRequest.getParameter("size") != null
                ? Integer.valueOf(webRequest.getParameter("size"))
                : 5;
        final String dateOperationString = webRequest.getParameter("querydateoperation") != null
                ? webRequest.getParameter("querydateoperation")
                : "01/01/1960";
        final String designation = webRequest.getParameter("querydesignation") != null
                ? webRequest.getParameter("querydesignation") : "";
        Date dateOperation = new Date();
        try {
            dateOperation = dateFormatter.parse(dateOperationString);
        }
        catch (ParseException ex) {
            try {
                dateOperation = dateFormatter.parse("01/01/1960");
            }
            catch (ParseException ex1) {
                Logger.getLogger(SortieController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        final Page<Operation> resultPage = sortieService.findPaginated(departementID, dateOperation, "sortie", designation, page, size);

        final Operation sortie = new Operation();
        TypeOperation typeOperation = new TypeOperation();
        Departement departement = new Departement();
        typeOperation.setIntitule("Audit");
        departement.setId(departementID);

        sortie.setDateOperation(dateOperation);
        sortie.setDepartement(departement);
        sortie.setTypeOperation(typeOperation);
        model.addAttribute("sortie", sortie);
        model.addAttribute("querydesignation", designation);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sorties", resultPage.getContent());
        return "sortie/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        SortieForm sortieForm = new SortieForm();
        Operation sortie = new Operation();
        TypeOperation typeOperation = itos.findByIntitule("sortie");
        sortie.setTypeOperation(typeOperation);
        sortieForm.setSortie(sortie);
        model.addAttribute("sortieForm", sortieForm);
        return "sortie/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(final ModelMap model, @Valid SortieForm sortieForm,
            final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("sortieForm", sortieForm);
            return "sortie/new";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            sortieService.create(sortieForm.getSortie());
            return "redirect:/sortie/" + sortieForm.getSortie().getId() + "/show";

        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Operation sortie, final ModelMap model)
    {
        Operation sortieToDelete = sortieService.findOne(sortie.getId());
        sortieService.delete(sortieToDelete);
        return "redirect:/sortie/";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @PathVariable("id") final Long id,
            @Valid final SortieForm sortieForm,
            final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            model.addAttribute("sortie", sortieForm);
            model.addAttribute("error", "error");
            return "sortie/edit";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            sortieService.restoreInitialBdState(sortieForm.getSortie());
            sortieService.update(sortieForm.getSortie());
            return "redirect:/sortie/" + sortieForm.getSortie().getId() + "/show";
        }
    }

    @ModelAttribute("departements")
    public Map<Long, String> getDepartements()
    {
        Map<Long, String> map = new HashMap<>();
        List<Departement> departements = departementService.findAll();
        for (Departement departement : departements) {
            map.put(departement.getId(), departement.getIntitule());
        }
        return map;
    }

    @ModelAttribute("lots")
    public Map<Long, String> getLots()
    {
        Map<Long, String> map = new HashMap<>();
        List<Fourniture> fournitures = fournitureService.findExisting();
        for (Fourniture fourniture : fournitures) {
            map.put(fourniture.getId(), fourniture.getDesignation());
        }
        return map;
    }

}
