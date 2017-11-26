# 简单电商前后台例子

前台通过vue，后台主要通过spring-cloud实现。每个例子项目都可以通过mvn spring-boot:run来运行

* 普通oauth2 token
    * [oauth2-server](./oauth2-server) oauth2 server  需要postgresql
    * [security-mvc](./security-mvc) 使用上述oauth2 server的资源服务器
    * [security-rest-jersey](./security-rest-jersey) 使用上述oauth2 server的spring security, jersey rest api server 
* JWT
    * [oauth2-server-jwt](./oauth2-server-jwt) oauth2 server jwt 含自定义token条目，需要postgresql
    * [security-mvc-jwt](./security-mvc-jwt) 使用上述oauth2 server jwt的资源服务器，含解析上述自定义token内容的部分
* API-Gateway
    * [api-gateway-zuul](./api-gateway-zuul) 使用zuul实现API网关的例子
    
    
    