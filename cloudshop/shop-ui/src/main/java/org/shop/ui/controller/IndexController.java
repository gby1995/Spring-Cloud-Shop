package org.shop.ui.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shop.ui.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class IndexController{

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpSession session){
	
		Map<String, List<Product>> map = this.restTemplate.getForObject("http://shop-product", Map.class);
		request.setAttribute("nList", map.get("nList"));
		request.setAttribute("hList", map.get("hList"));
		session.setAttribute("cList", map.get("cList"));
		return "index";
	}
	
	
}
