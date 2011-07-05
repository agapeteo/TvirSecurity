package info.tvir.server.security;

import info.tvir.server.security.model.IPerson;

import org.springframework.security.core.userdetails.UserDetails;

public interface PersonUserDetails extends UserDetails{
	
	IPerson getPerson();

}
