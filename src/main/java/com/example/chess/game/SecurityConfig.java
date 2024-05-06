package com.example.chess.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig  {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager()  {

        UserDetails user1= User.builder().username("hany").password("{noop}123").roles("m").build();
        UserDetails user2= User.builder().username("nada").password("{noop}123").roles("m").build();


        return new InMemoryUserDetailsManager(user1,user2);

    }





}


