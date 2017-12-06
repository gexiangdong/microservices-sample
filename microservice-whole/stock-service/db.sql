/** ROLES and Permissions **/

/** 权限表*/
create table auth_permission(
	permission  varchar(40) primary key,
	name varchar(50) not null,
	ord int not null
);

create table auth_role_permission(
	role varchar(40) not null,
	permission varchar(40) not null,
	primary key(role, permission)
);

/***---- sample data -----**/
insert into auth_permission(permission, name, ord) values ('queryInventory', '查询商品', 1);
insert into auth_permission(permission, name, ord) values ('modifyInventory', '修改商品', 2);
insert into auth_permission(permission, name, ord) values ('inventoryReport', '查看商品统计表', 3);

/*#系统管理员组*/
insert into auth_role_permission(role, permission) values('admins', 'queryInventory');
insert into auth_role_permission(role, permission) values('admins', 'modifyInventory');
insert into auth_role_permission(role, permission) values('admins', 'inventoryReport');
/*#销售组	*/
insert into auth_role_permission(role, permission) values('sales', 'queryInventory');
insert into auth_role_permission(role, permission) values('sales', 'inventoryReport');
