package com.tp2.hospitalv2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
String encodePassword= passwordEncoder.encode("1234");
        return new InMemoryUserDetailsManager(User.withUsername("user1").password(encodePassword).roles("USER").build(),
                User.withUsername("user2").password(encodePassword).roles("USER").build(),
                User.withUsername("admin").password(encodePassword).roles("USER","ADMIN").build()

        );

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ar->ar.requestMatchers("/delete/**").hasRole("ADMIN"))

                .authorizeHttpRequests(ar->ar.anyRequest().authenticated()).
                build();
    }
@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
