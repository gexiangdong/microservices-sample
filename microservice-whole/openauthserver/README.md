README
===================

Spring oauth2 server, 配合 [security-mvc](../../../security-mvc) 或 [security-rest-jersey](../../../security-rest-jersey)查看运行示例。


使用postgresql数据库，数据库名为：oauth2server。建表和示例数据参照[schema-and-sample-data.sql](schema-and-sample-data.sql)。


password类型，获取token测试：
如果rs1无密码：
$curl rs1@localhost:8008/authserver/oauth/token -d grant_type=password -d username=admin -d password=admpwd
或
$curl localhost:8008/authserver/oauth/token -d grant_type=password -d username=admin -d password=admpwd -d client_id=rs1

如果rs1有密码，密码为secret：
$curl rs1:secret@localhost:8008/authserver/oauth/token -d grant_type=password -d username=admin -d password=admpwd
或
$curl localhost:8888/authserver/oauth/token -d grant_type=password -d username=admin -d password=admpwd -d client_id=rs1  -d client_secret=secret


curl -i -X OPTIONS rs1@localhost:8008/authserver/oauth/token
curl -i -X OPTIONS localhost:8008/authserver/oauth/token -d client_id=rs1

##
用password授权类型时，需要注意content-type只支持pplication/x-www-form-urlencoded，使用username, password, client_id, client_secret, grant_type五个参数，可以支持浏览器跨域AJAX



