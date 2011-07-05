package info.tvir.server.security.example.controller;

import info.tvir.server.security.PersonUserDetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/home.xhtml")
	public String sweetHome(ModelMap model){
		String login;
		String rolesStr;
		String displayname;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof PersonUserDetails && principal != null){
			PersonUserDetails details = (PersonUserDetails) principal; 			
			login = details.getUsername();
			displayname = details.getPerson().getDisplayname();
			Collection<GrantedAuthority> authorities = details.getAuthorities();
			if (authorities != null ){
				rolesStr = details.getAuthorities().toString();
			} else {
				rolesStr = "NO AUTHORITIES";
			}
			
		} else {
			login = "UNKNOWN";
			rolesStr = "UNKNOWN";
			displayname = "UNKNOWN";
		}
		
		model.addAttribute("login", login);
		model.addAttribute("rolesStr", rolesStr);
		model.addAttribute("displayname", displayname);
		
		return "home";
	}

}
