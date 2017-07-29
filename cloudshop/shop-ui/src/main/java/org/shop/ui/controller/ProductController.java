package org.shop.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.shop.ui.utils.PageBean;
import org.shop.ui.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;



@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private RestTemplate restTemplate;
	
	// 根据商品的ID进行查询商品:执行方法:
	@RequestMapping("/findByPid")
	public String findByPid(Integer pid, HttpServletRequest request) {
		
		Product model =  this.restTemplate.getForObject("http://shop-product/product/findByPid?pid="+pid, Product.class);
		request.setAttribute("model", model);
		return "product";
	}

	// 根据分类的id查询商品:
	@RequestMapping("/findByCid")
	public String findByCid(HttpServletRequest request, Integer cid, Integer page) {
		// List<Category> cList = categoryService.findAll();
		PageBean<Product> pageBean =  this.restTemplate.getForObject("http://shop-product/product/findByCid?cid="+cid+"&page="+page, PageBean.class);
		request.setAttribute("cid", cid);
		request.setAttribute("pageBean", pageBean);
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<pageBean.getTotalPage(); i++){
			list.add(i);
		}
		request.setAttribute("list", list);
		return "productList";
	}

	// 根据二级分类id查询商品:
	@RequestMapping("/findByCsid")
	public String findByCsid(HttpServletRequest request, Integer csid,  Integer page) {
		PageBean<Product> pageBean =  this.restTemplate.getForObject("http://shop-product/product/findByCsid?csid="+csid+"&page="+page, PageBean.class);
		request.setAttribute("csid", csid);
		request.setAttribute("pageBean", pageBean);
		return "productList";
	}
}
