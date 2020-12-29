package pl.mw.san.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mw.san.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    UserRole getUserRoleByRoleName(String roleName);
}
