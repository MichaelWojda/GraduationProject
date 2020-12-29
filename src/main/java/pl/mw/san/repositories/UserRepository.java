package pl.mw.san.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mw.san.model.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser,Long> {

    ApplicationUser getApplicationUserByUserName(String userName);


}
