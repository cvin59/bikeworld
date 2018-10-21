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

    @GetMapping("/auth")
    public ResponseEntity authCheck() {return ResponseEntity.status(HttpStatus.OK).body("Logged in!");
    }

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

//    @PostMapping(value = "/login")
//    public String login(@RequestBody  Account user, HttpServletRequest request) {
//        System.out.println(user.toString());
//        Optional<Account> optional = userService.getUser(user.getUsername(), user.getPassword());
//        if (optional.isPresent()) {
//            Account currUser = optional.get();
//
//            HttpSession session = request.getSession();
//
//            session.setAttribute("USER_NAME", currUser.getUsername());
//            session.setAttribute("USER_ROLE", currUser.getRoleId().getName());
//            return gson.toJson( new Response<>(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS));
//        }
//
//        return gson.toJson(new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL));
//    }

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
