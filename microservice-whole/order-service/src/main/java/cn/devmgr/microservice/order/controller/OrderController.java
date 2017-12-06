package cn.devmgr.microservice.order.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.devmgr.microservice.order.domain.Order;
import cn.devmgr.microservice.order.service.OrderService;


@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="Authorization")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Log log = LogFactory.getLog(OrderController.class);
    
    @Autowired
    private OrderService orderService;
    
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Order queryOrder(@PathVariable("id")  int id){
        Order order = orderService.getOneOrder(1);

        return order;
    }
    
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Order order, SecurityContext sc){
        if(log.isTraceEnabled()) {
            log.trace("order=" + order + "; " + order.getOrderItems());
        }
        if(sc != null) {
            log.trace("UserPrincipal: " + sc.getAuthentication());
            if(sc.getAuthentication() != null) {
                log.trace("Name:" + sc.getAuthentication().getName());
            }
            
        }
        return order;
    }
}
