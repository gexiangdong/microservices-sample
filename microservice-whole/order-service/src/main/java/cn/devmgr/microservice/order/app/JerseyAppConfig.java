package cn.devmgr.microservice.order.app;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.stereotype.Component;

import cn.devmgr.common.rest.app.BaseJerseyAppConfig;

@Component
@ApplicationPath("/rest")
public class JerseyAppConfig extends BaseJerseyAppConfig {

	public JerseyAppConfig() {
		//设置哪些包下面的类会用于REST API
		packages("cn.devmgr.microservice.order.endpoint");
		//register(SampleResource.class);
		register(RolesAllowedDynamicFeature.class); //这个会使得PermitAll, RolesRequired(""), DenyAll等注解起作用


	}
}
