package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Reserva;

@Controller
@RequestMapping("/reserva")
public class ReservaController {
	
	private ReservaDao reservaDao;
	
	@Autowired
	public void setReservaDao(ReservaDao reservaDao) {
		this.reservaDao=reservaDao; 
	}

	@RequestMapping(value="/add")
	public String addReserva(Model model) {
		model.addAttribute("reserva", new Reserva()); 
		return "reserva/add"; 
	}

	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva, BindingResult
			bindingResult) { 
		/*if (bindingResult.hasErrors()) 
			return "reserva/add";*/
		reservaDao.addReserva(reserva); 
		return "redirect:list"; 
	}

	@RequestMapping(value="/update/{id}", method=RequestMethod.GET) 
	public String updateReserva(Model model, @PathVariable String id) {
		model.addAttribute("reserva", reservaDao.getReserva(id)); 
			return "reserva/update";
		}

	@RequestMapping(value="/update/{id}", method = RequestMethod.POST) public String processUpdateSubmit(@PathVariable String id,
			@ModelAttribute("reserva") Reserva reserva, BindingResult bindingResult) { 
		if(bindingResult.hasErrors()) 
			return "reserva/update";
		reservaDao.updateReserva(reserva);
		return "redirect:../list"; }


	@RequestMapping(value="/delete/{id}") 
	public String deleteReserva(Model
		model, @PathVariable String id) { 
			reservaDao.deleteReserva(id);
			return "redirect:../list";
		}

	@RequestMapping("/list") 
	public String listReservas(Model model) {
		model.addAttribute("reservas", reservaDao.getReservas()); 
			return "reserva/list"; 
		}



}