package cn.devmgr.microservice.stock.domain;



public class Inventory{

	private String name;
	private int id;


	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
}