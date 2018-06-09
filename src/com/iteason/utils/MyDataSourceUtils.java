package com.iteason.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;



public class MyDataSourceUtils {
		//1.定义四个常量
		private static String driver;
	    private static String url;
	    private static String username;
	    private static String password;
	    public static BasicDataSource dataSource = new BasicDataSource();
	    
	    static{
	    //2.调用ResourceBundle的getBundle方法绑定properties配置文件
	    ResourceBundle bundle = ResourceBundle.getBundle("db");
	    driver = bundle.getString("driver");
	    url = bundle.getString("url");
	    username = bundle.getString("username");
	    password = bundle.getString("password");
	    }
	
	//创建ThreadLocal,当前线程对象，将connection存入其中
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	//开启事务
	public static void startTransaction() throws SQLException{
		Connection conn = getCurrentConnection();
		conn.setAutoCommit(false);
	}
	
	//获得当前线程上绑定的conn
	public static Connection getCurrentConnection() throws SQLException{
		//从ThreadLocal寻找 当前线程是否有对应Connection
		Connection conn = tl.get();
		if(conn==null){
			//获得新的connection
			conn = getConnection();
			//将conn资源绑定到ThreadLocal（map）上
			tl.set(conn);
		}
		return conn;
	}
	
	public static Connection getConnection() throws SQLException{
		Connection conn =null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url
					,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	
	}

	//回滚事务
	public static void rollback() throws SQLException {
		getCurrentConnection().rollback();
	}

	//提交事务
	public static void commit() throws SQLException {
		Connection conn = getCurrentConnection();
		conn.commit();
		//将Connection从ThreadLocal中移除
		tl.remove();
		conn.close();
		
	}

}
