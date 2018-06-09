package com.iteason.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.iteason.domain.Category;
import com.iteason.domain.Product;
import com.iteason.service.ProductService;

public class IndexServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用service层，ProductService
		ProductService service = new ProductService();
		
		//准备热门商品--->List<Product>
		List<Product> hotProduct = 	null;
		try {
			hotProduct = service.findHotProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("hotProduct", hotProduct);
		
		//准备最新商品--->List<Product>
		List<Product> newProduct = 	null;
		try {
			newProduct = service.findNewProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("newProduct", newProduct);
		
		//准备分类商品
		/*List<Category> category  = null;
		try {
			category = service.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("category", category);
		response.setCharacterEncoding("UTF-8");
		String CategoryJson = Gson.
		response.getWriter().write();*/
		
		//转发
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}