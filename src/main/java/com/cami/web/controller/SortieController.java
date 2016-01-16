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
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Controller
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
        final Integer page = webRequest.getParameter("page") != null
                ? Integer.valueOf(webRequest.getParameter("page"))
                : 0;
        final Integer size = webRequest.getParameter("size") != null
                ? Integer.valueOf(webRequest.getParameter("size"))
                : 5;

        final Page<Operation> resultPage = sortieService.findPaginated(page, size);
        final Operation sortie = new Operation();
        model.addAttribute("sortie", sortie);
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

//        boolean depasser = false;
//        System.out.println("dans le controller");
//        List<LigneOperation>  ligneOperations = sortieForm.getSortie().getLigneOperations();
//        for (LigneOperation ligneOperation : ligneOperations)
//        {
//            if (ligneOperation.getQuantite() > ligneOperation.getLot().getFourniture().getQuantite())
//            {
//                depasser=true;
//            }
//        }
//        if (result.hasErrors() || depasser == true) {
        if (result.hasErrors())
        {
            System.out.println("nul ou erreur" + result.getFieldError());
            model.addAttribute("error", "error");
            model.addAttribute("sortieForm", sortieForm);
            return "sortie/new";
        }
        else
        {
            System.out.println("non nul");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            sortieService.create(sortieForm.getSortie());
            System.out.println("object created");
//            return "redirect:/sortie/";
            return "redirect:/sortie/" + sortieForm.getSortie().getId() + "/show";

        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Operation sortie, final ModelMap model)
    {
        Operation sortieToDelete = sortieService.findOne(sortie.getId());
        System.out.println("deleteAction of a sortie =" + sortieToDelete.getId());

        sortieService.delete(sortieToDelete);
        return "redirect:/sortie/";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @PathVariable("id") final Long id,
            @Valid final SortieForm sortieForm,
            final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("enter");
        if (result.hasErrors())
        {
            System.out.println("il ya eu erreur de modification");
            model.addAttribute("sortie", sortieForm);
            model.addAttribute("error", "error");
            return "sortie/edit";
        }
        else
        {
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
        for (Departement departement : departements)
        {
            map.put(departement.getId(), departement.getIntitule());
        }
        return map;
    }

    @ModelAttribute("lots")
    public Map<Long, String> getLots()
    {
        Map<Long, String> map = new HashMap<>();
        List<Fourniture> fournitures = fournitureService.findExisting();
        for (Fourniture fourniture : fournitures)
        {
            map.put(fourniture.getId(), fourniture.getDesignation());
        }
        return map;
    }

}
