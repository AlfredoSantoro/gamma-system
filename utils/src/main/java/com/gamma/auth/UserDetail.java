package com.gamma.auth;

import com.gamma.model.User;
import com.gamma.repository.UserMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDetail implements UserDetails {

    private User user;

    @Autowired
    private UserMatrixRepository userMatrixRepository;

    public UserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userMatrixRepository.findByUserType(this.user.getUserType().name())
                .stream()
                .map((record) -> (GrantedAuthority) record::getService)
                .toList();
    }

    @Override
    public String getPassword() { return this.user.getPassword();}

    @Override
    public String getUsername() { return this.getUsername(); }

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
