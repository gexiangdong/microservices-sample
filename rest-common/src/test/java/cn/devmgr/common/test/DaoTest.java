package cn.devmgr.common.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.devmgr.common.security.impl.PermissionDao;
import cn.devmgr.common.security.impl.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "cn.devmgr.common.security" })
public class DaoTest {

    private PermissionDao dao;

    @Test
    public void testDaoAll() {
	System.out.println(dao.getClass().getName());
	Collection<Permission> c = dao.getAllPermission();
	assertEquals(c.size(), 3);
	for (Permission p : c) {
	    System.out.println(p.getId() + " -- " + p.getName());
	}

	Collection<String> c1 = dao.queryPermissionsByRole("user");
	for (String s : c1) {
	    System.out.println("C1:" + s);
	}
	assertEquals(c1.size(), 2);

	ArrayList<String> permissions = new ArrayList<String>();
	permissions.add("update-order");
	permissions.add("delete-order");
	permissions.add("import-order");
	dao.updateRolePermissions("user", permissions);

	Collection<String> c2 = dao.queryPermissionsByRole("user");
	for (String s : c2) {
	    System.out.println("C2:" + s);
	}
	assertEquals(c2.size(), 3);
    }

    @Before
    public void setup() {
	EmbeddedDatabase db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("test-db.sql")
	        .build();
	dao = new PermissionDao();
	dao.setJdbcTemplate(new JdbcTemplate(db));
    }
}
