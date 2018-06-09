package com.iteason.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iteason.domain.PageBean;
import com.iteason.domain.Product;
import com.iteason.service.ProductService;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.InterningXmlVisitor;

public class FindProductByIdServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收数据
		String cid = request.getParameter("cid");
		String pid = request.getParameter("pid");
		String currentPageStr = request.getParameter("currentPage");
		int currentPage = 0 ;
		
		
		if(cid.trim().equals("")){
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		
		if(currentPageStr == null){
			currentPageStr = "1";
			currentPage = Integer.parseInt(currentPageStr);
		}else if(currentPageStr != null){
			currentPage = Integer.parseInt(currentPageStr);
		}
		
		
		int currentCount = 12;
		//封装数据
		//传递数据
		ProductService service = new ProductService();
		PageBean<Product> pageBean = null;
		try {
			 pageBean = service.findProductById(cid,currentPage,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//数据回显
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		
		
		
		//定义一个记录历史商品信息的集合
			List<Product> historyProductList = new ArrayList<Product>();
			
			//获得客户端携带名字叫pids的cookie
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for(Cookie cookie:cookies){
					if("pids".equals(cookie.getName())){
						String pids = cookie.getValue();//3-2-1
						String[] split = pids.split("-");
						for(String PID : split){
							Product pro = null;
							try {
								pro = service.findProductInfoById(PID);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							historyProductList.add(pro);
						}
					}
				}
			}
			
			//将历史记录的集合放到域中
			request.setAttribute("historyProductList", historyProductList);
			
			
			request.getRequestDispatcher("/product_list.jsp").forward(request, response);
			
		}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}