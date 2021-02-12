package pl.mw.san.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.model.UserBasicData;
import pl.mw.san.model.UserRole;
import pl.mw.san.repositories.UserRepository;
import pl.mw.san.repositories.UserRoleRepository;
import pl.mw.san.security.JWTTokenService;
import pl.mw.san.security.LoggedUser;
import pl.mw.san.security.UserPrincipal;
import pl.mw.san.security.payload.ApiResponse;
import pl.mw.san.security.payload.JWTTokenResponse;
import pl.mw.san.security.payload.LoginRequest;
import pl.mw.san.security.payload.SignUpRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTTokenService jwtTokenService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenService.generateJWTToken(authentication);

        return ResponseEntity.ok(new JWTTokenResponse(token));

    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return new ResponseEntity(new ApiResponse("ERROR Username already taken!!!", false), HttpStatus.BAD_REQUEST);
        }
        ApplicationUser user = new ApplicationUser(signUpRequest.getUserName(),signUpRequest.getPassword());
        UserRole userRole = userRoleRepository.getUserRoleByRoleName("BASIC");

        user.setUserRoles(Collections.singleton(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ApplicationUser resultUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/{id}")
                .buildAndExpand(resultUser.getId()).toUri();

        return  ResponseEntity.created(location).body(new ApiResponse("User Registered Successfully",true));

    }

    @GetMapping("/user/current")
    public UserBasicData getCurrentUserBasicData(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new UserBasicData(userPrincipal.getId(),userPrincipal.getUsername(),userPrincipal.getAuthorities().toString());
    }




}
