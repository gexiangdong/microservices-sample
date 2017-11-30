package cn.devmgr.common.security.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cn.devmgr.common.security.RolePermissionMappingService;

@Component
public class RolePermissionMappingServiceImpl implements RolePermissionMappingService {
    private final Log log = LogFactory.getLog(RolePermissionMappingServiceImpl.class);
    
    @Override
    public Collection<String> getPermissions(String role) {
        if(log.isTraceEnabled()) {
            log.trace("query permissions for " + role);
        }
        //TODO: 持久存储等
        Collection<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("user");
        return list;
    }

    @Override
    public Collection<String> getPermissions(String[] roles) {
        if(roles == null) {
            return new ArrayList<String>();
        }
        Set<String> set = new HashSet<String>();
        for(String r : roles) {
            if(r == null) {
                continue;
            }
            Collection<String> oneList = getPermissions(r);
            set.addAll(oneList);
        }
        return set;
    }

}
