package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
public class LoginController extends AbstractController{

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/signup")
    public ResponseEntity signupNewUser(@RequestBody Account account) {
        if (userService.findUserByUsername(account.getUsername()) == null) {
            userService.saveUser(account);
            return ResponseEntity.status(HttpStatus.OK).body("Signed in");
        }
        return ResponseEntity.status(HttpStatus.OK).body("This username has been used");
    }

    @RequestMapping(value="/portal", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("userName", "Welcome " + user.getUsername());
        modelAndView.setViewName("portal-index");
        return modelAndView;
    }
}
