package com.iteason.service;

import java.sql.SQLException;

import com.iteason.dao.AdminProductDao;
import com.iteason.domain.Product;

public class AdminProductService extends ProductService {

	public void addProduct(Product product) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		dao.addProduct(product);
	}

}
