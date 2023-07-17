package com.gamma.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class handles the token validation of an authenticated user
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private String param;
    private String prefix;
    private String secret;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               String param,
                               String prefix,
                               String secret) {
        super(authenticationManager);
        this.param = param;
        this.prefix = prefix;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String headerAuth = request.getHeader(this.param);
        if (headerAuth != null && headerAuth.startsWith(this.prefix))
        {
            this.logger.info("### access_token in header request");
            String token = headerAuth.replace(this.prefix + " ", "");
            DecodedJWT decoded = JwtUtil.verifyJwt(token, this.secret);
            List<String> authority = decoded.getClaim("authority").asList(String.class);
            List<GrantedAuthority> authorities = authority.stream().map((x) -> (GrantedAuthority) () -> x).toList();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decoded.getSubject(), null, authorities));
            this.logger.info("### valid token");
        }
        chain.doFilter(request, response);
    }
}
