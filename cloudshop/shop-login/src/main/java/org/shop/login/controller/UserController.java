package org.shop.login.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.shop.login.service.UserService;
import org.shop.login.vo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController{
	
	// 注入UserService
	@Resource
	private UserService userService;

	/**
	 * AJAX进行异步校验用户名的执行方法
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/findByName")
	public String findByName(String username) throws IOException {
		// 调用Service进行查询:
		User existUser = userService.findByUsername(username);
		System.out.println(username);
		// 判断
		if (existUser != null) {
			// 查询到该用户:用户名已经存在
			return "<font color='red'>用户名已经存在</font>";
		} else {
			// 没查询到该用户:用户名可以使用
			return "<font color='green'>用户名可以使用</font>";
		}
	}

	/**
	 * 用户注册的方法:
	 */
	@RequestMapping("/regist")
	public String regist(@RequestBody User user) {
		
		userService.save(user);
		return "msg";
	}

	/**
	 * 用户激活的方法
	 */
	@RequestMapping("/active")
	public String active(@RequestBody User user, HttpSession session) {
		// 根据激活码查询用户:
		User existUser = userService.findByCode(user.getCode());
		// 判断
		if (existUser == null) {
			// 激活码错误的
			session.setAttribute("msg", "激活失败:激活码错误!");
		} else {
			// 激活成功
			// 修改用户的状态
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			session.setAttribute("msg", "激活成功:请去登录!");
		}
		return "msg";
	}


	/**
	 * 登录的方法
	 * @throws Exception 
	 */
	@PostMapping(value="/login")
	public User login(@RequestBody User user){
		
		User existUser = userService.login(user);
		
		return existUser;
	
	}
	

}
