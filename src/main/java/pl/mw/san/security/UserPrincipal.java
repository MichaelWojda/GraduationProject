package pl.mw.san.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mw.san.model.ApplicationUser;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private Long id;

    private String userName;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }


    public static UserPrincipal createPrincipal(ApplicationUser user) {
        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                authorities


        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrincipal that = (UserPrincipal) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(userName, that.userName)) return false;
        if (!Objects.equals(password, that.password)) return false;
        return Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
