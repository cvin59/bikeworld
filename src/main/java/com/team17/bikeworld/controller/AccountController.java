package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Profile;
import com.team17.bikeworld.model.ProfileModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.AccountRepository;
import com.team17.bikeworld.service.UserService;
import com.team17.bikeworld.transformer.ProfileTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccountController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserService userService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProfileTransformer profileTransformer;

    @PutMapping(CoreConstant.API_ACCOUNT + "/profile/edit")
    public String saveProfile(@RequestParam String profileModelString, MultipartFile avatar) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            ProfileModel profileModel = gson.fromJson(profileModelString, ProfileModel.class);
            Profile profile = userService.findUserByUsername(profileModel.getAccountUser()).getProfileId();
            if (profile==null){
                profile = new Profile();
            }

            userService.handleImage(profileModel, avatar);
            LOGGER.info(profileModel.getAvatar());
            profile = profileTransformer.ProfileModelToEntity(profileModel, profile);
            profile = userService.saveProfile(profile);

            Account account = userService.findUserByUsername(profileModel.getAccountUser());
            account.setProfileId(profile);
            accountRepository.save(account);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_ACCOUNT + "/profile/{accountUsername}")
    public String getProfile(@PathVariable String accountUsername) {
        Response<Profile> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Account account = userService.findUserByUsername(accountUsername);
            Profile profile = new Profile();
            if (account != null) {
                profile = account.getProfileId();
            }
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, profile);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
