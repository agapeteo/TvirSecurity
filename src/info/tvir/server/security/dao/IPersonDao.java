package info.tvir.server.security.dao;

import java.util.List;

import info.tvir.hibernateutils.dao.IDao;
import info.tvir.server.security.model.Person;

public interface IPersonDao extends IDao<Person, Long>{
	
	final String QUERY_PERSON_BY_LOGIN = "FROM Person p WHERE UPPER(p.login) = UPPER(?)";
	final String QUERY_ALL_ENABLED = "FROM Person p WHERE p.enabled = true";
	final String QUERY_ALL = "FROM Person";
	
	Person getPersonByLogin(String login);
	
	List<Person> getAllEnabledPersons();
	
	List<Person> getAll();

}
