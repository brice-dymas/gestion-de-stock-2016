/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Categorie;
import com.cami.persistence.model.Entree;
import com.cami.persistence.model.Lot;
import com.cami.persistence.service.ICategorieService;
import com.cami.persistence.service.IEntreeService;
import com.cami.persistence.service.IFournitureService;
import com.cami.persistence.service.ILotService;
import com.cami.persistence.service.IRoleService;
import com.cami.web.form.EntreeForm;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/entree")
public class EntreeController
{

    @Autowired
    ILotService lotService;

    @Autowired
    IFournitureService fournitureService;
    @Autowired
    private IEntreeService entreeService;

    @Autowired
    private ICategorieService categorieService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String showAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Entree entree = entreeService.findOne(id);
        List<Lot> listeLots = lotService.findByEntreeId(id);
        model.addAttribute("lots", listeLots);
        model.addAttribute("entree", entree);
        return "/entree/show";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Entree et = entreeService.findOne(id);
        final EntreeForm entree = new EntreeForm();
        List<Lot> listeLots = lotService.findByEntreeIdForEdit(id);
        entree.setEntree(et);
        entree.setListeLots(listeLots);
//        entree.
        model.addAttribute("entreeForm", entree);
        return "/entree/edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteAction(@PathVariable("id") final Long id, final RedirectAttributes redirectAttributes)
    {
        final Entree entreeToDisable = entreeService.findOne(id);
        entreeService.delete(entreeToDisable);
        return "redirect:/entree/";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final EntreeForm entree = new EntreeForm();
        model.addAttribute("entreeForm", entree);
        return "entree/new";
    }

    @RequestMapping(value = "/{type}/new", method = RequestMethod.GET)
    public String newEntreeAction(@PathVariable("type") final String categorie, final ModelMap model)
    {
        Map<Long, String> fournitures = fournitureService.findByCategorieName(categorie);
        final Categorie categori = categorieService.getCategorie(categorie);
        Entree entree = new Entree();
        entree.setCategorie(categori);
        final EntreeForm entreeForm = new EntreeForm();
        entreeForm.setEntree(entree);
        model.addAttribute("entreeForm", entreeForm);
        model.addAttribute("fournitures", fournitures);
        return "entree/new";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;

        final Page<Entree> resultPage = entreeService.findPaginated(page, size);
//        final Page<Entree> resultPage = iEntreeService.findPaginated(numero, dateEntree, observation, deleted, page, size);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("entrees", resultPage.getContent());
        return "entree/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(final ModelMap model, @Valid final EntreeForm entree,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {
        System.out.println("dans la methode create du controller");
        System.out.println("tout parametre pret");
        if (result.hasErrors())
        {
            System.out.println(" dans le entree controller avec errerur ");
            model.addAttribute("error", "error");
            model.addAttribute("entreeForm", entree);
            return "entree/new";
        }
        else
        {
            System.out.println(" dans le entree controller sans erreur ");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            entreeService.create(entree.getEntree());
            System.out.println("tout est fini");
            return "redirect:/entree/" + entree.getEntree().getId() + "/show";
        }
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @Valid final EntreeForm entree,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
        {
            System.out.println(" dans le entree controller pour update avec errerur ");
            model.addAttribute("error", "error");
            model.addAttribute("entreeForm", entree);
            return "entree/edit";
        }
        else
        {
            System.out.println(" dans le entree controller sans errerur ");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            entreeService.update(entree.getEntree());
            return "redirect:/entree/" + entree.getEntree().getId() + "/show";
        }
    }
    /**
     * @ModelAttribute("fournitures") public Map<Long, String>
     * getfournitures(@PathVariable("type") final String categorie) { if
     * (categorie.length() > 3) { Map<Long, String> listMap = new HashMap<>();
     * final List<Fourniture> fournitures =
     * fournitureService.findByCategorieName(categorie); for (Fourniture
     * fourniture : fournitures) { listMap.put(fourniture.getId(),
     * fourniture.getDesignation()); } return listMap; } return null; }
     *
     */
//    @ModelAttribute("fournitures")
//    public Map<Long, String> getfournitures()
//    {
//        Map<Long, String> listMap = new HashMap<>();
//        final List<Fourniture> fournitures = fournitureService.findAll();
//        for (Fourniture fourniture : fournitures)
//        {
//            listMap.put(fourniture.getId(), fourniture.getDesignation());
//        }
//        return listMap;
//    }

}
