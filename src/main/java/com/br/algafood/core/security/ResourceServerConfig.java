package com.br.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//.httpBasic()
			//.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
			.and()
				.cors()
			.and()
				.oauth2ResourceServer()
				.jwt();
	}
	
//	@Bean
//	public JwtDecoder jwtDecoder() {
//		var secretKey = new SecretKeySpec("jaiUJIDSHQ8HEDJasdbjakdbjklahndfjkHSOHKklçjOLDJaopdJDOJAnklahnfdkah".getBytes(), "HmacSHA256");
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}

}
