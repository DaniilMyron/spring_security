package com.miron.user.controllers;

import com.miron.user.controllers.api.RegistrationRequest;
import com.miron.user.controllers.api.ReplenishBalance;
import com.miron.user.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/auth")
    public ResponseEntity<String> authorization(){
        return ResponseEntity.ok("Authorization confirmed");
    }

    @GetMapping("/get-auth")
    public ResponseEntity<Object> getAuthorizationToken(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest request){
        userService.registerUser(request);
        return ResponseEntity.ok("Registration confirmed");
    }

    @PostMapping("/replenish")
    public ResponseEntity<String> replenishBalance(@RequestBody ReplenishBalance request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.getAuthenticatedUser(auth.getPrincipal(), request.sum());
        return ResponseEntity.ok("Your balance now is: " + user.getBalance());
    }
}
