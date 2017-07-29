package org.shop.product.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.shop.product.service.ProductService;
import org.shop.product.vo.Cart;
import org.shop.product.vo.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/cart")
public class CartController {
	
	// 注入商品的Service
	@Resource
	private ProductService productService;

	// 将购物项添加到购物车:执行的方法
	@RequestMapping("/addCart")
	public Product addCart(Integer count, Integer pid, HttpSession session) {
		
		Product product = productService.findByPid(pid);
		
		
		return product;
	}

	// 清空购物车的执行的方法:
	@RequestMapping("/clearCart")
	public String clearCart(HttpSession session){
		// 获得购物车对象.
		Cart cart = getCart(session);
		// 调用购物车中清空方法.
		cart.clearCart();
		return "cart";
	}
	
	// 从购物车中移除购物项的方法:
	@RequestMapping("/removeCart")
	public String removeCart(Integer pid, HttpSession session){
		// 获得购物车对象
		Cart cart = getCart(session);
		// 调用购物车中移除的方法:
		cart.removeCart(pid);
		// 返回页面:
		return "cart";
	}
	
	// 我的购物车:执行的方法
	@RequestMapping("/myCart")
	public String myCart(){
		return "cart";
	}
	
	/**
	 * 获得购物车的方法:从session中获得购物车.
	 * @return
	 */
	private Cart getCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
}
