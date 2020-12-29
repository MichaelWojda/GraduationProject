package pl.mw.san.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.repositories.UserRepository;
import pl.mw.san.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserRestController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ApplicationUser> getAll() {
        return userRepository.findAll();

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> ResponseEntity.ok().body(user)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(value = "/user/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationUser> createUser(@RequestBody ApplicationUser appUser) throws URISyntaxException {
        ApplicationUser savedUser = userService.createUserFunction.apply(appUser);
        return appUser.getId() == null ?
                ResponseEntity.created(new URI("/api/user/" + savedUser.getId())).body(savedUser) :
                ResponseEntity.status(HttpStatus.CONFLICT).build();


    }

    @PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationUser> updateUser(@RequestBody ApplicationUser applicationUser ){
        return ResponseEntity.ok().body(userRepository.save(applicationUser));

    };

    @DeleteMapping(value="/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }





}
