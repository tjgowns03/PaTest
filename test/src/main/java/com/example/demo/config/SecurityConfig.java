package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http
	         .csrf().disable()
	         .authorizeRequests(authorize -> authorize
	                 .antMatchers("/css/**","/index").permitAll()
	                 .antMatchers("/user/**").hasRole("USER")  //user로 들어올 경우 권한 체크
	         )
	         .formLogin( formLogin -> formLogin
	                 .loginPage("/login") 					// 불러올 로그인 페이지(권한이 없으면 로그인페이지로 이동)
	                 .loginProcessingUrl("/login_post")		//로그인 정보를 보낼 액션 페이지
	                 .defaultSuccessUrl("/loginSuccess")	//로그인 성공 시 보낼 페이지
	         );
		
	}
	
	
	
}
