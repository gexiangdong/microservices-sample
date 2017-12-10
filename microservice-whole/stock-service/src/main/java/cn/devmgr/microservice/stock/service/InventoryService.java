package cn.devmgr.microservice.stock.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.devmgr.microservice.stock.dao.InventoryDao;
import cn.devmgr.microservice.stock.domain.Inventory;

@Service
public class InventoryService {
    private final Log log = LogFactory.getLog(Inventory.class);
    
    @Autowired
    private InventoryDao inventoryDao;
    
    public Inventory getInventory(int id) {
        return inventoryDao.getInventoryById(id);
    }
    
    public List<Inventory> getAllInventories(){
        if(log.isTraceEnabled()) {
            log.trace("getAllInventories() was called.");
        }
        return inventoryDao.queryInventories();
    }
}
