package com.example.demo.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import com.example.demo.auth.TokenProvider;

public class JwtFilter extends AbstractAuthenticationProcessingFilter{

		
		public JwtFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
			super(requiresAuthenticationRequestMatcher, authenticationManager);
		}
	
		public JwtFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
			super(defaultFilterProcessesUrl, authenticationManager);
		}
	
		public JwtFilter(String defaultFilterProcessesUrl) {
			super(defaultFilterProcessesUrl);
		}
	
		protected JwtFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
			super(requiresAuthenticationRequestMatcher);
			// TODO Auto-generated constructor stub
		}
	
	
		public static final String AUTHORIZATION_HEADER = "Authorization";
	    public static final String BEARER_PREFIX = "Bearer ";
	    
	    private AuthenticationManager manager;
	    
	    public class TokenAuthentication implements Authentication {

			public TokenAuthentication(String token) {
				super();
				this.token = token;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return null;
			}

			@Override
			public Object getCredentials() {
				return null;
			}

			@Override
			public Object getDetails() {
				return null;
			}

			@Override
			public Object getPrincipal() {
				return null;
			}

			@Override
			public boolean isAuthenticated() {
				return false;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				
			}
			
	    	public String getToken() {
				return token;
			}

			public void setToken(String token) {
				this.token = token;
			}

			private String token;
	    }
	    
		@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
				throws AuthenticationException, IOException, ServletException {
			
			System.out.println(">>>>>>>>"+SecurityContextHolder.getContext().getAuthentication());
			//-Request Header?????? ????????? ??????
			String jwt = resolveToken(request);
		
			System.out.println(jwt);
			String token = null;
			String userName = null;
			
			//-validationToken ?????? ?????? ????????? ??????
			// ?????? ???????????? ?????? ???????????? Authentication ??? ???????????? SecurityContext ??? ??????


			//Authentication Authentication = getAuthenticationManager().authenticate(new TokenAuthentication("asdasdasdasdasdasdasd"));
			
			System.out.println(tokenProvider);
			
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
	            Authentication authentication = tokenProvider.getAuthentication(jwt);
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            
	            return authentication;
	        }

            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            return authentication;
		}

		// Request Header ?????? ?????? ????????? ????????????
	    private String resolveToken(HttpServletRequest request) {
	        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
	        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
	            return bearerToken.substring(7);
	        }
	        return null;
	    }

		public static String getAuthorizationHeader() {
			return AUTHORIZATION_HEADER;
		}

		public AuthenticationManager getManager() {
			return manager;
		}

		public void setManager(AuthenticationManager manager) {
			this.manager = manager;
		}
		
		private TokenProvider tokenProvider;

		public TokenProvider getTokenProvider() {
			return tokenProvider;
		}

		public void setTokenProvider(TokenProvider tokenProvider) {
			this.tokenProvider = tokenProvider;
		}
		
}
