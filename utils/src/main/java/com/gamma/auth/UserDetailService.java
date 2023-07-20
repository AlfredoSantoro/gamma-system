package com.gamma.auth;

import com.gamma.model.User;
import com.gamma.repository.UserMatrixRepository;
import com.gamma.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * UserDetailService is called by Spring after attemptAuthentication in AuthenticationFilter
 */
@Component
public class UserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserDetailService.class);
    @Autowired
    private UserMatrixRepository userMatrixRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Loading user's username and the services user can use
     * @param username
     * @return Spring UserDetails to manage authentication with spring security
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.logger.info("### checking user {}", username);
        Optional<User> optionalUser = this.userRepository.findById(username);
        if (optionalUser.isPresent()) {
            var grantedAuthorityList = this.userMatrixRepository.findByUserType(optionalUser.get().getUserType())
                    .stream()
                    .map((record) -> (GrantedAuthority) record::getService)
                    .toList();
            return new UserDetail(optionalUser.get(), grantedAuthorityList);
        } else {
            this.logger.info("### user not found");
            throw new UsernameNotFoundException(username + " not found");
        }
    }
}
