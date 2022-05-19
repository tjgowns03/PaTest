package com.example.demo.pc.comm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
	//로그인
	public Object processLogin(Map<String, Object> searchVo, HttpServletRequest request); 
}
