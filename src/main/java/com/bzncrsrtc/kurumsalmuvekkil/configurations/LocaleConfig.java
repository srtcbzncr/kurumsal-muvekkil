package com.bzncrsrtc.kurumsalmuvekkil.configurations;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

	@Bean
	public AcceptHeaderLocaleResolver localeResolver() {
		final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		Locale locale = new Locale("tr", "TR");
		resolver.setDefaultLocale(locale);
		return resolver;
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}	
}
