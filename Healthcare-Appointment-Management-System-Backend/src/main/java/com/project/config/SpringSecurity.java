package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.project.filter.JwtFilter;
import com.project.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity implements WebMvcConfigurer{

	@Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                		.requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                		.requestMatchers("/doctor/**").hasRole("DOCTOR")
                      	.requestMatchers("/patient/**").hasRole("PATIENT")
                        .requestMatchers("/api/authController/**").permitAll()
                        .anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") 
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") 
                .allowedHeaders("Authorization", "Content-Type") 
                .allowCredentials(true) 
                .maxAge(3600); 
    }
}


    
    

