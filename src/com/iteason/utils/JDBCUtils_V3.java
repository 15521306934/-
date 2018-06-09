package com.iteason.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * @description 建立数据源方法,用于apac commons QueryRunner（）进行绑定数据源
 * @author Eason Pang
 * @date 2018-03-10
 * @version V3.0
 */
public class JDBCUtils_V3 {
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
    
    
    //获得connection对象
	public static Connection getConnection(){
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
	
	
	//释放资源
	public static void release(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	static {
		//对连接池对象 进行基本的配置
		dataSource.setDriverClassName(driver); // 这是要连接的数据库的驱动
		dataSource.setUrl(url); //指定要连接的数据库地址
		dataSource.setUsername(username); //指定要连接数据的用户名
		dataSource.setPassword(password); //指定要连接数据的密码
		
	}
	
	//获得连接池对象
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	
}
