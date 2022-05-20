package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.example.demo.auth.PrincipalDetailsService;

@Configuration
@EnableWebSecurity//-스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String[] staticFileList = {"/css/**","/index",};
	
	@Autowired
	private PrincipalDetailsService principalDetailsService; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	 	 
	 	 
		 http
	         .csrf().disable()
	         .authorizeRequests(authorize -> authorize
	                 .antMatchers("/mainPage").permitAll()
	                 //.antMatchers("/**/user").hasRole("USER")  //user로 들어올 경우 권한 체크
	                 .antMatchers("/mainPage/**").authenticated()
	                 .anyRequest().permitAll()
	                 //.anyRequest().authenticated()				
	         )
	         .formLogin( formLogin -> formLogin
	                 .loginPage("/login") 					// 불러올 로그인 페이지(권한이 없으면 로그인페이지로 이동)
	                 .loginProcessingUrl("/login_post")		//"login_post"로 들어오는 요청을 security에서 낚아챈다. (로그인 정보를 보낼 액션 페이지)
//	                 .failureHandler(new AuthenticationFailureHandler() {
//						
//						@Override
//						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//								AuthenticationException exception) throws IOException, ServletException {
//							
//							System.out.println("fail out");
//							
//						}
//					})
	                 .passwordParameter("SCRT_NUMB")
	                 .defaultSuccessUrl("/mainpage", true)		//로그인 성공 시 보낼 페이지	  
	                 .failureForwardUrl("/login")
	         )
	         .logout()
//	         .logoutRequestMatcher(null)					//로그아웃을 호출할 경우 해당 경로
//	         .logoutSuccessUrl(null)						//로그아웃이 성공했을 경우 해당 경로
	         .invalidateHttpSession(true); 					//로그아웃시 인증정보를 지우고 세션을 무효화 시키는 설정
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		//-정적인 파일에 대한 요청들은 무시한다.
		web.ignoring().antMatchers(staticFileList);
	
	}
	
	//로그인을 할 때 비밀번호가 들어갈 텐데 인코딩 된 비밀번호를 비교해주는 역할을 해준다. 
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	
	
}
