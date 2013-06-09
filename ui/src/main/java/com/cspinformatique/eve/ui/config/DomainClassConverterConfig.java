package com.cspinformatique.eve.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class DomainClassConverterConfig extends WebMvcConfigurationSupport{
	 public @Bean DomainClassConverter<?> domainClassConverter(){
		 return new DomainClassConverter<FormattingConversionService>(
			this.mvcConversionService()
		);
	 }
}
