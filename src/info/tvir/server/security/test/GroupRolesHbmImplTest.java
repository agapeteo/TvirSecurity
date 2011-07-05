package info.tvir.server.security.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.Resource;

import info.tvir.server.security.dao.IGroupDao;
import info.tvir.server.security.dao.IGroupRolesDao;
import info.tvir.server.security.dao.IRoleDao;
import info.tvir.server.security.model.Group;
import info.tvir.server.security.model.GroupRoles;
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
				
public class GroupRolesHbmImplTest {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IGroupRolesDao groupRolesDao;
	
	@Resource
	private IGroupDao groupDao;
	
	@Resource
	private IRoleDao roleDao;
	
	@Resource 
	private Role testRole;
	
	@Resource 
	private Group testGroup;
	
	private GroupRoles groupRoles;
	private static Long groupRolesId;
	
	
	
	@Before
	public void prepare(){
		roleDao.create(testRole);
		groupDao.create(testGroup);
		groupRoles = new GroupRoles();
		groupRoles.setGroup(testGroup);
		groupRoles.setRole(testRole);
		groupRolesId = groupRolesDao.create(groupRoles);
	}
	
	@After
	public void clear(){
		groupRoles = groupRolesDao.findById(groupRolesId);
		if (groupRoles != null){			
			groupRolesDao.delete(groupRoles);
		}
		if (testRole != null){
			roleDao.delete(testRole);
		}
		if (testGroup != null){
			groupDao.delete(testGroup);
		}
	}
	
	@Test
	@Transactional
	public void testCreate(){
		log.info("created GroupRoles id = " + groupRolesId);
		assertNotNull(groupRoles);
	}	
	
	@Test
	@Transactional
	public void testRead(){
		log.info("GroupRoles id = " + groupRolesId);
		groupRoles = groupRolesDao.findById(groupRolesId);
		assertNotNull(groupRoles);
		log.info("GroupRoles - "+groupRoles);		
	}
	
	@Test
	public void testGetRolesByGroup(){
		Set<Role> roles = groupRolesDao.getRolesByGroup(testGroup);
		log.info("group ["+testGroup.getName()+"] has roles: "+ roles);
		assertNotNull(roles);
		assertTrue(roles.size() > 0);
		assertEquals(roles.iterator().next().getName() , testRole.getName());
	}
	
	@Test
	@Transactional
	public void testDelete(){
		log.info("GroupRoles id to delete = "+groupRolesId);
		groupRoles = groupRolesDao.findById(groupRolesId);
		groupRolesDao.delete(groupRoles);
		GroupRoles groupRoles = groupRolesDao.findById(groupRolesId);
		assertNull(groupRoles);
	}
	
	@Test
	public void testGetRolesByGroupId(){
		Set<Role> roles = groupRolesDao.getRolesByGroupId(testGroup.getId());
		log.info("roles list of testGroup.id[" + testGroup.getId() +"] :" + roles);
		assertNotNull(roles);
		assertTrue(roles.size() > 0);
	}
	
	@Test
	@Transactional
	public void testRemoveGroupRoles(){
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(testRole.getId());
		groupRolesDao.removeRolesFromGroup(testGroup, ids);
		Set<Role> roles = groupRolesDao.getRolesByGroup(testGroup);
		log.info("roles list after remove from group: " + roles.toString());
		assertTrue( !roles.contains(testRole) );
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testGroupNull(){
		groupRoles.setGroup(null);
		groupRolesDao.update(groupRoles);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testRoleNull(){
		groupRoles.setRole(null);
		groupRolesDao.update(groupRoles);
	}

}
