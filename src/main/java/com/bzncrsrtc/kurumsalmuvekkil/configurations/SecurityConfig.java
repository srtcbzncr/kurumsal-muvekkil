package com.bzncrsrtc.kurumsalmuvekkil.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeHttpRequests()
        	.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
        	.anyRequest().authenticated();
		http.httpBasic(Customizer.withDefaults());
	    return http.build();
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return NoOpPasswordEncoder.getInstance();
	  }
	  
}
