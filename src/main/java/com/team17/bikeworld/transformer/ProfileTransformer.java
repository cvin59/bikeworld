package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.Profile;
import com.team17.bikeworld.model.ProfileModel;
import org.springframework.stereotype.Service;

@Service
public class ProfileTransformer {
    public Profile ProfileModelToEntity(ProfileModel model, Profile entity) {

        entity.setFirstName(model.getFirstname());
        entity.setLastName(model.getLastname());
        entity.setPhone(model.getPhone());
        entity.setEmail(model.getEmail());
        entity.setAddress(model.getAddress());
        entity.setAvatarLink(model.getAvatar());

        return entity;
    }
}


