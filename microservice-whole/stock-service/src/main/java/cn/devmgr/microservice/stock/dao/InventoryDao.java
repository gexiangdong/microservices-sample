package cn.devmgr.microservice.stock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.devmgr.microservice.stock.domain.Inventory;

public interface InventoryDao {

    @Select("select * from inventory where id=#{id}")
    public Inventory getInventoryById(@Param(value = "id") int id);

    @Select("select * from innventory")
    public List<Inventory> queryInventories();
}
