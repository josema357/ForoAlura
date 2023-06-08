package com.alura.foro.infra.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	private SecurityFilter securityFilter;
	
	@Autowired
	public SecurityConfigurations(SecurityFilter securityFilter) {
		this.securityFilter=securityFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf().disable()
				.authorizeHttpRequests(req -> req
		                .requestMatchers(
		                        "/login",
		                        "/v3/api-docs/**",
		                        "/swagger-ui/**").permitAll()
		                .anyRequest()
		                .authenticated())
				
				.exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> {
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write("{ \"message\": \"Tu no estas autenticado.\" }");
                })
                
                .and()
                .sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationconfiguration) throws Exception {
		return authenticationconfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public OpenAPI personalizarOpenAPI() {
		final String securitySchemeName = "bearerAuth";
	    return new OpenAPI()
	    		.addSecurityItem(new SecurityRequirement()
	    				.addList(securitySchemeName)
	                )
	            .components(new Components()
	                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
	                                .name(securitySchemeName)
	                                .type(SecurityScheme.Type.HTTP)
	                                .scheme("bearer")
	                                .bearerFormat("JWT")
	                        )
	             )
	             .info(new Info()
	                        .title("Foro-Alura")
	                        .version("1.0")
	                        .description("API creada del Challenge Oracle ONE")
	             );
	    }
}
