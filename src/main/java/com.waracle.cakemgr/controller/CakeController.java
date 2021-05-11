package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultPage(ModelMap modelMap) {
        ModelAndView model = new ModelAndView("home");
        List<Cake> cakes = cakeService.getCakes();
        modelMap.addAttribute("cakes", cakes);
        model.addObject("title", "Cake Manager");
        model.addObject("message", "View and add Cakes!");
        model.addAllObjects(modelMap);
        return model;
    }

    @RequestMapping(value = "/cakes", method = RequestMethod.GET)
    public ModelAndView listAndAddCakes(ModelMap modelMap) {
        ModelAndView model = new ModelAndView("cakes");
        String json = cakeService.getCakesAsJson();
        List<Cake> cakes = cakeService.getCakes();
        modelMap.addAttribute("json", json);
        modelMap.addAttribute("cakes", cakes);
        model.addObject("title", "Cake Manager");
        model.addObject("add", "Add Cakes!");
        model.addObject("list", "List Cakes as JSON Data!");
        model.addObject("table", "List Cakes on table!");
        model.addAllObjects(modelMap);
        return model;
    }

    @RequestMapping(value = "/cake/add", method = RequestMethod.POST)
    public String createCake(HttpServletRequest request) {
        Cake cake = new Cake(request.getParameter("title"),
                request.getParameter("description"),
                request.getParameter("image"));
        cakeService.addCake(cake);
        return "redirect:/cakes";
    }

    @RequestMapping(value = "/cake", method = RequestMethod.GET)
    public Cake getCake(@RequestParam(name = "title") String title) {
        return cakeService.getCake(title);
    }
}
