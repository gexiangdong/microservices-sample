package cn.devmgr.microservice.oauth.jwt.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cn.devmgr.microservice.oauth.jwt.service.SecurityUserDetailService;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Log log = LogFactory.getLog(SecurityConfig.class);


    @Autowired
    private SecurityUserDetailService userDetailService;
    
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        if(log.isTraceEnabled()) {
            log.trace("creating passwordEncoder...");
        }
//        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder加密更安全
        return new Md5PasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
     @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.authenticationProvider(authProvider());
         //auth.userDetailsService(userDetailService); //如果不需要passwordEncoder可以这样写
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.requestMatchers()
            .antMatchers("/login", "/oauth/authorize")
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin().loginPage("/login")
            .permitAll();
        
    } // @formatter:on

}
