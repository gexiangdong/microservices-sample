package cn.devmgr.common.security;

import java.util.Collection;

/**
 * 用户角色到本模块权限
 *
 */
public interface RolePermissionMappingService {

    public Collection<String> getPermissions(String role);
    public Collection<String> getPermissions(String[] roles);
}
