package cn.devmgr.microservice.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * 注意@ComponentScan加入后，会仅仅扫描指定的包，不包含当前包；如果需要，必须指定当前包，否则不会扫描当前包，
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages= {"cn.devmgr.common.security", "cn.devmgr.microservice.stock"})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        // new SpringApplicationBuilder(Application.class).web(true).run(args);

    }

}