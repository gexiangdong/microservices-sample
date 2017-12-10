package cn.devmgr.microservice.order.domain;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class OrderItem  implements Serializable{
	private static final long serialVersionUID = -3975898975212474960L;
	
	@NotNull
	private int inventoryId;
	private String inventoryName;

	@DecimalMin(value = "1", message = "购买数量不可以少于1个")
    @DecimalMax(value = "50", message = "购买数量不可以多于50个")
	private int num;
    
	/**
	 * 售价
	 */
    @Null
	private double price;
    
    /**
     * 供货价，采购价，进价
     */
    @Null
	private double supplyPrice;

    
    public int getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(double supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	
}
