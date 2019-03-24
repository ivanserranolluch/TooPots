package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.PreferenciasClienteDao;
import es.uji.ei1027.toopots.model.PreferenciasCliente;

@Controller
@RequestMapping("/preferenciascliente")
public class PreferenciasClienteController {
	
	private PreferenciasClienteDao preferenciasClienteDao;
	
	@Autowired
	public void setPreferenciasClienteDao(PreferenciasClienteDao preferenciasClienteDao) {
		this.preferenciasClienteDao=preferenciasClienteDao; 
	}

	@RequestMapping(value="/add")
	public String addPreferenciasCliente(Model model) {
		model.addAttribute("preferenciasCliente", new PreferenciasCliente()); 
		return "preferenciascliente/add"; 
	}

	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("preferenciasCliente") PreferenciasCliente preferenciasCliente, BindingResult
			bindingResult) { 
		if (bindingResult.hasErrors()) 
			return "preferenciascliente/add";
		preferenciasClienteDao.addPreferenciasCliente(preferenciasCliente); 
		return "redirect:list"; 
	}

	@RequestMapping(value="/update/{id}", method=RequestMethod.GET) 
	public String updatePreferenciasCliente(Model model, @PathVariable String id) {
		model.addAttribute("preferenciasCliente", preferenciasClienteDao.getPreferenciasCliente(id)); 
			return "preferenciascliente/update";
		}

	@RequestMapping(value="/update/{id}", method = RequestMethod.POST) public String processUpdateSubmit(@PathVariable String id,
			@ModelAttribute("preferenciasCliente") PreferenciasCliente preferenciasCliente, BindingResult bindingResult) { 
		if(bindingResult.hasErrors()) 
			return "preferenciascliente/update";
		preferenciasClienteDao.updatePreferenciasCliente(preferenciasCliente);
		return "redirect:../list"; }


	@RequestMapping(value="/delete/{id}") 
	public String deletePreferenciasCliente(Model
		model, @PathVariable String id) { 
			preferenciasClienteDao.deletePreferenciasCliente(id);
			return "redirect:../list";
		}

	@RequestMapping("/list") 
	public String listPreferenciasCliente(Model model) {
		model.addAttribute("preferenciasCliente", preferenciasClienteDao.getPreferenciasClientes()); 
			return "preferenciascliente/list"; 
		}



}
