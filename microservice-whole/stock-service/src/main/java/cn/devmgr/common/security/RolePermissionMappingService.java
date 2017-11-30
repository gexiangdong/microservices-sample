package cn.devmgr.common.security;

import java.util.List;

/**
 * 用户角色到本模块权限
 *
 */
public interface RolePermissionMappingService {

    public List<String> getPermissions(String role);
    public List<String> getPermissions(String[] roles);
}
