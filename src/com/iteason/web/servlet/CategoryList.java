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
import com.iteason.service.ProductService;
import com.iteason.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CategoryList extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ProductService service = new ProductService();
		 List<Category> findAllCategory = null;
		 try {
			findAllCategory = service.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 //gson解析
		 Gson gson = new Gson();
		 String json = gson.toJson(findAllCategory);
		 //返回给请求页
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html;charset=UTF-8");
		 response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}