package es.uji.ei1027.toopots.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.dao.UsuariosRegistradosDao;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.NavbarLink;
import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/login")
public class SessionController {
	
	private final String LOGIN_ROUTE = "common/login";
	
	private UsuariosRegistradosDao usuariosRegistradosDao;
	private MonitorDao monitorDao;
	
	@Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao=monitorDao;
	}
    
    @Autowired
    public void setUserDao(UsuariosRegistradosDao usuariosRegistradosDao) {
		this.usuariosRegistradosDao=usuariosRegistradosDao;
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String log_view(Model model){
		model.addAttribute("user", new User());
		return LOGIN_ROUTE;
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String loging_user(@ModelAttribute("user") User user, 
			BindingResult bindingResult, HttpSession session){
		
		if (bindingResult.hasErrors()) {
            return LOGIN_ROUTE;
        }
		
        // Comprova que el login siga correcte 
        // intentant carregar les dades de l'usuari 
        user = usuariosRegistradosDao.loadUserByEmail(user.getEmail(),user.getPassword(), usuariosRegistradosDao.getUsuarios()); 
        if (user == null) {
            session.setAttribute("errors", "ERROR: Los credenciales introducidos son incorrectos");
            return LOGIN_ROUTE;
        }
        
    
        //NUEVO IVAN
        if(user.getTipoUsuario().equals("monitor")) {    	
        	
	        Monitor monitor = monitorDao.getMonitorEmail(user.getEmail());
	        //System.out.println(monitor.getEstado("rechazada"));
	        if(monitor.getEstado().equals("pendiente") || monitor.getEstado().equals("rechazada")) {
	        		return LOGIN_ROUTE;
	        }
        }
        //#########
        
        
        // Autenticats correctament. 
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user); 
        
        // Introducir los links de la navbar
        session.setAttribute("links", getLinks(user.getTipoUsuario()));

		return "redirect:/";
	}
	
	private List<NavbarLink> getLinks(String tipoUsuario){
		
		List<NavbarLink> links = new ArrayList<NavbarLink>();
		
		switch (tipoUsuario) {
		
		case "cliente":
			links.add(makeLink("Actividades", "/actividad/actividades"));
			links.add(makeLink("Mis reservas", "/reservaClienteActividad/listCliente"));
			break;
			
		case "monitor":
            links.add(makeLink("Gestion reservas", "/reservaClienteActividad/listaux"));
            links.add(makeLink("Mis actividades", "/actividad/listActividades"));
			break;
		
		case "administrador":
			
			break;
		}
		
		return links;
		
	}
	
	private NavbarLink makeLink(String name, String url){
		NavbarLink link = new NavbarLink();
		link.setLink(url);
		link.setName(name);
		return link;
	}

}
