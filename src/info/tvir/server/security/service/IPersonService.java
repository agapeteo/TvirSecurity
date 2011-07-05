package info.tvir.server.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IPersonService {
	UserDetails loadUserByUsername(String name);
}
