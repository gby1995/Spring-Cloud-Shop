package org.shop.product.service;

import java.util.List;

import javax.annotation.Resource;

import org.shop.product.mapper.CategorySecondMapper;
import org.shop.product.utils.PageBean;
import org.shop.product.vo.CategorySecond;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategorySecondService {
	// 注入Dao
	@Resource
	private CategorySecondMapper categorySecondMapper;


	// 二级分类带有分页的查询操作:
	public PageBean<CategorySecond> findByPage(Integer page) {
		PageBean<CategorySecond> pageBean = new PageBean<CategorySecond>();

		// 设置参数:
		pageBean.setPage(page);
		// 设置每页显示记录数:
		int limit = 10;
		pageBean.setLimit(limit);
		// 设置总记录数:
		int totalCount = categorySecondMapper.findCount();
		pageBean.setTotalCount(totalCount);
		// 设置总页数:
		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置页面显示数据的集合:
		int begin = (page - 1) * limit;
		List<CategorySecond> list = categorySecondMapper.findByPage(begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	// 业务层的保存二级分类的操作
	public void save(CategorySecond categorySecond) {
		categorySecondMapper.save(categorySecond);
	}

	// 业务层的删除二级分类的操作
	public void delete(CategorySecond categorySecond) {
		categorySecondMapper.delete(categorySecond.getCsid());
	}

	// 业务层根据id查询二级分类
	public CategorySecond findByCsid(Integer csid) {
		return categorySecondMapper.findByCsid(csid);
	}

	// 业务层修改二级分类的方法
	public void update(CategorySecond categorySecond) {
		categorySecondMapper.update(categorySecond);
	}

	// 业务层查询所有二级分类(不带分页)
	public List<CategorySecond> findAll() {
		return categorySecondMapper.findAll();
	}

}
