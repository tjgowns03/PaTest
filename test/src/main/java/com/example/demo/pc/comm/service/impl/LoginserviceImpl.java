package com.example.demo.pc.comm.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import com.example.demo.pc.comm.dao.LoginDao;
import com.example.demo.pc.comm.service.LoginService;
import com.example.demo.pc.comm.vo.UserInfo;

@Service("LoginService")
public class LoginserviceImpl implements LoginService {

	@Resource(name =  "LoginDao")
	private LoginDao loginDao;
	
	@Override
	public UserInfo processLogin(Map<String, Object> searchVo, HttpServletRequest request) {
		
		return loginDao.Login();
	}

}
