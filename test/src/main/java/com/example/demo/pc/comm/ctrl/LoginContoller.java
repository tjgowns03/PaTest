package com.example.demo.pc.comm.ctrl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pc.comm.service.LoginService;
import com.example.demo.pc.comm.vo.UserInfo;

@Controller
public class LoginContoller {
	
	@Resource(name="LoginService")
    private LoginService service;
	
	//회원가입시 비밀번호를 인코딩 해줘야 회원가입이 가능하다.
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//로그인 페이지 access
	@GetMapping(value = "/login")
	public String Login(Model model,HttpServletRequest req,UserInfo user){
		
		return "/login";
	}

	@GetMapping(value = "/mainpage")
	public String loginSuccess(HttpServletRequest rq, UserInfo user) {
		
		return "/mainpage";
	}
		
	/*mybatis 연결 테스트
	* 
	*/
	@PostMapping(value = "/test")
	public UserInfo test(Map<String, Object> searchVo,HttpServletRequest request,UserInfo user) {
		
		
		UserInfo userInfo  = (UserInfo) service.processLogin(searchVo,request);
		
		System.out.println("Mybatis 테스트");
		System.out.println(user.getUSER_IDXX());
		//System.out.println(user.getPASSWORD());
		
		return userInfo;
	}
	
	
}
