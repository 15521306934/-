package com.iteason.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iteason.domain.User;

public class UserLoginPriviliageFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	 
		HttpServletRequest Hrequest = (HttpServletRequest)request;
		HttpServletResponse Hresponse = (HttpServletResponse)response;
		
		//判断
		HttpSession session = Hrequest.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			Hresponse.sendRedirect(Hrequest.getContextPath()+"/login.jsp");
			return;
		}else {
			chain.doFilter(Hrequest, Hresponse);//放行
		}
	
	}
	 
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
