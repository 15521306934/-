package com.iteason.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.iteason.dao.AdminDao;
import com.iteason.domain.Category;
import com.iteason.domain.Product;
import com.iteason.utils.GetUUID;

public class AdminService {

	public List<Category> findAllCategory() throws SQLException {
		//  
		AdminDao dao = new AdminDao();
		List<Category> categoryList = dao.findAllCategory();
		return categoryList;
	}

	public void addCategory(String cname) throws SQLException {
		//addCategory
		AdminDao dao = new AdminDao();
		String cid = GetUUID.getUUID();
		dao.addCategory(cname,cid);
		
		
	}

	public void deleteCategory(String cid) throws SQLException {
		//
		AdminDao dao = new AdminDao();
		dao.deleteCategory(cid);
		
	}

	public void editCategory(String cid, String cname) throws SQLException {
		AdminDao dao = new AdminDao();
		dao.editCategory(cid,cname);
	}

	public List<Product> findAllProductUI() throws SQLException {
		//  
		AdminDao dao = new AdminDao();
		
		return dao.findAllProductUI();
	}

	public void deleteProductByPid(String pid) throws SQLException {
		// 
		AdminDao dao = new AdminDao();
		dao.deleteProductByPid(pid);
		
	}

	public Product findProductByPidToEdit(String pid) throws SQLException {
		AdminDao dao = new AdminDao();
		Product product = dao.findProductByPidToEdit(pid);
		return product;
	}

	public Map<String, Object> findCidByPid(String pid) throws SQLException {
		AdminDao dao = new AdminDao();
		
		return dao.findCidByPid(pid);
	}
	
}
