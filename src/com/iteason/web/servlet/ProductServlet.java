package com.iteason.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.iteason.domain.Category;
import com.iteason.domain.PageBean;
import com.iteason.domain.Product;
import com.iteason.service.ProductService;

public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//获得需要调用的方法名
		/*String methodName = request.getParameter("method");
		if(methodName.equals("findIndexProduct")){
			findIndexProduct(request,response);
		}else if(methodName.equals("")){
			
		}*/
		
		//设置全局编码表
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/charset=UTF-8");
		
		//采用反射机制调用方法
		String methodName = request.getParameter("method");
		//获取字节码对象
		Class<? extends ProductServlet> clazz = this.getClass();
		Method method = null;
		try {
			//通过字节码对象获得方法---ProductServlet.class，传递参数的字节码对象
			method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//执行当前对象的方法，传递实参
			method.invoke(this, request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//用这个servlet作为一个商品模块，进行模块开发
	
	//显示首页商品的功能
		public void findIndexProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
		//显示分类
		public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		
		//显示分类商品
		public void findProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			//接收数据
			String cid = request.getParameter("cid");
			String pid = request.getParameter("pid");
			String currentPageStr = request.getParameter("currentPage");
			int currentPage = 0 ;
			
			
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
				
				//将历史记录的集合放到域中
				request.setAttribute("historyProductList", historyProductList);
				
				
				request.getRequestDispatcher("/product_list.jsp").forward(request, response);
				
			}

}
}
