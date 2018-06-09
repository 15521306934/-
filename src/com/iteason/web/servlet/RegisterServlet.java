package com.iteason.web.servlet;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import com.iteason.domain.User;
import com.iteason.utils.GetUUID;
import com.iteason.utils.MailUtils;
import com.iteason.service.UserService;

public class RegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、设置编码表
		request.setCharacterEncoding("UTF-8");
		
		//2、获得表单数据
		Map<String, String[]> properties = request.getParameterMap();
		//3、封装表单数据到对应的User类中(BeanUtils工具)
		User user = new User();
		try {
			//映射封装
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		 //private String uid;
		 	user.setUid(GetUUID.getUUID());
		 // private String telephone;
		 	user.setTelephone("155");
		 // private int state;//是否激活
		 	user.setState(0);
		 //private String code;//激活码
		 	String activeCode = GetUUID.getUUID();
		 	user.setCode(activeCode);
		 
		//4、传递数据给service层
		UserService service = new UserService();
		boolean isRegisteSuccess = false;
		try {
			isRegisteSuccess = service.register(user);
		} catch (SQLException e) {
		}
		//5、数据回显
		if(isRegisteSuccess){
			String emailMsg = "注册成功！请点击链接进行激活：<a href='http://localhost:8080/HeimaShop/active?activeCode="+activeCode+"'>"
					+ "http://localhost:8080/HeimaShop/active?activeCode="+activeCode+"</a>";
			try {//发送以上信息给用户邮箱
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			//显示以下邮箱页面供用户快速选择登录
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//注册失败,重定向到失败页面
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
