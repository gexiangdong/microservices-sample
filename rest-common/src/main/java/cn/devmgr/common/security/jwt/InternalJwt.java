package cn.devmgr.common.security.jwt;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import cn.devmgr.common.security.domain.SecurityUser;

@Service
public class InternalJwt {
    private final Log log = LogFactory.getLog(InternalJwt.class);
    
    @Value("${security.oauth2.resource.jwt.internalsecret}")
    private String secret;
    
    @Value("${spring.application.name}")
    private String appName;
    
    private Algorithm algorithm;
    
    @PostConstruct
    public void init() throws IllegalArgumentException, UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(secret);
    }
    
    public String createInternalJwt() {
        JWTCreator.Builder builder = JWT.create();
        int userId = 0;
     
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null) {
                SecurityUser su = (SecurityUser)  authentication.getPrincipal();
                userId = su.getId();
            }
        }catch(Exception e) {
            if(log.isWarnEnabled()) {
                log.warn("error occured. ", e);
            }
        }
        String token = builder.withClaim("app_id", appName).withClaim("user_id", userId).sign(algorithm);
        return token;
    }
    
    public Object vertify(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        System.out.println( jwt.getClaim("appId").asString() );
        
        return null;
    }
    

}
