package es.uji.ei1027.toopots.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/login")
public class SessionController {
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String log_view(Model model){
		model.addAttribute("user", new User());
		return "common/login";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String loging_user(Model model){
		model.addAttribute("user", new User());
		return "common/succes";
	}

}
