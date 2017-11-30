package cn.devmgr.common.security.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cn.devmgr.common.security.RolePermissionMappingService;

@Component
public class RolePermissionMappingServiceImpl implements RolePermissionMappingService {
    private final Log log = LogFactory.getLog(RolePermissionMappingServiceImpl.class);
    
    @Override
    public List<String> getPermissions(String role) {
        if(log.isTraceEnabled()) {
            log.trace("query permissions for " + role);
        }
        //TODO: 持久存储等
        List<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("user");
        return list;
    }

    @Override
    public List<String> getPermissions(String[] roles) {
        if(roles == null) {
            return new ArrayList<String>();
        }
        List<String> list = new ArrayList<String>();
        for(String r : roles) {
            if(r == null) {
                continue;
            }
            List<String> oneList = getPermissions(r);
            list.addAll(oneList);
        }
        return list;
    }

}
