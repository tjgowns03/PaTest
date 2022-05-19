package com.example.demo.pc.comm.ctrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.pc.comm.service.LoginService;
import com.example.demo.pc.comm.vo.UserInfo;

@Controller
public class LoginContoller {
	
	@Resource(name="LoginService")
    private LoginService service;
	
	//로그인 페이지 access
	@GetMapping(value = "/login")
	public String Login(Model model,HttpServletRequest req){
		
		System.out.println("컨트롤러 로그인 실행");
		
		return "/login";
	}
	
//	@GetMapping(value = "/test")
//	public UserInfo test(Map<String, Object> searchVo,HttpServletRequest request) {
//		
//		return (UserInfo) service.processLogin(searchVo,request);
//	}
	//로그인 처리 프로세스
	@PostMapping(value = "/login_post")
	public String test(HttpServletRequest rq, UserInfo user) {
		System.out.println(rq.getParameter("USER_IDXX"));
		System.out.println(user.getUSER_IDXX());
		System.out.println(user.getPASSWORD());
		
		return "/mainPage";
	}
	
	
	
}
