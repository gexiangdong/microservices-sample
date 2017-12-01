package cn.devmgr.common.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PermissionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Cacheable
    public List<String> queryPermissionsByRole(String role) {
        String sql = "select permission from auth_role_permission where role=?";
        return jdbcTemplate.queryForList(sql, String.class, role);
    }

    //TODO: 修改权限 管理权限

}
