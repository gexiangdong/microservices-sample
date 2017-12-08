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


/** -----------------------------------------------------  **/
create table inventory(
	id serial primary key,
	name varchar(50) not null,
	price real not null,
	logo varchar(100) not null,
	pictures varchar(100)[] null,
	status int not null
);

create table warehouse(
	id serial primary key,
	name varchar(30) not null,
	type int not null
);

create table stock(
	warehouseId int not null,
	inventoryId int not null,
	number int not null
);

insert into inventory (name, price, logo, pictures, status) values('iPhone X', 8999, 'iphon1.jpg', '{"iphone10.jpg", "iphone20.jpg"}', 0);
insert into inventory (name, price, logo, pictures, status) values('Mac Book Pro', 18999, 'macpro.jpg', '{"macpro1.jpg", "macpro2.jpg", "macpro3.jpg"}', 0);

