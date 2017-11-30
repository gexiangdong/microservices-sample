package cn.devmgr.microservice.stock.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin; 

import cn.devmgr.microservice.stock.domain.Inventory;


@RestController
public class InventoryController {
    private final Log log = LogFactory.getLog(InventoryController.class);

    @RequestMapping("/inventories")
    public List<Inventory> listInventories(@RequestParam(value="name", defaultValue="World") String name,
    		Principal principal) {
        List<Inventory> list = new ArrayList<Inventory>();
        for(int i=0; i<5; i++){
            	Inventory inventory = new Inventory();
            	inventory.setId(i * 10);
            	inventory.setName("Inventory #" + i + (principal == null ? " NULL" : principal.getName()));
            	list.add(inventory);
        }
        if(log.isTraceEnabled()) {
            if(principal == null) {
                log.trace("principal is null");
            }
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for(SimpleGrantedAuthority sga : authorities) {
                log.trace("SimpleGrantedAuthority: " + sga.getAuthority());
            }
        }
        return list;
    }
}