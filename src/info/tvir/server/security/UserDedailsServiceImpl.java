package info.tvir.server.security;

import javax.annotation.Resource;

import info.tvir.server.security.service.IPersonService;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserDedailsServiceImpl implements UserDetailsService{
	
	@Resource
	private IPersonService personService; 

	@Override
	public UserDetails loadUserByUsername(String name)
			throws UsernameNotFoundException, DataAccessException {
		
		return personService.loadUserByUsername(name);
	}

}