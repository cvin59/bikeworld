package com.team17.bikeworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal")
public class PortalController {

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("portal-index");
    }

    @GetMapping("/event")
    public ModelAndView openEvent(){
        return new ModelAndView("portal-event");
    }
}
