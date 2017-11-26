package cn.devmgr.common.rest.app;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import cn.devmgr.common.rest.exception.GenericExceptionMapper;
import cn.devmgr.common.rest.exception.JsonMappingExceptionMapper;
import cn.devmgr.common.rest.exception.NullPointerExceptionMapper;
import cn.devmgr.common.rest.filter.AuthenticationFilter;


public class BaseJerseyAppConfig extends ResourceConfig {

	public BaseJerseyAppConfig() {
		//设置哪些包下面的类会用于REST API
		//packages("cn.devmgr.sample.rest.resource");
		register(JacksonJsonProvider.class);
		register(MultiPartFeature.class);
		
		register(RolesAllowedDynamicFeature.class); //这个会使得PermitAll, RolesRequired(""), DenyAll等注解起作用

		//登记身份验证过滤器，类上增加Provider注解，这里不需要登记(使用spring+jersey后，Provider注解不起作用了，需要手工登记)
		//Provider不起作用是jersey < v2.5 bug; https://java.net/jira/browse/JERSEY-2175
		//this should be helpful  http://stackoverflow.com/questions/25905941/jersey-global-exceptionhandler-doesnt-work
		register(AuthenticationFilter.class);
		//登记异常转换用类
		register(NullPointerExceptionMapper.class);
		register(JsonMappingExceptionMapper.class);
		register(GenericExceptionMapper.class);
	}
}
