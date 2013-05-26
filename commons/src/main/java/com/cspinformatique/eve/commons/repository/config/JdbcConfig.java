package com.cspinformatique.eve.commons.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:jeve/spring/applicationContext-datasource.xml")
public class JdbcConfig {}