package pl.mw.san.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.model.Basket;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Long> {

    Optional<ApplicationUser> getApplicationUserByUserName(String userName);

    Boolean existsByUserName(String userName);


}
