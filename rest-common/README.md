# 教程(Spring REST security)公共基础库

在Spring REST中使用spring security（JWT) 的类，此仓库内的例子大部分都用到了这个jar。

主要包括以下内容：
授权与身份验证

用法：
在application类中增加注解，特别注意别忘了增加自己的所在的包
````Java
@ComponentScan(basePackages= {"cn.devmgr.common.security", "自身需要的package"})
````
