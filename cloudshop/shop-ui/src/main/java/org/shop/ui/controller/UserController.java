package org.shop.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.shop.ui.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/user")
public class UserController{
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/registPage")
	public String registPage() {
		return "regist";
	}

	/**
	 * AJAX进行异步校验用户名的执行方法
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/findByName")
	public void findByName(HttpServletResponse response, String username) throws IOException {
		System.out.println(username);
		String str = this.restTemplate.getForObject("http://shop-login/user/findByName?username="+username,String.class);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().println(str);
	}

	@RequestMapping("/regist")
	public String regist(HttpSession session, String checkcode, User user) {
		String checkcode1 = (String)session.getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			session.setAttribute("msg", "验证码输入错误!");
		}
		String str = this.restTemplate.postForObject("http://shop-login/user/regist",user, String.class);
		if(str.equals("msg")){
			return "msg";
		}else{
			return "error";
		}
	}

	/**
	 * 用户激活的方法
	 */
	@RequestMapping("/active")
	public String active(User user, HttpSession session) {
		// 根据激活码查询用户:
	
		return "msg";
	}


	@RequestMapping("/loginPage")
	public String loginPage() {
		return "login";
	}


	@RequestMapping("/login" )
	public void login(User user, HttpSession session, HttpServletResponse response) throws Exception {
		
		User str = this.restTemplate.postForObject("http://shop-login/user/login", user, User.class);
		if(str == null){
			session.setAttribute("message", "登录失败:用户名或密码错误或用户未激活!");
			response.sendRedirect("/user/loginPage");
		}else{
			session.setAttribute("existUser", str);
			response.sendRedirect("/");
		}
	}
	
	/**
	 * 用户退出的方法
	 */
	@RequestMapping("/quit")
	public void quit(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 销毁session
		session.invalidate();
		response.sendRedirect("/");
	}

}
