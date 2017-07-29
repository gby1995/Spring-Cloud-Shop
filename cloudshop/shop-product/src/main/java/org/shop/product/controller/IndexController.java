package org.shop.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shop.product.service.CategoryService;
import org.shop.product.service.ProductService;
import org.shop.product.vo.Category;
import org.shop.product.vo.Product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController{
	// 注入一级分类的Service:
	@Resource
	private CategoryService categoryService;
	// 注入商品的Service
	@Resource
	private ProductService productService;
	
	/**
	 * 执行的访问首页的方法:
	 */
	@RequestMapping("/")
	public Map<String, List> index(HttpServletRequest request, HttpSession session){
		// 查询所有一级分类集合
		List<Category> cList = categoryService.findAll();
		Map<String, List> map = new HashMap<>();
		// 将一级分类存入到Session的范围:
		session.setAttribute("cList", cList);
		// 查询热门商品:
		List<Product> hList = productService.findHot();
		// 保存到值栈中:
		request.setAttribute("hList", hList);
		// 查询最新商品:
		List<Product> nList = productService.findNew();
		
		map.put("nList", nList);
		map.put("hList", hList);
		map.put("cList", cList);
		// 保存到值栈中:
		request.setAttribute("nList", nList);
		return map;
	}
	
	
}
