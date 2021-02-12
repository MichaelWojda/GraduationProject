package pl.mw.san.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.model.Item;
import pl.mw.san.repositories.UserRepository;
import pl.mw.san.security.LoggedUser;
import pl.mw.san.security.UserPrincipal;
import pl.mw.san.service.BasketService;

@RestController
@RequestMapping("/api/basket")
public class BasketRestController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    BasketService basketService;

    @GetMapping
    ResponseEntity<?> getUsersBasket(@LoggedUser UserPrincipal userPrincipal){
        ApplicationUser applicationUser = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new UsernameNotFoundException(" USER NOT FOUND"));
        return ResponseEntity.ok().body(applicationUser.getBasket());
    }

    @PostMapping
    ResponseEntity<?> addItemToBasket(@LoggedUser UserPrincipal userPrincipal,@RequestBody Item item){
        ApplicationUser applicationUser = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new UsernameNotFoundException(" USER NOT FOUND"));
        return ResponseEntity.ok().body(basketService.addItemToBasket(applicationUser,item));
    }

    @PutMapping
    ResponseEntity<?> removeItemFromBasket (@LoggedUser UserPrincipal userPrincipal , @RequestBody Item item){
        ApplicationUser applicationUser = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new UsernameNotFoundException(" USER NOT FOUND"));
        return ResponseEntity.ok().body(basketService.removeItemFromBasket(applicationUser,item));
    }



}
