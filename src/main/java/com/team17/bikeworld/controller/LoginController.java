package com.team17.bikeworld.controller;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.model.ConsumeAccount;
import com.team17.bikeworld.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

import static com.team17.bikeworld.common.CoreConstant.*;

@RestController
@CrossOrigin
public class LoginController extends AbstractController{
    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/auth")
    public ResponseEntity authCheck() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasAuthorityAdmin = authorities.contains(new SimpleGrantedAuthority(ADMIN));
        boolean hasAuthorityMember = authorities.contains(new SimpleGrantedAuthority(MEMBER));
        if (hasAuthorityAdmin) {
            return ResponseEntity.status(HttpStatus.OK).body("Logged in!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Admin");
    }

    @GetMapping("/authMember")
    public ResponseEntity authMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasAuthorityAdmin = authorities.contains(new SimpleGrantedAuthority(ADMIN));
        boolean hasAuthorityMember = authorities.contains(new SimpleGrantedAuthority(MEMBER));
        if (hasAuthorityMember) {
            return ResponseEntity.status(HttpStatus.OK).body(authentication.getName());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Admin");
    }

    @PostMapping("/signup")
    public ResponseEntity signupNewUser(@RequestBody ConsumeAccount consumeAccount) {
        if (userService.findUserByUsername(consumeAccount.getUsername()) == null) {
            userService.saveUser(consumeAccount);
            return ResponseEntity.status(HttpStatus.OK).body("Signed in");
        }
        return ResponseEntity.status(HttpStatus.OK).body("This username has been used");
    }

    @GetMapping("/check-username")
    public ResponseEntity checkUsername(@RequestParam String username) {
        LOGGER.info(username);
        if (userService.findUserByUsername(username) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.OK).body(0);
    }
}
