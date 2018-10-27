package com.team17.bikeworld.controller;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.service.UserService;
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
    public ResponseEntity signupNewUser(@RequestBody Account account) {
        if (userService.findUserByUsername(account.getUsername()) == null) {
            userService.saveUser(account);
            return ResponseEntity.status(HttpStatus.OK).body("Signed in");
        }
        return ResponseEntity.status(HttpStatus.OK).body("This username has been used");
    }
}
