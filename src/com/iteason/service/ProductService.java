package com.iteason.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import com.iteason.dao.ProductDao;
import com.iteason.domain.Category;
import com.iteason.domain.Order;
import com.iteason.domain.OrderItem;
import com.iteason.domain.PageBean;
import com.iteason.domain.Product;
import com.iteason.utils.MyDataSourceUtils;

public class ProductService {

	public List<Product> findHotProduct() throws SQLException {
		//获得热门商品
		ProductDao dao = new ProductDao();
		List<Product> hotProduct = dao.findHotProduct();
		return hotProduct;
	}

	public List<Product> findNewProduct() throws SQLException {
		// 获得最新商品
		ProductDao dao = new ProductDao();
		List<Product> newProduct = dao.findNewProduct();
		return newProduct;
	}

	public List<Category> findAllCategory() throws SQLException {
		// 获得分类商品
		ProductDao dao = new ProductDao();
		List<Category> category = dao.findAllCategory();
		return category;
	}

	public PageBean<Product> findProductById(String cid,int currentPage,int currentCount ) throws SQLException {
		ProductDao dao = new ProductDao();
		//获得pagebean，即分页的数据bean
		PageBean<Product> pageBean = new PageBean<Product>();
		
		
		//封装当前页
		pageBean.setCurrentPage(currentPage);
		
		//封装当前页条数
		pageBean.setCurrentCount(currentCount);
		
		//封装总条数
		int totalCount =dao. getCount(cid);
		pageBean.setTotalCount(totalCount);
		
		//封装总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		
		//每页显示的数据
		int index = (currentPage - 1)*currentCount;
	   List<Product> list = dao.getProductByCategory(cid,index,currentCount);
	   pageBean.setList(list);
	   
		return pageBean;
	}

	public Product findProductInfoById(String pid) throws SQLException {
		// 
		ProductDao dao = new ProductDao();
		Product productInfo = dao.findProductInfoById(pid);
		return productInfo;
	}

	public void submitOrder(Order order) throws SQLException {
			// 用户订单的提交
			ProductDao dao = new ProductDao();
		
		try {
			//1、开启事务
			MyDataSourceUtils.startTransaction();
			//2、调用dao存储order表数据的方法
			dao.addOrders(order);
			//3、调用dao存储orderitem表数据的方法
			dao.addOrderItem(order);
		} catch (SQLException e) {
			//4、失败回滚
			MyDataSourceUtils.rollback();
		}finally{
			//5、成功提交
			MyDataSourceUtils.commit();
		}
		
		
	}

	public void updateOrder(Order order) throws SQLException {
		// 更新Order
		ProductDao dao = new ProductDao();
		dao.updateOrder(order);
		
	}

	public List<Order> findAllOrders(String uid) throws SQLException {
		// 获得该用户的订单
		ProductDao dao = new ProductDao();
		List<Order> orderList = dao.findAllOrders(uid);
		return orderList;
	}

	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		// 获得orderitem
		ProductDao dao = new ProductDao();
		List<Map<String, Object>> mapList = dao.findAllOrderItemByOid(oid);
		return mapList;
	}


}
