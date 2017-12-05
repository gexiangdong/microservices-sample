package cn.devmgr.microservice.order.domain;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class OrderItem  implements Serializable{
	private static final long serialVersionUID = -3975898975212474960L;
	
	@NotNull
	private String giftId;
	private String giftName;

	@DecimalMin(value = "2", message = "购买数量不可以少于2个")
    @DecimalMax(value = "5", message = "购买数量不可以多于5个")
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

    
    public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
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
