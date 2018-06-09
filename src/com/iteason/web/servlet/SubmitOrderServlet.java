package com.iteason.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iteason.domain.Cart;
import com.iteason.domain.CartItem;
import com.iteason.domain.Order;
import com.iteason.domain.OrderItem;
import com.iteason.domain.Product;
import com.iteason.domain.User;
import com.iteason.service.ProductService;
import com.iteason.utils.GetUUID;

public class SubmitOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//判断用户是否登陆
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			//没有登陆 
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		Cart cart = (Cart)session.getAttribute("cart");
		
		//获得商品总额total
		String totalStr = request.getParameter("total");
		double total = 0;
		total = Double.parseDouble(totalStr);
		
		
		/*
		 *  1、private String oid;
			2、private String ordertime;
			3、private double total;
			4、private int state;
			5、private String address;
			
			6、private String name;
			7、private String telephone;
			8、private User uid;
			
			9、List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
		 */
		//提交订单
		
		//封装好Order对象并提交到service层
		Order order = new Order();
		//1、private String oid;
		order.setOid(GetUUID.getUUID());
		//2、private String ordertime;
		order.setOrdertime("2017");
		//3、private double total;
		order.setTotal(total);
		//4、private int state;
		order.setState(0);
		//5、private String address;
		order.setAddress("address");
		//6、private String name;
		order.setName("name");
		//7、private String telephone;
		order.setTelephone("telephone");
		//8、private User uid;
		order.setUser(user);
		//9、List<OrderItem> orderItems = new ArrayList<OrderItem>();
		Map<String, CartItem> cartItems = cart.getCartItems();
		for(Map.Entry<String, CartItem> entry:cartItems.entrySet()){
			//Map.Entry<String, CartItem>---键值对对象
			//cartItems.entrySet()---cartItems的键值对集合对象
			CartItem cartItem = entry.getValue();
			OrderItem orderItem = new OrderItem();
			/*
			 * private String itemid;
				private int count;
				private double subtotal;
				private Product product;
				private Order order;
			 */
			orderItem.setItemid(GetUUID.getUUID());
			orderItem.setCount(cartItem.getBuyNumber());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			//
			order.getOrderItems().add(orderItem);
			
		}
		
		//order对象封装完毕
		//传递数据到service层
		ProductService service = new ProductService();
		try {
			service.submitOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//数据回显
		session.setAttribute("order", order);
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}