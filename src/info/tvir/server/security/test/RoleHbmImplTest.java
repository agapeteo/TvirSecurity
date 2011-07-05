package info.tvir.server.security.test;

import static org.junit.Assert.*;

import java.util.Set;

import javax.annotation.Resource;

import info.tvir.server.security.dao.IRoleDao;
import info.tvir.server.security.model.Role;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={"file:/home/emix/workspace/tvir-security/WebContent/WEB-INF/springweb-servlet.xml",
				"file:/home/emix/workspace/tvir-security/WebContent/WEB-INF/springweb-persistance.xml",
				"file:/home/emix/workspace/tvir-security/WebContent/WEB-INF/springweb-test.xml"})
				
public class RoleHbmImplTest {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IRoleDao roleDao;	
	
	private Role role;
	private static Long roleId;
	
	private static final String ROLE_NAME = "ROLE_TEST";
	private static final String ROLE_DESCRIPTION = "role description";	
	
	
	@Before
	public void prepare(){
		role = new Role(null, ROLE_NAME, ROLE_DESCRIPTION);
		roleId = roleDao.create(role);
	}
	
	@After
	public void clear(){
		role = roleDao.findById(roleId);
		if (role != null){
			roleDao.delete(role);
		}
	}
	
	@Test
	@Transactional
	public void testCreate(){
		log.info("created Role id = " + roleId);
		assertNotNull(role);
	}	
	
	@Test
	@Transactional
	public void testRead(){
		log.info("Role id = " + roleId);
		role = roleDao.findById(roleId);
		assertNotNull(role);
		log.info("Role - "+role);		
	}
	
	@Test
	@Transactional
	public void testDelete(){
		log.info("Role id to delete = "+roleId);
		role = roleDao.findById(roleId);
		roleDao.delete(role);
		Role role = roleDao.findById(roleId);
		assertNull(role);
	}
	
	@Test
	@Transactional
	public void testGetRoleByName(){
		Role newRole = roleDao.getRoleByName(ROLE_NAME);
		assertNotNull(newRole);
		assertEquals(newRole.getName(), ROLE_NAME);
	}
	
	@Test
	public void testGetAllRoles(){
		Set<Role> roles = roleDao.getAllRoles();
		log.info("all roles: " + roles.toString());
		assertNotNull(roles);
		assertTrue(roles.size() > 0);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testNameNull(){
		role.setName(null);
		roleDao.update(role);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testNameUnique(){
		Role newRole = new Role(null, ROLE_NAME, ROLE_DESCRIPTION);
		roleDao.create(newRole);
	}

}
