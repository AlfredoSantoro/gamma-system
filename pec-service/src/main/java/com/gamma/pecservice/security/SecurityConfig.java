package com.gamma.pecservice.security;

import com.gamma.auth.AuthorizationFilter;
import com.gamma.auth.Sha256PasswordEncoder;
import com.gamma.auth.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Value("${identity.jwt.prefix}")
    private String prefix;
    @Value("${identity.jwt.secret}")
    private String secret;
    @Value("${identity.jwt.param}")
    private String param;
    @Value("${app.security.service.name}")
    private String serviceName;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/messages/**")
                .hasAnyAuthority(serviceName)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new AuthorizationFilter(this.authenticationManager(), this.param, this.prefix, this.secret), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService)
                .passwordEncoder(new Sha256PasswordEncoder());
    }
}
