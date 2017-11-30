package cn.devmgr.common.security.jwt;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import cn.devmgr.common.security.RolePermissionMappingService;
import cn.devmgr.common.security.domain.SecurityUser;
import cn.devmgr.common.security.domain.User;

@Component
public class JwtParser {
    private final Log log = LogFactory.getLog(JwtParser.class);
    
    @Value("${security.oauth2.resource.jwt.keyValue}")
    private String publicKeyValue;

    private PublicKey publicKey;
    
    @Autowired
    private RolePermissionMappingService rolePermissionService;
    
    @PostConstruct
    private void init() {
        if(publicKey == null) {
            try {
                String[] lines = publicKeyValue.split("\n");
                StringBuffer buf = new StringBuffer();
                for(String line : lines) {
                    if(line.contains("PUBLIC KEY")) {
                        continue;
                    }
                    for(int i=0; i<line.length(); i++) {
                        char c = line.charAt(i);
                        //Base64部分
                        if((c >= '0' && c <= 'z') || c == '+' || c == '/' || c == '=') {
                            buf.append(c);
                        }
                    }
                }
                byte[] buffer = Base64.getDecoder().decode(buf.toString());  
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
                publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            }catch(Exception e) {
                log.error("创建私钥失败" + publicKeyValue, e);
            }
        }
    }
    
    public SecurityUser parseToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                .acceptExpiresAt(5) 
                .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            int userId = jwt.getClaim("user_id").asInt();
            String name = jwt.getClaim("name").asString();
            String[] roles = jwt.getClaim("authorities").asArray(String.class);
            String userName = jwt.getClaim("user_name").asString();
            
            User u = new User();
            u.setId(userId);
            u.setName(name);;
            u.setUsername(userName);
            
            //通过rolePermissionService完成角色到权限的转换。在认证服务器和JWT内传递的是角色。
            //角色对应的权限存储在各个独立的服务模块内；由服务模块自己完成转换，
            //在服务模块内使用的是permission
            SecurityUser su = new SecurityUser(u, rolePermissionService.getPermissions(roles));
            
            return su;
        } catch (JWTVerificationException exception){
            //
            if(log.isWarnEnabled()) {
                log.warn("JWT无效", exception);
            }
        }
        return null;
    }
}
