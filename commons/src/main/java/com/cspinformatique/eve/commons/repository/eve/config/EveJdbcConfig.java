package com.cspinformatique.eve.commons.repository.eve.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ImportResource("classpath:persistence/applicationContext-eveDatasource.xml")
public class EveJdbcConfig {
	@Autowired private DataSource eveDatasource;
	
	public @Bean JdbcTemplate eveJdbcTemplate(){
		return new JdbcTemplate(this.eveDatasource);
	}
}