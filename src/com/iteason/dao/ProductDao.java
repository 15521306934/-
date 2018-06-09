package com.iteason.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iteason.domain.Category;
import com.iteason.domain.Order;
import com.iteason.domain.OrderItem;
import com.iteason.domain.Product;
import com.iteason.domain.User;
import com.iteason.utils.JDBCUtils_V3;
import com.iteason.utils.MyDataSourceUtils;

public class ProductDao {

	public List<Product> findHotProduct() throws SQLException {
		// 查找热门商品
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql  = "select * from product where is_hot = 1 limit 0,9";
		List<Product> hotProduct= runner.query(sql, new BeanListHandler<Product>(Product.class));
		return hotProduct;
	}

	public List<Product> findNewProduct() throws SQLException {
		// 查找最新商品
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql  = "select * from product order by pdate desc limit 0,9";//根据时间降序
		List<Product> hotProduct= runner.query(sql, new BeanListHandler<Product>(Product.class));
		return hotProduct;
	}

	public List<Category> findAllCategory() throws SQLException {
		// 查找分类商品
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql  = "select * from category";//根据时间降序
		List<Category> category= runner.query(sql, new BeanListHandler<Category>(Category.class));
		return category;
	}

	public int getCount(String cid) throws SQLException {
		//查询总条数
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select count(*) from product where cid = ?";
		Long query = (Long)runner.query(sql, new ScalarHandler(),cid);
		return query.intValue();
	}

	public List<Product> getProductByCategory(String cid, int index, int currentCount) throws SQLException {
		// 获得分页的数据
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from product where cid = ? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),cid,index,currentCount);
		return list;
	}

	public Product findProductInfoById(String pid) throws SQLException {
		// 
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from product where pid = ?";
		Product productInfo = runner.query(sql, new BeanHandler<Product>(Product.class),pid);
		return productInfo;
	}

	public void addOrders(Order order) throws SQLException {
		// 向order表插入数据
		//开启事务
		//order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
		//order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values (?,?,?,?,?,?,?,?) ";
		Connection conn = MyDataSourceUtils.getCurrentConnection();
		runner.update(conn, sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState()
				,order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}

	public void addOrderItem(Order order) throws SQLException {
		// 向orderitem表插入数据
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitem values (?,?,?,?,?) ";
		Connection conn = MyDataSourceUtils.getCurrentConnection();
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem item :orderItems){
			runner.update(conn,sql,item.getItemid(),item.getCount(),item.getSubtotal(),item.getProduct().getPid(),item.getOrder().getOid());
		}
	}

	public void updateOrder(Order order) throws SQLException {
		// 更新Order
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "update orders set address = ?,name=?,telephone=? where oid = ? ";
		runner.update(sql,order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}

	public List<Order> findAllOrders(String uid)  {
		// 订单查询
		 QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from orders where uid = ?";
		List<Order> query = null;
		try {
			query = runner.query(sql, new BeanListHandler<Order>(Order.class),uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(query); 
		return query;
	}

	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		// 通过oid查orderitem
		QueryRunner runner = new QueryRunner(JDBCUtils_V3.getDataSource());
		String sql = "select * from orderitem i,product p where i.pid=p.pid and i.oid = ? ";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		return mapList;
	}
	

}
