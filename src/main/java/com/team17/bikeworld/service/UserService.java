package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Role;
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

    @Autowired
    public UserService(AccountRepository accountRepository,
                       RoleRepository roleRepository
                       , BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Account findUserByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    public Account saveUser(Account user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setIsActive((short) 1);
        Role userRole = roleRepository.findByName("CUSTOMER");
        user.setRoleId(userRole);
        return accountRepository.save(user);
    }

    public Optional<Account> getUser(String username, String password) {
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }
}
