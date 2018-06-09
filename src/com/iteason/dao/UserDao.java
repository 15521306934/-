package com.iteason.dao;

import java.sql.SQLException;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iteason.domain.User;
import com.iteason.utils.JDBCUtils_V3;

public class UserDao {

	public int register(User user) throws SQLException {
		//进行注册
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
		int rows = runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode());
		return rows;
	}

	public void activeCode(String activeCode) throws SQLException {
		// 将两者存储的验证码进行比对并修改激活状态码
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "update user set state = 1 where code = ?";
		runner.update(sql,activeCode);
		
	}

	public long checkUsername(String username) throws SQLException {
		// 校验用户名是否存在
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select count(*) from user where username=?";
		Long rows = (Long) runner.query(sql,new ScalarHandler(), username);
		return rows;
	}

	public User loginTest(String username, String password) throws SQLException {
		// 查询是否存在此账号名和密码
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		User user = runner.query(sql, new BeanHandler<User>(User.class),username,password);
		return user;
	}
	
}
