/* standard Schema for PostgreSQL to use with JdbcTokenStore (Spring Security OAuth2) */
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);

create table oauth_code (
  code VARCHAR(256), authentication bytea
);

create table oauth_approvals (
  userId VARCHAR(256),
  clientId VARCHAR(256),
  scope VARCHAR(256),
  status VARCHAR(10),
  expiresAt TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

/* 本例子增加的用户、组及权限部分 */
create table users(
    id char(10) primary key,
    username varchar(50) not null,
    name varchar(50) null,
    password varchar(100) null,
    head_image varchar(50) null,
    email varchar(100) null,
    phone varchar(20) null,
    position varchar(20) null,
    department varchar(20) null,
    enabled boolean default true,
    expireDate date null,
    create_date timestamp not null default now(),
    last_modify_at timestamp not null default now()
);

create table groups (
    id varchar(20) primary key,
    group_name varchar(50) not null
);

/**
#对组进行授权放到了各个微服务模块内，不再需要这个表
create table group_authorities (
    group_id int not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);
*/

create table group_members (
    id serial primary key,
    user_id char(10) not null,
    group_id varchar(20) not null,
    constraint fk_group_members_group foreign key(group_id) references groups(id),
    constraint fk_group_members_user foreign key(user_id) references users(id)
);

/* ------- sample date ------*/
/** 应用， 自动授权，授权后重定向到http://127.0.0.1?key=rs1    **/
insert into oauth_client_details 
        (client_id, resource_ids, client_secret, scope, 
            authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, 
            refresh_token_validity, additional_information, autoapprove) 
values(
    'rs1', 'oauth2-resource', 'read, trust', 'authorization_code,password,refresh_token',
    '', 'ROLE_CLIENT', '', null,
    null, '{}', 'read,trust'
);
/** 用户 用户名 admin, 密码admpwd (admpwd的密文是$2a$10$3JzxlSNU6ih.w6TUZJEmO.Icxga7i9bCbofNbR3Qd2AWrNK6nswfa) */
insert into users(id, username, name, password) values('00-00-0001', 'admin', 'System Administrator', '$2a$10$3JzxlSNU6ih.w6TUZJEmO.Icxga7i9bCbofNbR3Qd2AWrNK6nswfa');
/** asales密码是thepwd */
insert into users(id, username, name, password) values('00-01-0002', 'asales', '销售员甲', '$2a$10$e48FCAP2KrXXqXXt/6f/W.i/j7EcULM3LW7mHrQYFarMTCQDSsvl.');
insert into groups(id, group_name) values('admins', '管理员');
insert into groups(id, group_name) values('managers', '经理组');
insert into groups(id, group_name) values('sales', '销售组');
insert into group_members(user_id, group_id) select id, 'admins' from users u where u.username='admin';
insert into group_members(user_id, group_id) select id, 'sales' from users u where u.username='asales';
    