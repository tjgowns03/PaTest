package com.example.demo.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.pc.comm.vo.UserInfo;

/**
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인 진행이 완료가 되면 session을 만들어준다.(세션 공간은 똑같은데 Security ContextHolder 키 값에 저장한다.)
 * 오브젝트 =>Authentication 타입 객체
 * Authentication 안에 User 정보가 있어야 됨.
 * User 오브젝트의 타입은 -> UserDetails 타입 객체
 * 
 * Security Session -> Authentication -> UserDetails(안에 userInfo객체가 있다)  
 * 
 * */
public class PrincipalDetails implements UserDetails{

	private UserInfo user;
	
	public PrincipalDetails(UserInfo user) {
		
		this.user = user;
	}
	
	//해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collect = new ArrayList<>();
		
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return user.getUSDN_CODE();
			}
		});
		
		return collect;
	}
	
	//비밀번호
	@Override
	public String getPassword() {
		return user.getSCRT_NUMB();
	}
	//사용자 명
	@Override
	public String getUsername() {
		return user.getUSER_NAME();
	}
	
	//계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		
		//테스트를 위해 true로 하드코딩
		return true;
	}
	
	//계정이 잠겼는지
	@Override
	public boolean isAccountNonLocked() {
		
		//테스트를 위해 true로 하드코딩
		return true;
	}
	//비밀번호 기간 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		
		//테스트를 위해 true로 하드코딩
		return true;
	}
	//계정 활성화 
	@Override
	public boolean isEnabled() {
		
		//테스트를 위해 true로 하드코딩
		return true;
	}

}
