create table order_main(
    id serial primary key,
    orderDate timestamp not null,
    consignee varchar(20) not null,
    phone varchar(20) not null,
    province varchar(50) null,
    city varchar(50) null,
    district varchar(50) null,
    address varchar(50) null,
    status int default 0
);

create table order_detail(
    id serial primary key,
    orderId int not null,
    inventoryId int not null,
    inventoryName varchar(100) not null,
    num int not null,
    price float not null,
    supplyPrice float not null
);


insert into order_main(orderDate, consignee, phone, province, city, district, address, status)
    values('2017/4/28 12:13:14', '郝尤乾', '13501234567', '上海', '上海', '徐汇', '漕宝路22222号', 0);
insert into order_detail(orderId, inventoryId, inventoryName, num, price, supplyPrice)
    values(1, 12, 'iphone手机模型', 1, 10000, 122.80);
insert into order_detail(orderId, inventoryId, inventoryName, num, price, supplyPrice)
    values(1, 13, 'USB充电插座', 1, 150, 12.80);



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
insert into auth_permission(permission, name, ord) values ('queryOrder', '查询订单', 1);
insert into auth_permission(permission, name, ord) values ('modifyOrder', '修改订单', 2);
insert into auth_permission(permission, name, ord) values ('orderReport', '查看订单统计表', 3);

/*#系统管理员组*/
insert into auth_role_permission(role, permission) values('admins', 'queryOrder');
insert into auth_role_permission(role, permission) values('admins', 'modifyOrder');
insert into auth_role_permission(role, permission) values('admins', 'orderReport');
/*#销售组  */
insert into auth_role_permission(role, permission) values('sales', 'queryOrder');
insert into auth_role_permission(role, permission) values('sales', 'orderReport');
/*#经理组*/
insert into auth_role_permission(role, permission) values('managers', 'queryOrder');
insert into auth_role_permission(role, permission) values('managers', 'modifyOrder');
insert into auth_role_permission(role, permission) values('managers', 'orderReport');
