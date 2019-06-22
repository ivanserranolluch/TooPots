package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ReservaClienteActividadDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/reservaClienteActividad")
public class ReservaClienteActividadController {
	
	private ReservaClienteActividadDao reservaClienteActividadDao;
	
	@Autowired
	public void setReservaClienteActividadDao(ReservaClienteActividadDao reservaClienteActividadDao) {
		this.reservaClienteActividadDao = reservaClienteActividadDao; 
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model,
                                 @RequestParam("estado") Optional<Integer> estado){

	    if (estado.orElse(-1) == 1){
            model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservaClienteActividadPendientes());
        }else if (estado.orElse(-1) == 2){
            model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservaClienteActividadAceptadas());
        }else{
	        model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservas());
        }

		return "reservaClienteActividad/list"; 
	}





	/*@RequestMapping(value="/listAceptadas", method=RequestMethod.GET)
	public String listActivitiesAceptadas(Model model) {
		model.addAttribute("reservaClienteActividadDao", reservaClienteActividadDao.getReservaClienteActividadAceptadas()); 
		return "reservaClienteActividad/list"; 
	}*/

}
