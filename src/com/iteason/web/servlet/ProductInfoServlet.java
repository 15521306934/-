package com.iteason.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iteason.domain.Product;
import com.iteason.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		ProductService service = new ProductService();
		Product productInfo = null;
		try {
			productInfo = service.findProductInfoById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("productInfo", productInfo);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		
		
		
		//cookie 历史记录部分
				//获得名为pids的cookie串
				String pids = pid;
				Cookie[] cookies = request.getCookies();
					for(Cookie cookie :cookies){
						if("pids".equals(cookie.getName())){
							//获得到的这个cookie就是名为pids的cookie
							//拼接pids字符串
							pids = cookie.getValue();
							//除去-
							String[] splitPids = pids.split("-");
							//将splitPids字符串转换成list集合
							List<String> asList = Arrays.asList(splitPids);
							//将List转换成linkedList，方便使用方法
							LinkedList<String> list = new LinkedList<String>(asList);
							//查询是否存在当前商品的pid
							if(list.contains(pids)){
								//存在，删去pid，并将pid放到最前面
								list.remove(pids);
								list.addFirst(pids);
							}else{
								//不存在，直接加到list集合前面
								list.addFirst(pids);
								
							}
							//将[3,1,2]转成3-1-2字符串
							StringBuffer sb = new StringBuffer();
							for(int i=0;i<list.size()&&i<7;i++){//StringBuffer中限制在list的前七个数据
								sb.append(list.get(i));
								sb.append("-");//3-1-2-
							}
							//去掉3-1-2-后的-
							pids = sb.substring(0, sb.length()-1);
							
						}
					}
					
							//创建cookie
							Cookie cookie_pids = new Cookie("pids", pids);
							//将cookie返回给客户端
							response.addCookie(cookie_pids);
							
							request.getRequestDispatcher("/product_info.jsp").forward(request, response);
			}	
		
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}