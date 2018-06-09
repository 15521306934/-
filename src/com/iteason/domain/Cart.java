package com.iteason.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
	//该购物车存储的n个购物项
	private Map<String,CartItem> cartItems = new HashMap<String,CartItem>();
	
	//购物车金额总计
	private double totalSum;

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}
	
	
}
