package org.shop.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.shop.order.service.OrderService;
import org.shop.order.utils.PageBean;
import org.shop.order.vo.Order;
import org.shop.order.vo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

	// 注入OrderService
	@Resource
	private OrderService orderService;

	// 生成订单的执行的方法:
	@PostMapping("/saveOrder")
	public String saveOrder(@RequestBody Order order) {
		System.out.println(order.getName());
		orderService.save(order);
		return "order";
	}

	// 查询我的订单:
	@RequestMapping("/findByUid")
	public PageBean<Order> findByUid(Integer page, Integer uid) {
		
		PageBean<Order> list = orderService.findByUid(uid, page);

		return list;
	}

	// 根据订单id查询订单:
	@RequestMapping("/findByOid")
	public String findByOid(@RequestBody Order order) {
		order = orderService.findByOid(order.getOid());
		return "order";
	}


	
	// 修改订单的状态:
	@RequestMapping("/updateState")
	public String updateState(@RequestBody Order order){
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		return "updateStateSuccess";
	}
}
