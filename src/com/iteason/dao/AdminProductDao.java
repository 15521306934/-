package com.iteason.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.iteason.domain.Category;
import com.iteason.domain.Product;
import com.iteason.utils.JDBCUtils_V3;

public class AdminProductDao {
	/*private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private String pimage;
	private String pdate;
	private int is_hot;
	private String pdesc;
	private int pflag;
	private Category category;*/

	public void addProduct(Product product) throws SQLException {
		//添加商品
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql,product.getPid(),product.getPname(),
				product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCategory().getCid());	
		
	}
	
	
}
