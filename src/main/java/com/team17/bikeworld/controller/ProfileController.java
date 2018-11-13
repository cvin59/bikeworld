package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.ProfileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
@CrossOrigin
public class ProfileController extends AbstractController{

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(API_PROFILE + "/avatar/{username}")
    public String getAvatar(@PathVariable("username") String username) {
        Response<Object> response = profileService.getAvatar(username);
        return gson.toJson(response);
    }
}
