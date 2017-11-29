package cn.devmgr.microservice.order.endpoint;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import cn.devmgr.microservice.order.domain.Order;
import cn.devmgr.microservice.order.service.OrderService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="Authorization")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class OrderEndpoint {
    private static final Log log = LogFactory.getLog(OrderEndpoint.class);
    
    @Autowired
    private OrderService orderService;
    
    
    @Path("/{id:\\d+}/")
    @GET
    // @RolesAllowed({ "admin", "user" })
    //@PostAuthorize("")
    public Order queryOrder(@PathParam("id") int id, @Context SecurityContext sc){
        Order order = orderService.getOneOrder(1);
        if(sc != null) {
            log.trace("UserPrincipal: " + sc.getUserPrincipal());
            if(sc.getUserPrincipal() != null) {
                log.trace("Name:" + sc.getUserPrincipal().getName());
            }
            
        }
        return order;
    }
}
