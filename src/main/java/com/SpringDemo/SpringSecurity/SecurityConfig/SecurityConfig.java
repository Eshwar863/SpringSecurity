package com.SpringDemo.SpringSecurity.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
public HttpSecurity securityFilterChain (HttpSecurity http) throws Exception {

return http
.csrf(customizer -> {

            try {
                customizer.disable()
                .authorizeHttpRequests (request -> request.anyRequest().authenticated())
                .httpBasic (Customizer.withDefaults())
                .sessionManagement (session ->
                session.sessionCreationPolicy (SessionCreationPolicy.STATELESS))
                .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

}
@Bean
public AuthenticationProvider authenticationProvider(){
DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
provider.setPasswordEncoder (NoOpPasswordEncoder.getInstance());
provider.setUserDetailsService(userDetailsService);
return provider;
}
}