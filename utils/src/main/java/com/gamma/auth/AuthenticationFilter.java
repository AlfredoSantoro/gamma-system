package com.gamma.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String loginUrl = "/api/v1/login";
    private static final String loginType = "POST";

    private String param;
    private String prefix;
    private String secret;

    protected AuthenticationFilter(String defaultFilterProcessesUrl,
                                   String param,
                                   String prefix,
                                   String secret) {
        super(new AntPathRequestMatcher(loginUrl, loginType));
        this.param = param;
        this.prefix = prefix;
        this.secret = secret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        Login login = objectMapper.readValue(request.getInputStream(), Login.class);
        this.logger.info("### loginDTO forwarded #${loginDTO.username}");
        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword(), List.of()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetail user = (UserDetail) authResult.getPrincipal();
        String accessToken = JwtUtil.createJwt(user.getUsername(), this.secret, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        response.addHeader(this.param, this.prefix + " " + accessToken);
        response.addHeader("Content-Type", "application/json");
        String loginResponse = objectMapper.writeValueAsString(new LoginResponse(user.getUsername(), accessToken, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()));
        this.logger.info("### successfulAuthentication for " + user.getUsername());
        response.getWriter().write(loginResponse);
    }
}
