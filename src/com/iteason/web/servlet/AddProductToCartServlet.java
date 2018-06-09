package com.iteason.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iteason.domain.Cart;
import com.iteason.domain.CartItem;
import com.iteason.domain.Product;
import com.iteason.service.ProductService;

public class AddProductToCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//创建session
		HttpSession session = request.getSession();
		
		//获得商品id，添加到购物车
		
		//获得pid
		String pid = request.getParameter("pid");
		//获得该商品的购买数量buyNumber
		int buyNumber = Integer.parseInt(request.getParameter("buyNumber"));
		
		
		//获得该id对应的Product对象
		ProductService service = new ProductService();
		Product product = null;
		try {
			  product = service.findProductInfoById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//计算小计
		double oldSubTotal = product.getShop_price()*buyNumber;
		//封装数据在CartItem对象中
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setBuyNumber(buyNumber);
		cartItem.setSubTotal(oldSubTotal);
		
		//获得购物车项，判断其中是否含有这一项
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		
		//将购物项放车里,key是pid,值是cartItem
		//先判断pid是否存在，若已存在，则商品的数量叠加
		Map<String, CartItem> cartItems = cart.getCartItems();
		double newSubTotal = 0;
		if(cartItems.containsKey(pid)){
			//取出商品的数量并相加
			CartItem item = cartItems.get(pid);
			int  oldNumber = item.getBuyNumber();
			int newBuyNumber = oldNumber + buyNumber;
			item.setBuyNumber(newBuyNumber);
			
			//修改小计
			 newSubTotal = newBuyNumber*item.getProduct().getShop_price();
			item.setSubTotal(newSubTotal);
			cart.getCartItems().put(pid,item);
			
		}else{
			//如果没有该pid，直接将此pid添加
			cart.getCartItems().put(pid, cartItem);
		}
		//计算总计
		double total = cart.getTotalSum()+buyNumber*cartItems.get(pid).getProduct().getShop_price();
		cart.setTotalSum(total); 
		//将购物车再次访问session
		session.setAttribute("cart", cart);
		
		//直接跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
