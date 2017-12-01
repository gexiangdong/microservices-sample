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

