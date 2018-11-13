package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import static com.team17.bikeworld.common.CoreConstant.*;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Response<Object> getAvatar(String username) {
        Response<Object> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Object object = profileRepository.findAvatarByUsername(username);

            if (object != null) {
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, object);
            } else {
                response.setResponse(CoreConstant.STATUS_CODE_NO_RESULT, CoreConstant.MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }

        return response;
    }
}
