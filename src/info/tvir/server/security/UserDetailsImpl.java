package info.tvir.server.security;

import java.util.Collection;

import info.tvir.server.security.model.IPerson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImpl implements PersonUserDetails {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(getClass());

	private IPerson person;

	public UserDetailsImpl(IPerson person) {
		this.person = person;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		log.debug("getAuthorities(): " + person.getAuthorities());
		return person.getAuthorities();
	}

	@Override
	public String getPassword() {
		return person.getPassword();
	}

	@Override
	public String getUsername() {
		return person.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return person.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return person.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return person.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return person.isEnabled();
	}

	@Override
	public IPerson getPerson() {
		return this.person;
	}

}
