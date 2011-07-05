package info.tvir.server.security.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import info.tvir.hibernateutils.dao.impl.HibernateDaoImpl;
import info.tvir.server.security.dao.IPersonDao;
import info.tvir.server.security.model.Person;

@Repository("personDao")
public class PersonHbmImpl extends HibernateDaoImpl<Person, Long> implements IPersonDao{
	private static final Class<Person> CLASS = Person.class;
	
	@Resource
	private HibernateTemplate template;
	
	public PersonHbmImpl(){
		super(CLASS);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Person getPersonByLogin(String login) {
		Person result = null;
		List<Person> list = (List<Person>) template.find(QUERY_PERSON_BY_LOGIN, login);
		
		if (list != null && list.size() > 0){
			result = list.get(0);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Person> getAllEnabledPersons() {		
		return template.find(QUERY_ALL_ENABLED);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Person> getAll() {		
		return template.find(QUERY_ALL);
	}
	
	@Override
	@Transactional
	public Long create(Person person){
		return (Long) template.save(person);
	}
	
	@Override
	@Transactional
	public void update(Person person){
		template.update(person);
	}
	
	@Override
	@Transactional
	public void delete(Person person){
		template.delete(person);
	}

}
