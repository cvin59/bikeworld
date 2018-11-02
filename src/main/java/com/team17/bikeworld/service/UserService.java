package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Profile;
import com.team17.bikeworld.entity.Role;
import com.team17.bikeworld.model.ConsumeAccount;
import com.team17.bikeworld.repositories.ProfileRepository;
import com.team17.bikeworld.repositories.RoleRepository;
import com.team17.bikeworld.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ProfileRepository profileRepository;

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

    public Optional<Account> getUser(String username, String password) {
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }


}
