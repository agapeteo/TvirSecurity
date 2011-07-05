package info.tvir.server.security.model;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface IPerson extends UserDetails{
	
	Date getPrevlogin();
	
	Date getCurrlogin();
	
	String getDisplayname();
	
	Long getId();

}
