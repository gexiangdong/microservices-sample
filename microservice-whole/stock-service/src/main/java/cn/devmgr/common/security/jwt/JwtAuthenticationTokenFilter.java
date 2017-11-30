package cn.devmgr.common.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.devmgr.common.security.domain.SecurityUser;

/**
 * 过滤器，查看请求是否包含token，并尝试按照JWT解析，如果解析成功，利用token内信息创建当前用户，并赋值给spring security.
 *  
 *
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final String TOKEN_HEADER = "Authorization";
    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestHeader = request.getHeader(this.TOKEN_HEADER);
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            SecurityUser su = jwtParser.parseToken(authToken);
            if (su != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Current User is " + su.getUsername());
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(su, null,
                        su.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("遇到无效的token:" + authToken);
                }
            }
        } else {
            log.warn("couldn't find bearer string, will ignore the header " + requestHeader);
        }

        chain.doFilter(request, response);
    }
}