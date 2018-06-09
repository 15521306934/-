package com.iteason.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iteason.domain.Cart;
import com.iteason.domain.CartItem;

public class DeleteCartItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//删除购物车项
		String pid = request.getParameter("pid");
		//删除session中cartitem集合项的item
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart != null){
			Map<String, CartItem> cartItems = cart.getCartItems();
			//修改总价
			cart.setTotalSum(cart.getTotalSum()-cartItems.get(pid).getSubTotal());
			//删除该pid的item
			cartItems.remove(pid);
			cart.setCartItems(cartItems);
		}
		
		session.setAttribute("cart", cart);
		//跳转回购物车
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}