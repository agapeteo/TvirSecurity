package info.tvir.server.security.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import info.tvir.server.security.dao.IGroupDao;
import info.tvir.server.security.model.Group;

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

public class GroupHbmImplTest {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IGroupDao groupDao;	
	
	private Group group;
	private static Long groupId;
	
	private static final String GROUP_NAME = "TEST GROUP";
	private static final String GROUP_DESCRIPTION = "group description";	
	
	
	@Before
	public void prepare(){
		group = new Group(null, GROUP_NAME, GROUP_DESCRIPTION, null);
		groupId = groupDao.create(group);
	}
	
	@After
	public void clear(){
		group = groupDao.findById(groupId);
		if (group != null){
			groupDao.delete(group);
		}
	}
	
	@Test
	@Transactional
	public void testCreate(){
		log.info("created Group id = " + groupId);
		assertNotNull(group);
	}	
	
	@Test
	@Transactional
	public void testRead(){
		log.info("Group id = " + groupId);
		group = groupDao.findById(groupId);
		assertNotNull(group);
		log.info("Group - "+group);		
	}
	
	@Test
	@Transactional
	public void testDelete(){
		log.info("Group id to delete = "+groupId);
		group = groupDao.findById(groupId);
		groupDao.delete(group);
		Group group = groupDao.findById(groupId);
		assertNull(group);
	}
	
	
	@Test
	@ExpectedException(DataIntegrityViolationException.class)
	public void testNameNull(){
		group.setName(null);
		groupDao.update(group);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testNameUnique(){
		Group newGroup = new Group(null, GROUP_NAME, GROUP_DESCRIPTION, null);
		groupDao.create(newGroup);
	}
	
	@Test
	@Transactional
	public void testGetGroupByName(){
		Group newGroup = groupDao.getGroupByName(GROUP_NAME);
		assertNotNull(newGroup);
		assertEquals(newGroup.getName(),GROUP_NAME);
	}
	
	@Test
	@Transactional
	public void testGetAll(){
		List<Group> groups = groupDao.getAll();
		log.info("all groups: " + groups.toString());
		assertNotNull(groups);
		assertTrue(groups.size() > 0);
	}

}
