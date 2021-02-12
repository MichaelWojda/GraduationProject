package pl.mw.san.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.getApplicationUserByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("User of username: " + userName +" not found"));

        return UserPrincipal.createPrincipal(user);

    }


    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("User of id: " + id +" not found"));

        return UserPrincipal.createPrincipal(user);

    }


}
