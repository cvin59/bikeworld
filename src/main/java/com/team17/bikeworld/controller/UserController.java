package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Event;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
@CrossOrigin
public class UserController extends AbstractController{
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //Web Service
    @GetMapping(API_USER)
    public String getAccount() {
        Response<List<Account>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            List<Account> accounts = userService.getAllMember();
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, accounts);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(API_USER + "/ban-user/{username}")
    public String banUser(@PathVariable("username") String username) {
        Response<Account> response = userService.banUser(username);
        return gson.toJson(response);
    }

    @GetMapping(API_USER + "/unban-user/{username}")
    public String unbanUser(@PathVariable("username") String username) {
        Response<Account> response = userService.unbanUser(username);
        return gson.toJson(response);
    }
}
