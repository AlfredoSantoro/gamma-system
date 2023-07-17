package com.gamma.auth;

import com.gamma.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {

    private User user;

    private List<GrantedAuthority> authorities;

    public UserDetail(User user) {
        this.user = user;
    }

    public UserDetail(User user, List<GrantedAuthority> grantedAuthorities) {
        this.user = user;
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities != null ? this.authorities : List.of();
    }

    @Override
    public String getPassword() { return this.user.getPassword();}

    @Override
    public String getUsername() { return this.user.getUsername(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
