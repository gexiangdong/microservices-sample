package cn.devmgr.microservice.stock.controller;

import java.util.List;
import java.util.ArrayList;
import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.bind.annotation.CrossOrigin; 

import cn.devmgr.microservice.stock.domain.Inventory;


@CrossOrigin(allowedHeaders="Authorization")
@RestController
public class InventoryController {


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
        return list;
    }
}