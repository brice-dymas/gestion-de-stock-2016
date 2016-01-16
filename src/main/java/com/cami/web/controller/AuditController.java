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
import com.cami.persistence.service.IOperationService;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.ITypeOperationService;
import com.cami.web.form.AuditForm;
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
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    ILigneOperationService ligneOperationService;

    @Autowired
    IDepartementService departementService;

    @Autowired
    IOperationService auditService;

    @Autowired
    IFournitureService fournitureService;

    @Autowired
    IRoleService roleService;

    @Autowired
    ITypeOperationService typeOperationService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model) {
        final Operation audit;
        audit = auditService.findOne(id);
        final List<LigneOperation> ligneOperations = ligneOperationService.filterByOperation(id);
        Role role = roleService.findOne(audit.getUser().getId());
        audit.setLigneOperations(ligneOperations);
        audit.setUser(role);
        model.addAttribute("audit", audit);
        model.addAttribute("user", role);
        model.addAttribute("ligneOperations", ligneOperations);
        return "audit/show";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model) {
        final Operation audit = auditService.findOne(id);
        final List<LigneOperation> ligneOperations = ligneOperationService.filterByOperation(id);
        final AuditForm auditForm = new AuditForm();
        auditForm.setAudit(audit);
        auditForm.setLigneOperations(ligneOperations);
        model.addAttribute("auditForm", auditForm);
        return "audit/edit";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest) {
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 5;
        final Page<Operation> resultPage = auditService.findPaginated("audit", page, size);
        final Operation audit = new Operation();
        model.addAttribute("audit", audit);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("audits", resultPage.getContent());
        return "audit/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model) {
        final AuditForm auditForm = new AuditForm();
        final Operation audit = new Operation();
        TypeOperation typeOperation = typeOperationService.findByIntitule("audit");
        audit.setTypeOperation(typeOperation);
        auditForm.setAudit(audit);
        model.addAttribute("auditForm", auditForm);
        return "audit/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(final ModelMap model, @Valid AuditForm auditForm,
                               final BindingResult result,
                               final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            System.out.println("Dans Audit controller erreur" + result.getFieldError());
            model.addAttribute("error", "error");
            model.addAttribute("auditForm", auditForm);
            return "audit/new";
        }
        else {
            System.out.println("Dans Audit controller sans erreur");
            System.out.println("Debut create");
            auditService.createAudit(auditForm.getAudit());
            System.out.println("Fin create");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            return "redirect:/audit/" + auditForm.getAudit().getId() + "/show";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteAction(final Operation audit) {
        Operation auditToDelete = auditService.findOne(audit.getId());
        System.out.println("deleteAction of a audit =" + auditToDelete.getId());
        auditService.delete(auditToDelete);
        return "redirect:/audit/";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @Valid AuditForm auditForm,
                               final BindingResult result,
                               final RedirectAttributes redirectAttributes) {
        System.out.println("Dans Audit Controller debut ok...");
        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("auditForm", auditForm);
            return "audit/edit";
        }
        else {
            auditService.updateAudit(auditForm.getAudit());
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            return "redirect:/audit/" + auditForm.getAudit().getId() + "/show";
        }
    }

    @ModelAttribute("departements")
    public Map<Long, String> getDepartements() {
        Map<Long, String> map = new HashMap<>();
        List<Departement> departements = departementService.findAll();
        for (Departement departement : departements) {
            map.put(departement.getId(), departement.getIntitule());
        }
        return map;
    }

    @ModelAttribute("fournitures")
    public Map<Long, String> getFournitures() {
        Map<Long, String> map = new HashMap<>();
        List<Fourniture> fournitures = fournitureService.findExisting();
        for (Fourniture fourniture : fournitures) {
            map.put(fourniture.getId(), fourniture.getDesignation());
        }
        return map;
    }
}
