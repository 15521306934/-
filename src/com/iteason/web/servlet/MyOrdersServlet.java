package com.iteason.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.iteason.domain.Order;
import com.iteason.domain.OrderItem;
import com.iteason.domain.Product;
import com.iteason.domain.User;
import com.iteason.service.ProductService;

public class MyOrdersServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断用户是否登陆
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			//没有登陆 
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//查询该用户所有的订单信息（单表
		ProductService service = new ProductService();
		//集合中的每个Order对象是不完整的，缺少OrderItem
		List<Order> orderList = null;
		try {
			 orderList = service.findAllOrders(user.getUid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//循环所有的订单为每个订单填充订单项信息
		if(orderList != null){
			for(Order order:orderList){
				//获得oid
				String oid = order.getOid();
				//查询该订单的所有的订单项
				List<Map<String, Object>> mapList;
				try {
					mapList = service.findAllOrderItemByOid(oid);
					for(Map<String, Object> map:mapList){
						//从map中取出count subtotal 封装到OrderItem中
						OrderItem item = new OrderItem();
						BeanUtils.populate(item, map);
						Product product = new Product();
						BeanUtils.populate(product, map);
						//将Product封装到OrderItem中
						item.setProduct(product);
						//将orderitem封装到order中的orderItemList中
						order.getOrderItems().add(item);
					 
					}
				} catch (SQLException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}