package com.bzncrsrtc.kurumsalmuvekkil.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bzncrsrtc.kurumsalmuvekkil.security.filters.CheckAuthHeaderFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeHttpRequests()
        	.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
        	.requestMatchers(new AntPathRequestMatcher("/auth/login")).permitAll()
        	.anyRequest().authenticated();
		http.httpBasic(Customizer.withDefaults());
		http.cors();
		http.addFilterBefore(new CheckAuthHeaderFilter(), UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	  }
	  
	  @Bean
	  public AuthenticationManager authenticationManager(
	      AuthenticationConfiguration configuration) throws Exception {
		  return configuration.getAuthenticationManager();
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return NoOpPasswordEncoder.getInstance();
	  }
	  
}
