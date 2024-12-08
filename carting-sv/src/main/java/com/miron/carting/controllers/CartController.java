package com.miron.carting.controllers;

import com.miron.carting.controllers.model.ProductRequest;
import com.miron.carting.domain.Cart;
import com.miron.carting.services.ICartService;
import com.miron.carting.services.impl.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/carts")
public class CartController {
    @Autowired
    private ICartService cartService;

    @PostMapping("/buy-all")
    public ResponseEntity<String> buyAll(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        cartService.checkBalance(auth);
        return ResponseEntity.ok().body("Buying all");
    }

    @PostMapping("/buy-single")
    public ResponseEntity<String> buySingle(@RequestBody ProductRequest productRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        cartService.checkBalance(auth, productRequest.id());
        return ResponseEntity.ok().body("Buying one");
    }

    @GetMapping("/get-auth")
    public ResponseEntity<Object> getAuthorizationToken(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal());
    }

    @PostMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello!");
    }
}
