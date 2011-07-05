package info.tvir.server.security.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@RequestMapping("/login.xhtml")
	public String login(
			@RequestParam(value="error", required=false) String error,
			ModelMap model){
		
		String errorMsg = null;
		if (error != null){
			errorMsg = "wrong login/password";
		}
		model.addAttribute("error", errorMsg);
		return "login";
		
	}

}
