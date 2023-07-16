package com.gamma.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetailService is called by Spring after attemptAuthentication in AuthenticationFilter
 */
@Component
public class UserDetailService implements UserDetailsService {


    // TODO add repository and check user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
