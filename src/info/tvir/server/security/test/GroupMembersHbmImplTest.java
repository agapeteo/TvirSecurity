package info.tvir.server.security.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.Resource;

import info.tvir.server.security.dao.IGroupDao;
import info.tvir.server.security.dao.IGroupMembersDao;
import info.tvir.server.security.dao.IGroupRolesDao;
import info.tvir.server.security.dao.IPersonDao;
import info.tvir.server.security.dao.IRoleDao;
import info.tvir.server.security.model.Group;
import info.tvir.server.security.model.GroupMembers;
import info.tvir.server.security.model.GroupRoles;
import info.tvir.server.security.model.Person;
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
@ContextConfiguration(locations = {
		"file:/home/emix/workspace/tvir-security/WebContent/WEB-INF/springweb-servlet.xml",
		"file:/home/emix/workspace/tvir-security/WebContent/WEB-INF/springweb-persistance.xml",
		"file:/home/emix/workspace/tvir-security/WebContent/WEB-INF/springweb-test.xml" })
public class GroupMembersHbmImplTest {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private IGroupMembersDao groupMembersDao;

	@Resource
	private IGroupDao groupDao;

	@Resource
	private IPersonDao personDao;
	
	@Resource IRoleDao roleDao;
	
	@Resource
	private IGroupRolesDao groupRolesDao;

	@Resource
	private Person testPerson;

	@Resource
	private Group testGroup;

	private GroupMembers groupMembers;
	private static Long groupMembersId;

	@Before
	public void prepare() {
		personDao.create(testPerson);
		groupDao.create(testGroup);
		groupMembers = new GroupMembers(testPerson, testGroup);		
		groupMembersId = groupMembersDao.create(groupMembers);
	}

	@After
	public void clear() {
		groupMembers = groupMembersDao.findById(groupMembersId);
		if (groupMembers != null) {
			groupMembersDao.delete(groupMembers);
		}
		if (testPerson != null) {
			personDao.delete(testPerson);
		}
		if (testGroup != null) {
			groupDao.delete(testGroup);
		}
	}

	@Test
	@Transactional
	public void testCreate() {
		log.info("created GroupMembers id = " + groupMembersId);
		assertNotNull(groupMembers);
	}

	@Test
	@Transactional
	public void testRead() {
		log.info("GroupMembers id = " + groupMembersId);
		groupMembers = groupMembersDao.findById(groupMembersId);
		assertNotNull(groupMembers);
		log.info("GroupMembers - " + groupMembers);
	}

	@Test
	@Transactional
	public void testDelete() {
		log.info("GroupMembers id to delete = " + groupMembersId);
		groupMembers = groupMembersDao.findById(groupMembersId);
		groupMembersDao.delete(groupMembers);
		GroupMembers groupMembers = groupMembersDao.findById(groupMembersId);
		assertNull(groupMembers);
	}

	@Test
	@Transactional
	public void testGetGroupsByPersonNoParents() {
		Set<Group> groups = groupMembersDao.getGroupsByPerson(testPerson);
		log.info("person [" + testPerson.getDisplayname() + "] has groups: "
				+ groups);
		assertNotNull(groups);
		assertTrue(groups.size() > 0);
		assertEquals(groups.iterator().next().getName(), testGroup.getName());
	}


	@Test
	//@Transactional
	public void testGetGroupsByPersonWithParents() {
		//create parent group
		Group parentGroup = new Group(null, "PARENT_GROUP", "PARENT_GROUP", null);
		groupDao.create(parentGroup);
		testGroup.setParent(parentGroup);
		groupDao.update(testGroup);
		groupMembersDao.create(new GroupMembers(testPerson, testGroup));
		Set<Group> groups = groupMembersDao.getGroupsByPerson(testPerson, IGroupMembersDao.WITH_PARENTS);
		log.info("person ["+testPerson.getDisplayname()+"] has groups (with parents): "+groups);
		log.info("total groups (with parent) count = " + groups.size());
		assertNotNull(groups);
		assertTrue(groups.size() == 2);
		
		// delete parent group
		testGroup.setParent(null);
		groupDao.update(testGroup);
		groupDao.delete(parentGroup);
	}

