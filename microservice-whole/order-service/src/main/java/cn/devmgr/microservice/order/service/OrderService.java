package cn.devmgr.microservice.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import cn.devmgr.microservice.order.dao.OrderDao;
import cn.devmgr.microservice.order.domain.ConsigneeAddress;
import cn.devmgr.microservice.order.domain.Order;
import cn.devmgr.microservice.order.domain.OrderItem;

// 此处使用的是spring cache annotation，也可以使用JSR-107的cache注解；
// CacheConfig设置了cacheNames后，此类的方法中的CacheEvict/CachePut/Cacheable等则不在需要设置名字，仅仅设置key（自动计算key）即可
@CacheConfig(cacheNames="order")
@Service
public class OrderService {
	private static final Log log = LogFactory.getLog(OrderService.class);

	@Autowired
	private OrderDao orderDao;
	
    @Autowired
    private LoadBalancerClient loadBalancer;
	
	@Transactional
	public boolean createNewOrder(Order order){
	    if (order.getOrderItems() == null || order.getOrderItems().size() == 0){
	        if(log.isWarnEnabled()) {
	            log.warn("订单必须有明细条目。");
	        }
	        return false; 
	    }
	    
	    ServiceInstance instance = loadBalancer.choose("stock-service");
	    if(log.isTraceEnabled()) {
	        log.trace("stock-service URI:" + instance.getUri() + " getMetadata:" + instance.getMetadata());
	    }

	    RestTemplate restTemplate = new RestTemplate();
	    for(OrderItem item : order.getOrderItems()) {
	        int inventoryId = item.getInventoryId();
	        Map<String, Object> map = restTemplate.getForObject(instance.getUri() + "/stockservice/inventories/" + inventoryId, Map.class);
	        if(log.isTraceEnabled()) {
	            log.trace("#" + inventoryId + "  " + map);
	        }
	    }
		// orderDao.insertOrder(order);
		return true;
	}
	
	
	/**
	 * key的表达式设置可以参照 Spring Cache SpEL (http://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/html/cache.html#cache-spel-context)
	 */
	@CachePut(key="#result.id")
	@Transactional
	public Order getOneOrder(int orderId){

		Order newOrder = new Order();
		//newOrder.setConsignee("郝尤乾");
		newOrder.setOrderDate(new Date());
		newOrder.setConsigneeAddress(new ConsigneeAddress("张三", "13612348888", "江苏省", "南京市", "xx区", "xyz路20号"));
		List<OrderItem> list = new ArrayList<OrderItem>();
		Random rnd = new Random();
		int max = 1 + rnd.nextInt(5);
		for(int i=0; i<max; i++){
			OrderItem item = new OrderItem();
			item.setInventoryId((rnd.nextInt() + 1000) % 1000);
			item.setInventoryName("苹果" + (rnd.nextInt(20) + 1) + "代");
			item.setNum(rnd.nextInt(10) + 1);
			item.setPrice(rnd.nextInt(1000));
			item.setSupplyPrice(rnd.nextDouble() * 100);
			list.add(item);
		}
		newOrder.setOrderItems(list);
		
		int result = orderDao.insertOrder(newOrder);
		if(log.isTraceEnabled()){
			log.trace("插入的新记录" + newOrder.getId() + "; sql return " + result);
		}
		return newOrder;
	}
	
	@Cacheable(key="#id")
	public Order getOrder(int id){
		return orderDao.getOrderById(id);
	}
}
