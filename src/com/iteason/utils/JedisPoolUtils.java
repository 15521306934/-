package com.iteason.utils;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisPoolUtils_V1
 * @author Eason
 * 工具类，用于获得jedis对象
 * time:2018-04-07
 */
public class JedisPoolUtils {
	//定义ip地址和端口号
	private static String host;
	private static int port;
	
	//通过配置文件获取HOST和PORT
	static{
		ResourceBundle bundle = ResourceBundle.getBundle("jd");
		host = bundle.getString("host");
		//将字符串转换成int类型
		String portStr = bundle.getString("port");
		port = Integer.parseInt(portStr);
	}
	//获取单个jedis对象
	public static Jedis getJedis(){//静态方法，方便直接类名调用
		Jedis jedis = new Jedis(host, port);
		return jedis;
	}
	
	//获取jedis池，通过池获取jedis对象
	public static Jedis getJedisByPoll(){
				//0、创建池子的配置对象
				JedisPoolConfig poolConfig = new JedisPoolConfig();
				poolConfig.setMaxIdle(30);//最大闲置个数
				poolConfig.setMaxIdle(10);//最小闲置个数
				poolConfig.setMaxTotal(50);//最大连接数
				//1、创建一个redis的连接池
				JedisPool pool = new JedisPool(poolConfig,host, port);
				//2、从池子中获取资源
				Jedis jedis = pool.getResource();
		return jedis;
	}
	
}
