package pl.mw.san.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.repositories.UserRepository;
import pl.mw.san.repositories.UserRoleRepository;

import java.util.function.Function;

@Service
public class UserService {

    private static final String BASICROLE = "BASIC";
    private static final String ADMINROLE = "ADMIN";


    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public ApplicationUser createUser(ApplicationUser applicationUser) {
        applicationUser.getUserRoles().add(userRoleRepository.getUserRoleByRoleName(BASICROLE));
        return userRepository.save(applicationUser);
    }

    public Function<ApplicationUser,ApplicationUser> createUserFunction = applicationUser -> {
        applicationUser.getUserRoles().add(userRoleRepository.getUserRoleByRoleName(BASICROLE));
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        return userRepository.save(applicationUser);
    };

}
