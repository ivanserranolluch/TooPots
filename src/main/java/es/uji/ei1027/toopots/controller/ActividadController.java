package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.model.CertificacionesTipoActividad;

@Controller
@RequestMapping("/actividad")
public class ActividadController {
	
	private ActividadDao actividadDao;
	
	@Autowired
	public void setPreferenciasClienteDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao; 
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET) 
	public String processAddSubmit(@ModelAttribute("certificacionActividad") CertificacionesTipoActividad certificacionesTipoActividad, BindingResult
			bindingResult) { 
	
		return "redirect:list"; 
	}

}
