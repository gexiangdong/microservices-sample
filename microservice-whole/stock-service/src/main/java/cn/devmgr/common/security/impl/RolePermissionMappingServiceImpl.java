package cn.devmgr.common.security.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cn.devmgr.common.security.RolePermissionMappingService;

/**
 * 在open auth server端，user authorities里存储的是角色，
 * 在具体的服务模块内，需要把角色转换为权限存储到user authorities里。
 * 角色都会用ROLE_开头；权限则无前缀限制。
 * 此类完成角色到权限的转换
 */
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
        //add authorize to the list
        list.add("deleteInventory");
        list.add("queryInventory");
        //add role to the list
        list.add("ROLE_" + role);
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
