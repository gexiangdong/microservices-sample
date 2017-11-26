package cn.devmgr.microservice.order.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.devmgr.microservice.order.domain.Order;


public interface OrderDao {

	@Select("select id, orderDate, consignee from order_main where id=#{id}")
	public Order getOrderLiteById(@Param(value = "id") int id);

	
	public int insertOrder(@Param(value = "order") Order order);
	
	
	public Order getOrderById(int id);
}
