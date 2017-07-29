package org.shop.ui.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.shop.ui.utils.PageBean;
import org.shop.ui.vo.Cart;
import org.shop.ui.vo.CartItem;
import org.shop.ui.vo.Order;
import org.shop.ui.vo.OrderItem;
import org.shop.ui.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private RestTemplate restTemplate;

	// 生成订单的执行的方法:
	@RequestMapping("/saveOrder")
	public String saveOrder(HttpSession session, Order order) {

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			session.setAttribute("message", "亲!您还没有购物!");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		// 设置订单的状态
		order.setState(1); // 1:未付款.
		// 设置订单时间
		order.setOrdertime(new Date());
		// 设置订单关联的客户:
		User existUser = (User) session.getAttribute("existUser");
		if (existUser == null) {
			session.setAttribute("message", "亲!您还没有登录!");
			return "msg";
		}
		order.setUser(existUser);
		// 设置订单项集合:
		System.out.println(cart.getCartItems());
		for (CartItem cartItem : cart.getCartItems()) {
			// 订单项的信息从购物项获得的.
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOid(order.getOid());

			order.getOrderItems().add(orderItem);
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();
		
		
		String str = this.restTemplate.postForObject("http://shop-order/order/saveOrder", order, String.class);
		if(str.equals("order")){
			cart.clearCart();
			session.setAttribute("order", order);
		
			return "order";
		}
		return "error";
	}

	// 查询我的订单:
	@RequestMapping("/findByUid")
	public String findByUid(HttpSession session, Integer page) {
		User existUser = (User) session.getAttribute("existUser");
		// 获得用户的id
		Integer uid = existUser.getUid();
		PageBean<Order> list = this.restTemplate.getForObject("http://shop-order/order/findByUid?page="+page+"&uid="+uid, PageBean.class);
		session.setAttribute("pageBean", list);
		return "order";
	}

	// 根据订单id查询订单:
	@RequestMapping("/findByOid")
	public String findByOid(Order order) {
	
		String str = this.restTemplate.postForObject("http://shop-order/order/findByOid", order, String.class);
		return "order";
	}
	
	// 修改订单的状态:
	@RequestMapping("/updateState")
	public String updateState(Order order){
		
		String str = this.restTemplate.postForObject("http://shop-order/order/updateState", order, String.class);
		if(str.equals("updateState")){
			return "updateStateSuccess";
		}
		return "error";
	}
}
