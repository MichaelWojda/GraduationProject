package pl.mw.san.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.model.UserRole;
import pl.mw.san.repositories.UserRepository;
import pl.mw.san.repositories.UserRoleRepository;
import pl.mw.san.service.UserService;

@Component
public class Loader implements CommandLineRunner {

   private UserRoleRepository userRoleRepository;

   private UserService userService;

   @Autowired
    public Loader(UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleRepository.save(new UserRole("BASIC","Basic Role"));
        userService.createUserFunction.apply(new ApplicationUser("FirstUser","pass"));

    }
}
