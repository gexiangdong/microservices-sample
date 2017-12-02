package cn.devmgr.common.security.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PermissionDao {
    private final String SQL_INSERT = "insert into auth_role_permission(role, permission) values(?, ?)";
    private final String SQL_DELETE = "delete from auth_role_permission where role=? and permission=?";
    private final String SQL_SELECT = "select permission from auth_role_permission where role=?";
    private final String SQL_ALL_PERMISSIONS = "select permission as id, name as name from auth_permission order by ord";

    private JdbcTemplate jdbcTemplate;

    @Cacheable(value = "rolePermissions", key = "#role")
    public Collection<String> queryPermissionsByRole(String role) {
	return jdbcTemplate.queryForList(SQL_SELECT, String.class, role);
    }

    /**
     * 等效于在private JdbcTemplate jdbcTemplate定义出写上@Autowired，写在这里是为了测试用例里能用代码指定
     * 
     * @param jdbcTemplate
     */
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 更新某个角色的权限；供配置角色权限的API调用
     * 
     * @param role
     * @param permisions
     */
    @Transactional
    @CacheEvict(value = "rolePermissions", key = "#role")
    public void updateRolePermissions(final String role, Collection<String> permisions) {
	Collection<String> list = queryPermissionsByRole(role);
	for (final String p : list) {
	    if (!permisions.contains(p)) {
		// 需要删除
		jdbcTemplate.update(SQL_DELETE, new PreparedStatementSetter() {
		    public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, role);
			ps.setString(2, p);
		    }
		});
	    }
	}
	for (final String p : permisions) {
	    if (!list.contains(p)) {
		// 需要插入的新权限
		jdbcTemplate.update(SQL_INSERT, new PreparedStatementSetter() {
		    public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, role);
			ps.setString(2, p);
		    }
		});
	    }
	}
    }

    /**
     * 返回所有权限，供配置权限的API调用, 显示列表，以便选择角色权限
     * 
     * @return
     */
    public List<Permission> getAllPermission() {
	RowMapper<Permission> rowMapper = new BeanPropertyRowMapper<Permission>(Permission.class);
	List<Permission> list = jdbcTemplate.query(SQL_ALL_PERMISSIONS, rowMapper);
	return list;
    }

}
