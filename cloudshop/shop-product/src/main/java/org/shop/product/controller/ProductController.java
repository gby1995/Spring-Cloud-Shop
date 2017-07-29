package org.shop.product.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.shop.product.service.ProductService;
import org.shop.product.utils.PageBean;
import org.shop.product.vo.Product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Resource
	private ProductService productService;
	
	// 根据商品的ID进行查询商品:执行方法:
	@RequestMapping("/findByPid")
	public Product findByPid(Integer pid, HttpServletRequest request) {
		// 调用Service的方法完成查询.
		Product product = productService.findByPid(pid);
		request.setAttribute("model", product);
		return product;
	}

	// 根据分类的id查询商品:
	@RequestMapping("/findByCid")
	public PageBean<Product> findByCid(HttpServletRequest request, Integer cid, Integer page) {
		// List<Category> cList = categoryService.findAll();
		PageBean<Product> pageBean = productService.findByPageCid(cid, page);// 根据一级分类查询商品,带分页查询
		// 将PageBean存入到值栈中:
		request.setAttribute("pageBean", pageBean);
		return pageBean;
	}

	// 根据二级分类id查询商品:
	@RequestMapping("/findByCsid")
	public PageBean<Product> findByCsid(HttpServletRequest request, Integer csid,  Integer page) {
		// 根据二级分类查询商品
		PageBean<Product> pageBean = productService.findByPageCsid(csid, page);
		// 将PageBean存入到值栈中:
		request.setAttribute("pageBean", pageBean);
		return pageBean;
	}
}
