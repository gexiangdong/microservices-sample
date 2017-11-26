package cn.devmgr.sample.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        // new SpringApplicationBuilder(Application.class).web(true).run(args);

    }

}