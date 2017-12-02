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

insert into auth_permission(permission, name, ord) values('select-inventory', '商品查询', 1);
insert into auth_permission(permission, name, ord) values('modify-inventory', '商品修改', 2);
insert into auth_permission(permission, name, ord) values('select-order', '订单查询', 3);

insert into auth_role_permission(role, permission) values('user', 'select-order');
insert into auth_role_permission(role, permission) values('user', 'update-order');
