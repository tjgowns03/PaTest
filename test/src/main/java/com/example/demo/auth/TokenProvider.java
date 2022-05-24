package com.example.demo.auth;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.filter.JwtFilter.TokenAuthentication;
import com.example.demo.pc.comm.vo.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


public class TokenProvider implements AuthenticationProvider {
	
	private static final String AUTHORITIES_KEY = "authorization";
    private static final String BEARER_TYPE = "bearer";
    private static final long 	ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long 	REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;
    
    //-토큰 인코딩
    public TokenProvider () {
    	
    	String secretKey = "adsfassefawefqwefewafawfedsfsdafsafsefwfqwezxcasdqweqweasdzxasdadadqwdlojndsofjndsfjnosdfnjosdifsjndofsodnjfi";

    	byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    	System.out.println("======================================");
        this.key = Keys.hmacShaKeyFor(keyBytes);
        
    }
    
    //-토큰 생성
    public TokenDto generateTokenDto(Authentication authentication) {
    	
    	//권한들 가져오기
    	String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    	
    	long now = (new Date()).getTime();
    	
    	//-Access Token 생성
    	Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    	
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();
    	
    	//-Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    	
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
        
    }
    
    //-토큰 정보 가져오기
    public Authentication getAuthentication(String accessToken) {
    	
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    
    //-토큰 검증
    public boolean validateToken(String token) {
    	
    	try {
    		System.out.println(key);
    		System.out.println(token);
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            //log.info("잘못된 JWT 서명입니다.");
        	System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            //log.info("만료된 JWT 토큰입니다.");
        	System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            //log.info("지원되지 않는 JWT 토큰입니다.");
        	System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            //log.info("JWT 토큰이 잘못되었습니다.");
        	System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
    
    //-토큰 검증 로직
    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
    	TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication;
		
		authentication.setAuthenticated(true);
    	
		return authentication;
	}
    
    //-토큰 타입과 일치할 때 인증
	@Override
	public boolean supports(Class<?> authentication) {
		
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}

	private Claims parseClaims(String accessToken) {
    	
    	try {
    		System.out.println(key);
    		System.out.println(accessToken);
            return Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    
    

}
