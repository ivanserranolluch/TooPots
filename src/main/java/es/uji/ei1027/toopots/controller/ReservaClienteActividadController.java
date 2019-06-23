package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.MonitorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaClienteActividadDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.ReservaClienteActividad;
import es.uji.ei1027.toopots.model.User;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservaClienteActividad")
public class ReservaClienteActividadController {
	
	private ReservaClienteActividadDao reservaClienteActividadDao;
	private ClienteDao clienteDao;
	private MonitorDao monitorDao;

	@Autowired
	public void setReservaClienteActividadDao(ReservaClienteActividadDao reservaClienteActividadDao) {
		this.reservaClienteActividadDao = reservaClienteActividadDao; 
	}
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao; 
	}

	@Autowired
	public void setMonitorDao(MonitorDao monitorDao){
		this.monitorDao=monitorDao;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model,
                                 @RequestParam("estado") Optional<Integer> estado,
								@RequestParam("mon") Optional<Boolean> monitor,
								 HttpSession session){

		if (monitor.orElse(false)){
			User user = (User) session.getAttribute("user");
			String email = user.getEmail();
			model.addAttribute("reservasActividad", reservaClienteActividadDao.getReservasMonitor(monitorDao.getMonitorEmail(email).getId()));

		}else {

			if (estado.orElse(-1) == 1) {
				model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservaClienteActividadPendientes());
			} else if (estado.orElse(-1) == 2) {
				model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservaClienteActividadAceptadas());
			} else {
				model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservas());
			}
		}

		return "reservaClienteActividad/list"; 
	}

	@RequestMapping(value="/listCliente", method=RequestMethod.GET) 
	public String listActivitiesCliente(Model model,
                                 @RequestParam("estado") Optional<Integer> estado, HttpSession session){
		
		User user = (User) session.getAttribute("user");
	    Cliente c=clienteDao.getClienteEmail(user.getEmail());
	    model.addAttribute("reservasClienteActividad", reservaClienteActividadDao.getReservasCliente(c.getDni()));
        

		return "reservaClienteActividad/listCliente"; 
	}


	@RequestMapping(value="/cancelar/{id}") 
	public String deleteReserva(Model
		model, @PathVariable String id) { 
			ReservaClienteActividad r=reservaClienteActividadDao.getReservaClienteActividad(id);
			Date fechaHoy= new Date();
			Calendar calendar = new GregorianCalendar(r.getFechaActividad().getYear(), r.getFechaActividad().getMonth()-1, r.getFechaActividad().getDay());
			java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());
			int diasDiferencia = (int)(fechaHoy.getTime()-fecha.getTime());
			System.out.println("%%%%%%%% Diferencia : "+diasDiferencia);
			if (diasDiferencia>1000000000) {
				reservaClienteActividadDao.deleteReserva(id);
				return "redirect:../listCliente";
			}else {
				return "redirect:../cancelacionCancelacion";
			}
		}

	@RequestMapping(value="/cancelacionCancelacion", method=RequestMethod.GET)
	public String listActivitiesAceptadas(Model model) {
		//model.addAttribute("reservaClienteActividadDao", reservaClienteActividadDao.getReservaClienteActividadAceptadas()); 
		return "reservaClienteActividad/cancelacionCancelacion"; 
	}
	/*@RequestMapping(value="/listAceptadas", method=RequestMethod.GET)
	public String listActivitiesAceptadas(Model model) {
		model.addAttribute("reservaClienteActividadDao", reservaClienteActividadDao.getReservaClienteActividadAceptadas()); 
		return "reservaClienteActividad/list"; 
	}*/

}
