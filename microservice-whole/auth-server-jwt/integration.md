
配置spring-boot项目使用systemd服务自动启动

配置文件
[Unit]  
Description=auth2-server
After=syslog.target  
  
[Service]  
ExecStart=/usr/bin/java -jar -Xms256m -Xmx1G  -Dlog.level.console=warn -Dlog.level.ysb=info /web/webapps/open-auth-server-jwt-1.0.jar --spring.profiles.active=production 
SuccessExitStatus=143  
  
[Install]  
WantedBy=multi-user.target

配置文件放入/lib/systemd/system/auth2-server.service
然后执行：
#systemctl enable auth2-server.service
#systemctl daemon-reload

之后可以使用service auth2-server start启动

