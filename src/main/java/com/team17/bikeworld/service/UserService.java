package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Profile;
import com.team17.bikeworld.entity.Role;
import com.team17.bikeworld.model.ConsumeAccount;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.ProfileModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.ProfileRepository;
import com.team17.bikeworld.repositories.RoleRepository;
import com.team17.bikeworld.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static com.team17.bikeworld.common.CoreConstant.*;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ProfileRepository profileRepository;

    private final Path rootLocation = Paths.get("src/main/resources/static/images/avatar").toAbsolutePath().normalize();


    @Autowired
    public UserService(AccountRepository accountRepository,
                       RoleRepository roleRepository
            , BCryptPasswordEncoder bCryptPasswordEncoder
            , ProfileRepository profileRepository
    ) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRepository = profileRepository;
    }

    public Account findUserByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    public Account saveUser(ConsumeAccount user) {
        Account account = new Account();
        account.setUsername(user.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        account.setIsActive((short) 1);

        Role userRole = roleRepository.findByName("MEMBER");
        account.setRoleId(userRole);

        Profile profile = new Profile();
        profile.setEmail(user.getEmail());
        profile = profileRepository.save(profile);

        account.setProfileId(profile);
        return accountRepository.save(account);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Optional<Account> getUser(String username, String password) {
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }

    public void handleImage(ProfileModel model, MultipartFile image) throws IOException {
        if (image != null) {
            String fileName = image.getOriginalFilename();
            Files.createDirectories(rootLocation);
            Files.copy(image.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            model.setAvatar("/images/avatar/" + fileName);
            LOGGER.info("file name:" + fileName);
        }
    }

    public List<Account> getAllMember(){
        return accountRepository.findByRoleId_Id(MEMBER_ID);
    }

    public Response<Account> banUser(String username) {
        Response<Account> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            Account account = accountRepository.findAccountByUsername(username);
            if (account != null) {
                account.setIsActive((short) 0);

                account = accountRepository.save(account);
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, account);
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return response;
    }

    public Response<Account> unbanUser(String username) {
        Response<Account> response = new Response<>(STATUS_CODE_FAIL, MESSAGE_FAIL);
        try {
            Account account = accountRepository.findAccountByUsername(username);
            if (account != null) {
                account.setIsActive((short) 1);

                account = accountRepository.save(account);
                response.setResponse(STATUS_CODE_SUCCESS, MESSAGE_SUCCESS, account);
            } else {
                response.setResponse(STATUS_CODE_NO_RESULT, MESSAGE_NO_RESULT);
            }
        } catch (Exception e) {
            response.setResponse(STATUS_CODE_SERVER_ERROR, MESSAGE_SERVER_ERROR);
        }
        return response;
    }
}
