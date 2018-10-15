package com.team17.bikeworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends AbstractController {

    @GetMapping("/home")
    public ModelAndView viewHome(){
        return new ModelAndView("index");
    }

}
