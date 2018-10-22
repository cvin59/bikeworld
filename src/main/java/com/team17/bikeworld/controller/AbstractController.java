package com.team17.bikeworld.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController  {

    protected  final Gson gson;

    public AbstractController() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

//    public String getPrincipal(){
//        String userName;
//        Object principal = SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            userName = ((UserDetails) principal).getUsername();
//        } else {
//            userName = principal.toString();
//        }
//        return userName;
//    }
}
