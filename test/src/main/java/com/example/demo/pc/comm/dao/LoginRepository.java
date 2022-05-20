package com.example.demo.pc.comm.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.pc.comm.vo.UserInfo;

@Repository("LoginDao")
public class LoginRepository{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	//private final String log = "";
	
	public UserInfo getUserId(String userId) {
		
		UserInfo us = new UserInfo();		
		us.setUSER_IDXX(userId);
		
		return sqlSessionTemplate.selectOne("Login.SelectUser",us);
	}
	
	
	
	
}
