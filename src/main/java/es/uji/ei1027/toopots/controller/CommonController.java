package es.uji.ei1027.toopots.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Monitor;
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
				route = "redirect:cliente/lobby";
				break;
			case "monitor":
				route = "redirect:monitor/lobby";
				break;
			case "admin":
				route = "redirect:/administrador/lobby";
			};
		}
		return route;
	}
	
	@RequestMapping(value="/singup", method=RequestMethod.GET)
	public String singUp(Model model){
		model.addAttribute("rol", "None");
		model.addAttribute("monitor", new Monitor());
		model.addAttribute("cliente", new Cliente());
		
		return "common/singup";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.setAttribute("user", null);
		return "redirect:/";
	}
	
	@RequestMapping(value="/success")
	public String success(Model model){
		return "common/success";
	}
	
	
	
	
}
