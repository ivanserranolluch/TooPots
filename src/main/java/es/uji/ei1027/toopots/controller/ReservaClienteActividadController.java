package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ReservaClienteActividadDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;

@Controller
@RequestMapping("/reservaClienteActividad")
public class ReservaClienteActividadController {
	
	private ReservaClienteActividadDao reservaClienteActividadDao;
	
	@Autowired
	public void setReservaClienteActividadDao(ReservaClienteActividadDao reservaClienteActividadDao) {
		this.reservaClienteActividadDao = reservaClienteActividadDao; 
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model) {
		model.addAttribute("reservaClienteActividadDao", reservaClienteActividadDao.getReservaClienteActividadPendientes()); 
		return "reservaClienteActividad/list"; 
	}
	
	@RequestMapping(value="/listAceptadas", method=RequestMethod.GET) 
	public String listActivitiesAceptadas(Model model) {
		model.addAttribute("reservaClienteActividadDao", reservaClienteActividadDao.getReservaClienteActividadAceptadas()); 
		return "reservaClienteActividad/list"; 
	}

}
