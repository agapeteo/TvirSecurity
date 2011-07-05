package info.tvir.server.security.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import info.tvir.server.security.UserDetailsImpl;
import info.tvir.server.security.dao.IGroupMembersDao;
import info.tvir.server.security.dao.IPersonDao;
import info.tvir.server.security.model.Person;
import info.tvir.server.security.model.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsource.util.common.Assert;

@Service("personService")
public class PersonServiceImpl implements IPersonService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private IPersonDao personDao;

	@Resource
	private IGroupMembersDao groupMembersDao;

	@Override
	public UserDetails loadUserByUsername(String name) {
		UserDetails result = null;
		Person person = personDao.getPersonByLogin(name);

		if (person == null) {
			String message = "user [" + name + "] not found";
			log.debug(message);
			throw new UsernameNotFoundException(message);
		}

		try {
			Set<Role> userRoles = groupMembersDao.getRolesByPerson(person);
			person.setAuthorities(getGrantedAuthoritiesByRoles(userRoles));
			result = new UserDetailsImpl(person);
		} catch (Exception e) {
			String msg = "failed to get user details by user: " + name;
			log.warn(msg);
			throw new DataAccessResourceFailureException(msg, e);
		}

		Assert.notNull(result, "userDetails is null of user: " + name);

		return result;
	}

	private Set<GrantedAuthority> getGrantedAuthoritiesByRoles(Set<Role> roles) {
		Set<GrantedAuthority> result = new HashSet<GrantedAuthority>(roles
				.size());

		for (Role eachRole : roles) {
			result.add(new GrantedAuthorityImpl(eachRole.getName()));
		}

		return result;
	}

}
