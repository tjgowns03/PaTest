package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.pc.comm.dao.LoginRepository;
import com.example.demo.pc.comm.vo.UserInfo;

/**
 * 시큐리티에서 설정에서 loginProcessingUrl("/login") 요청이 오면 자동으로 
 * UserDetailsService 타입으로 Ioc되어 있는 loadUserByUsername 함수가 실행
 * 
 */
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		UserDetails userDetails = (UserDetails)principal;
//		System.out.println(userDetails.getUsername());
//		System.out.println(userDetails.getPassword());
		
		System.out.println("-------------------------------------------------------------");
		System.out.println("전달받은 USER_ID 값 :"+username);
		System.out.println("-------------------------------------------------------------");
		
		// TODO Auto-generated method stub
		UserInfo user = loginRepository.getUserId(username);
		
		
		if(user != null) {
			return new PrincipalDetails(user);
		}
		
		return null;
	}
	
	
	
}
