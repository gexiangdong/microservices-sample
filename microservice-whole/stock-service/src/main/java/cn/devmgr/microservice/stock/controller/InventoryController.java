package cn.devmgr.microservice.stock.controller;

import java.util.List;
import java.util.Collection;
import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.devmgr.microservice.stock.domain.Inventory;
import cn.devmgr.microservice.stock.service.InventoryService;


@RestController
@RequestMapping("/inventories")
public class InventoryController {
    private final Log log = LogFactory.getLog(InventoryController.class);
    
    @Autowired
    private InventoryService inventoryService;
    

    /**
     * @PreAuthorize("hasRole('XYZ')") 和 @PreAuthorize("hasAuthority('ROLE_XYZ')") 等效；
     * 注意如果方法访问URL和类相同, RequestMapping不写value属性，如果写了value="/"反而会访问不到
     */
    @PreAuthorize("hasRole('admins') or hasAuthority('queryInventory')")
    @RequestMapping(method = RequestMethod.GET)
    public List<Inventory> listInventories(@RequestParam(value="name", defaultValue="World") String name,
    		Principal principal) {
        List<Inventory> list = inventoryService.getAllInventories();
        if(log.isTraceEnabled()) {
            log.trace("query db return " + (list == null ? "NULL" : list.size() + " records."));
        }
        
        if(log.isTraceEnabled()) {
            if(principal == null) {
                log.trace("principal is null");
            }
            @SuppressWarnings("unchecked")
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for(SimpleGrantedAuthority sga : authorities) {
                log.trace("SimpleGrantedAuthority: " + sga.getAuthority());
            }
        }
        return list;
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Inventory queryInventory(@PathVariable("id")  int id){
        if(log.isTraceEnabled()) {
            log.trace("query inventory #" + id);
        }
        return inventoryService.getInventory(id);
    }
}