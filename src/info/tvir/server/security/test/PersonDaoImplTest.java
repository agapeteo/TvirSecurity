package info.tvir.server.security.test;

import static org.junit.Assert.*;

import java.util.List;

import info.tvir.server.security.dao.IPersonDao;
import info.tvir.server.security.model.Person;

import javax.annotation.Resource;

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

public class PersonDaoImplTest {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IPersonDao personDao;
	
	private Person person;
	private Long personId;
	
	private static final String LOGIN = "test_login";
	private static final String PASSWORD = "password";
	private static final String FIRSTNAME = "Олег";
	private static final String LASTNAME = "Анатольевич";
	private static final String DISPLAYNAME = "TEST Клавишенко";
	
	@Before	
	public void init(){
		person = new Person();
		person.setLogin(LOGIN);
		person.setPassword(PASSWORD);
		person.setFirstname(FIRSTNAME);
		person.setLastname(LASTNAME);
		person.setDisplayname(DISPLAYNAME);
		person.setEnabled(true);
		personId = personDao.create(person);
	}
	
	@After
	public void clear(){
		person = personDao.findById(personId);
		if (person != null){
			personDao.delete(person);
		}
	}
	
	@Test
	@Transactional
	public void testCreate(){
		log.info("created Person id = " + personId);
		assertNotNull(person);
	}	
	
	@Test
	@Transactional
	public void testRead(){
		log.info("Person id = " + personId);
		person = personDao.findById(personId);
		assertNotNull(person);
		log.info("Person - "+person);		
	}
	
	@Test	
	@Transactional
	public void testDelete(){
		log.info("User id to delete = "+personId);
		person = personDao.findById(personId);
		personDao.delete(person);
		Person person = personDao.findById(personId);
		assertNull(person);
	}
	
	@Test
	@Transactional
	public void testGetPersonByLogin(){
		Person newPerson = personDao.getPersonByLogin(LOGIN);
		assertNotNull(newPerson);
		assertEquals(newPerson.getLogin(), LOGIN);
	}
	
	@Test
	public void testGetAllEnabled(){
		List<Person> persons = personDao.getAllEnabledPersons();
		assertNotNull(persons);
		assertTrue(persons.size() > 0);
		log.info("all enabled persons: " + persons);
		log.info("enables persons count: " + persons.size());
	}
	
	@Test
	public void testGetAll(){
		List<Person> persons = personDao.getAll();
		assertNotNull(persons);
		assertTrue(persons.size() > 0);
		log.info("all persons: " + persons);
		log.info("all persons count: " + persons.size());
	}
		
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testLoginNull(){
		person.setLogin(null);
		personDao.update(person);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testPasswordNull(){
		person.setPassword(null);
		personDao.update(person);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testDisplaynameNull(){
		person.setDisplayname(null);
		personDao.update(person);
	}
	
	@Test	
	@ExpectedException(DataIntegrityViolationException.class)
	public void testLoginUnique(){
		person = new Person(person.getLogin(), person.getPassword(), person.getDisplayname());
		personId = personDao.create(person);
	}
	
}
