package es.uji.ei1027.toopots.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/")
public class CommonController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getLobby(HttpSession session){
		String route = "common/index";
		User user = (User) session.getAttribute("user");
		if (user != null){
			switch(user.getTipoUsuario()){
			case "cliente":
				route = "cliente/lobby";
				break;
			case "monitor":
				route = "monitor/lobby";
				break;
			case "admin":
				route = "admin/lobby";
			};
		}
		return route;
	}

}