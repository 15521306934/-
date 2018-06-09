package com.iteason.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.iteason.domain.Category;
import com.iteason.domain.Product;
import com.iteason.utils.JDBCUtils_V3;

public class AdminDao {

	public List<Category> findAllCategory() throws SQLException {
		//  
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from category";
		List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}

	public void addCategory(String cname, String cid) throws SQLException {
		// addCategory
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "insert into category values(?,?)";
		runner.update(sql,cid,cname);
	}

	public void deleteCategory(String cid) throws SQLException {
		//  
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "delete from category where cid = ?";
		runner.update(sql, cid);
		
	}

	public void editCategory(String cid, String cname) throws SQLException {
		// 修改分类名
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		runner.update(sql,cname,cid);
	}

	public List<Product> findAllProductUI() throws SQLException {
		// 
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from product ";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	public void deleteProductByPid(String pid) throws SQLException {
		//删除商品
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "delete from product where pid = ?";
		runner.update(sql,pid);
		
	}

	public Product findProductByPidToEdit(String pid) throws SQLException {
		// 显示商品by pid
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from product where pid = ? ";
		return runner.query(sql, new BeanHandler<Product>(Product.class),pid);
		
	}

	public Map<String, Object> findCidByPid(String pid) throws SQLException {
		//通过pid找到cid
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select cid from product where pid = ? ";
		
		return runner.query(sql, new MapHandler(),pid);
	}

}