	@Test
	public void testGetRolesByPerson() {
		//create parent group
		Group parentGroup = new Group(null, "PARENT_GROUP", "PARENT_GROUP", null);
		groupDao.create(parentGroup);
		testGroup.setParent(parentGroup);
		groupDao.update(testGroup);
		// create another group
		Group anotherGroup = new Group(null, "ANOTHER_GROUP", "ANOTHER_GROUP", null);
		groupDao.create(anotherGroup);
		groupMembersDao.create(new GroupMembers(testPerson, anotherGroup));
		//create roles list
		Role roleTest = new Role(null, "ROLE_TEST", null);
		Role roleParent = new Role(null, "ROLE_PARENT", null);
		Role roleChild = new Role(null, "ROLE_CHILD", null);
		Role roleAnother = new Role(null, "ROLE_ANOTHER", null);
		roleDao.create(roleTest);
		roleDao.create(roleParent);
		roleDao.create(roleChild);
		roleDao.create(roleAnother);
		// add 2 roles to parent group("role_test", "role_parent")
		groupRolesDao.create(new GroupRoles(testGroup, roleTest));
		groupRolesDao.create(new GroupRoles(testGroup, roleChild));
		// add 2 roles in child group ("role_test", "role_child")
		groupRolesDao.create(new GroupRoles(parentGroup, roleTest));
		groupRolesDao.create(new GroupRoles(parentGroup, roleParent));
		// add 2 roles to another group ("role_test", "role_another")
		groupRolesDao.create(new GroupRoles(anotherGroup, roleTest));
		groupRolesDao.create(new GroupRoles(anotherGroup, roleAnother));
		// get Roles
		Set<Role> roles = groupMembersDao.getRolesByPerson(testPerson);
		log.info("person ["+testPerson.getDisplayname()+"] has this roles: "+roles);
		assertNotNull(roles);
		assertTrue(roles.size() == 4);
		
		// delete new groups
		groupDao.delete(anotherGroup);
		testGroup.setParent(null);
		groupDao.update(testGroup);
		groupDao.delete(parentGroup);
		// delete roles
		roleDao.delete(roleTest);
		roleDao.delete(roleParent);
		roleDao.delete(roleChild);
		roleDao.delete(roleAnother);		
	}

	@Test
	public void testGetPersonsByGroup(){
		Set<Person> persons = groupMembersDao.getPersonsByGroup(testGroup);
		log.info("person list of testGroup: " + persons);
		assertNotNull(persons);
		assertTrue(persons.size() > 0);
	}
	
	@Test
	public void testGetPersonsByGroupId(){
		Set<Person> persons = groupMembersDao.getPersonsByGroupId(testGroup.getId());
		log.info("person list of testGroup.id[" + testGroup.getId() +"] :" + persons);
		assertNotNull(persons);
		assertTrue(persons.size() > 0);
	}
	
	@Test
	@Transactional
	public void testRemoveGroupMembers(){
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(testPerson.getId());
		groupMembersDao.removePersonsFromGroup(testGroup, ids);
		Set<Person> persons = groupMembersDao.getPersonsByGroup(testGroup);
		log.info("persons list after remove from group: " + persons.toString());
		assertTrue( !persons.contains(testPerson) );
	}

	@Test
	@ExpectedException(DataIntegrityViolationException.class)
	public void testGroupNull() {
		groupMembers.setGroup(null);
		groupMembersDao.update(groupMembers);
	}

	@Test
	@ExpectedException(DataIntegrityViolationException.class)
	public void testPersonNull() {
		groupMembers.setPerson(null);
		groupMembersDao.update(groupMembers);
	}

}
