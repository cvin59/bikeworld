package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Role;
import com.team17.bikeworld.repositories.RoleRepository;
import com.team17.bikeworld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository
//                       ,BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Account findUserByUsername(String username) {
        return userRepository.findAccountByUsername(username);
    }

    public Account saveUser(Account user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.setIsActive((short) 1);
        Role userRole = roleRepository.findByName("ADMIN");
        user.setRoleId(userRole);
        return userRepository.save(user);
    }




    public Optional<Account> getUser(String username, String password) {
        return userRepository.findAccountByUsernameAndPassword(username, password);
    }



}
