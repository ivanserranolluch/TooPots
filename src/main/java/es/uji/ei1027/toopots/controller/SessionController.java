package es.uji.ei1027.toopots.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.UsuariosRegistradosDao;
import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/login")
public class SessionController {
	
	private final String LOGIN_ROUTE = "common/login";
	
	private UsuariosRegistradosDao usuariosRegistradosDao;
    
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
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta"); 
            return LOGIN_ROUTE;
        }
        
        // Autenticats correctament. 
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user); 

		return "common/succes";
	}

}