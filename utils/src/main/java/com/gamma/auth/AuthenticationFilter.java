package com.gamma.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String loginType = "POST";
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private String param;
    private String prefix;
    private String secret;
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                   String defaultFilterProcessesUrl,
                                   String param,
                                   String prefix,
                                   String secret) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, loginType));
        this.authenticationManager = authenticationManager;
        this.param = param;
        this.prefix = prefix;
        this.secret = secret;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetail user = (UserDetail) authResult.getPrincipal();
        String accessToken = JwtUtil.createJwt(user.getUsername(), this.secret, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        response.addHeader(this.param, this.prefix + " " + accessToken);
        response.addHeader("Content-Type", "application/json");
        String loginResponse = objectMapper.writeValueAsString(new LoginResponse(user.getUsername(), accessToken, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()));
        this.logger.info("### successfulAuthentication for {}", user.getUsername());
        response.getWriter().write(loginResponse);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Login login = objectMapper.readValue(httpServletRequest.getInputStream(), Login.class);
        this.logger.info("### login attempt for {}", login.getUsername());
        return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword(), List.of()));
    }
}
