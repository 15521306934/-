package com.iteason.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iteason.domain.User;
import com.iteason.service.UserService;

public class LogingServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置编码表
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		//验证码校验
		String checkCode = request.getParameter("checkCode");
		String inputCode = (String)session.getAttribute("checkcode_session");
		if( !inputCode.equals(checkCode) ){
			//验证码不相同，给出去提
			request.setAttribute("checkInfo", "您输入的验证码不正确！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//验证码正确
		
		//获取登陆信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//传递给service层
		User user = null;
		UserService service = new UserService();
		try {
			 user = service.loginTest(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//回显
		if(user != null){
			//登陆成功
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}else{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("账号或密码错误");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	
}
