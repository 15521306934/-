package com.iteason.service;
import com.iteason.dao.UserDao
;
import java.sql.SQLException;

import com.iteason.domain.User;

public class UserService {

	public boolean register(User user) throws SQLException {
		
		UserDao dao = new UserDao();
		int rows =  dao.register(user);
		return rows>0?true:false;
	}

	public void activeCode(String activeCode) throws SQLException {
		// 获得激活码并传递给dao层进行查询比对
		UserDao dao = new UserDao();
		dao.activeCode(activeCode);
		
	}

	public boolean checkUsername(String username) throws SQLException {
		// 校验用户名是否存在
		UserDao dao = new UserDao();
		long rows = dao.checkUsername(username);
		return rows>0?true:false;//大于0表示存在这个username
	}

	public User loginTest(String username, String password) throws SQLException {
		//检验用户登陆账号和密码是否正确
		UserDao dao = new UserDao();
		User user = dao.loginTest(username,password);
		return user;
		
	}

}
