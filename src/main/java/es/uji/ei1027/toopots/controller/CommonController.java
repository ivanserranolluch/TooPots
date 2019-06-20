package es.uji.ei1027.toopots.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/")
public class CommonController {
	
	 private MonitorDao monitorDao;
	 private ClienteDao clienteDao;

	 @Autowired
	 public void setMonitorDao(MonitorDao monitorDao) {
		 this.monitorDao=monitorDao;
	 }
	 
	 @Autowired
	 public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao=clienteDao;
	 }
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getLobby(HttpSession session, Model model){
		String route = "common/index";
		User user = (User) session.getAttribute("user");
	
		if (user != null){
			switch(user.getTipoUsuario()){
			case "cliente":
				for (Cliente cliente : clienteDao.getClientes()) {
					if (cliente.getEmail().equals(user.getEmail())) {
						session.setAttribute("nombre", cliente.getNombre());
					}
				}
			
				route = "redirect:cliente/lobby";
				break;
				
			case "monitor":
			
				for (Monitor monitor : monitorDao.getMonitores()) {
					if (monitor.getEmail().equals(user.getEmail())) {
						session.setAttribute("nombre", monitor.getNombre());
						session.setAttribute("id", monitor.getId());
					}
				}
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
		model.addAttribute("rol", "monitor");
		model.addAttribute("monitor", new Monitor());
		//model.addAttribute("cliente", new Cliente());
		
		return "common/singup";
	}
	//NUEVO
	//################

	@RequestMapping(value="/singupCliente", method=RequestMethod.GET)
	public String singUpCliente(Model model){
		model.addAttribute("rol", "cliente");
		model.addAttribute("cliente", new Cliente());

		return "common/singupCliente";
	}

	@RequestMapping("/presingup")
	public String preSingUp(Model model){

		return "common/presingup";
	}

	//################

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
