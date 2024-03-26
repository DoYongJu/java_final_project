package com.damoyeo.healthyLife.config;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import org.springframework.web.filter.CorsFilter;


import com.damoyeo.healthyLife.security.JwtAuthenticationFilter;



@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors()
		.and()
		.csrf()
		.disable()
		.httpBasic()
		.disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers(
				"/trainerApi/getImage/*",
				"/ws/chat",
				"/chat/**",
				"/caloryApi/**", 
				"/WiseSayingApi",
				"/memberApi/findPwd",
				"/memberApi/findId",
				"/memberApi/signup",
				"/memberApi/checkUsername",
				"/memberApi/checkEmail" ,
				"/memberApi/signin",
				"/memberApi/signin/mailConfirm", 
				"/adminApi/login",
				"/",
				"/wiseSayingApi",
				"/static/**",
				"/manifest.json",
				"/favicon.ico",
				"/img/**")
		.permitAll()
		.antMatchers("/adminApi/**").hasRole("ADMIN")
		.anyRequest()
		.authenticated(); // 로그인 페이지의 경로
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	//return http.build();
	
	}
	
	@Bean()
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}
}
