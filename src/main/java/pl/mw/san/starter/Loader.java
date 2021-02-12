package pl.mw.san.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.model.Item;
import pl.mw.san.model.UserRole;
import pl.mw.san.repositories.ItemRepository;
import pl.mw.san.repositories.UserRepository;
import pl.mw.san.repositories.UserRoleRepository;
import pl.mw.san.service.UserService;

@Component
public class Loader implements CommandLineRunner {

   private UserRoleRepository userRoleRepository;

   private UserService userService;

   private ItemRepository itemRepository;

   @Autowired
    public Loader(UserRoleRepository userRoleRepository, UserService userService,ItemRepository itemRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.itemRepository=itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        userRoleRepository.save(new UserRole("BASIC","Basic Role"));
//        userRoleRepository.save(new UserRole("ADMIN","Admin Role"));
//        itemRepository.transactionalCreateItem(new Item(99L,"Test","TestDescription","url",10,true));
//        itemRepository.transactionalCreateItem(new Item(100L,"Test2","TestDescription2","url2",100,true));

    }
}
