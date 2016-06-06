package com.thoughtworks.users.config;

import com.thoughtworks.users.service.LadderUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                antMatchers("/node_modules/**", "css/**").permitAll().
                anyRequest().
                authenticated().
                and().
                formLogin().
                loginPage("/login").
                permitAll().
                and().
                logout().
                permitAll().
                and().
                csrf().
                disable();
    }

    @Autowired
    private LadderUserDetailsService ladderUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(ladderUserDetailsService);
    }
}
