package cn.devmgr.common.security.impl;

public class Permission {
    private String id, name;

    public Permission(String id, String name) {
	this.id = id;
	this.name = name;
    }

    public Permission() {

    }

    public String getId() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public void setId(String id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }
}
