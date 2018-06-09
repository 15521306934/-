package com.iteason.utils;

import java.util.UUID;

/**
 * 
 * @author Eason
 *	生成随机id的方法
 */
public class GetUUID {
	public static String getUUID(){
		String randomUUID = UUID.randomUUID().toString();
		return randomUUID;
	}
}
