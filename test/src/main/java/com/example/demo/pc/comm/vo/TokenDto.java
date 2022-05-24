package com.example.demo.pc.comm.vo;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
	
	private String grantType;
	private String accessToken;
	private String refreshToken;
	private Long accessTokenExpiresIn;
	
}
