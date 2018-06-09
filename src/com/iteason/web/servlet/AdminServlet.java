package com.iteason.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.iteason.domain.Category;
import com.iteason.domain.Product;
import com.iteason.service.AdminService;

public class AdminServlet extends BaseServlet {

	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		//提供一个List<Category>转换成json字符串
		AdminService service = new AdminService();
		List<Category> categoryList = service.findAllCategory();
		
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		
		response.setContentType("text/html;charset=UTF-8");
	
		//将json串传输给请求页
		response.getWriter().write(json);
	}
	
public void findAllCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//为category/list.jsp显示分类数据
		AdminService service = new AdminService();
		List<Category> categoryList = service.findAllCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}

public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	//为category/list.jsp添加分类
	String cname = request.getParameter("cname");
	AdminService service = new AdminService();
	service.addCategory(cname);
	request.getRequestDispatcher("/admin?method=findAllCategoryUI").forward(request, response);
}

public void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	//删除分类
	String cid = request.getParameter("cid");
	AdminService service = new AdminService();
	service.deleteCategory(cid);
	request.getRequestDispatcher("/admin?method=findAllCategoryUI").forward(request, response);
}

public void editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	//修改分类
	String cid = request.getParameter("cid");
	String cname = request.getParameter("cname");
	AdminService service = new AdminService();
	service.editCategory(cid,cname);
	request.getRequestDispatcher("/admin?method=findAllCategoryUI").forward(request, response);
}

public void findAllProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	//显示商品
	AdminService service = new AdminService();
	List<Product> productList = service.findAllProductUI();
	request.setAttribute("productList", productList);
	request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
}

public void deleteProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	//删除商品
	String pid = request.getParameter("pid");
	AdminService service = new AdminService();
	service.deleteProductByPid(pid);
	request.getRequestDispatcher("/admin?method=findAllProductUI").forward(request, response);
}

public void findProductByPidToEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	//点击编辑商品后跳转到此，准备数据给edit页面
	String pid = request.getParameter("pid");
	AdminService service = new AdminService();
	Product product = service.findProductByPidToEdit(pid);
	Map<String, Object> map = service.findCidByPid(pid);
	String cid = (String)map.get("cid");
	request.setAttribute("product", product);
	request.setAttribute("cid", cid);
	request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
}


}