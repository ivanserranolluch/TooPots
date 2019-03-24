package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.CertificacionesTipoActividadDao;
import es.uji.ei1027.toopots.model.CertificacionesTipoActividad;

@Controller
@RequestMapping("/certificacionestipoactividad")
public class CertificacionesTipoActividadController {
	
	private CertificacionesTipoActividadDao certificacionesTipoActividadDao;
	
	@Autowired
	public void setPreferenciasClienteDao(CertificacionesTipoActividadDao certificacionesTipoActividadDao) {
		this.certificacionesTipoActividadDao=certificacionesTipoActividadDao; 
	}

	@RequestMapping(value="/add")
	public String addCertificacionesTipoActividad(Model model) {
		model.addAttribute("certificacionActividad", new CertificacionesTipoActividad()); 
		return "certificacionestipoactividad/add"; 
	}

	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("certificacionActividad") CertificacionesTipoActividad certificacionesTipoActividad, BindingResult
			bindingResult) { 
		if (bindingResult.hasErrors()) 
			return "preferenciascliente/add";
		certificacionesTipoActividadDao.addCertificacionesTipoActividad(certificacionesTipoActividad); 
		return "redirect:list"; 
	}

	@RequestMapping(value="/update/{id}", method=RequestMethod.GET) 
	public String updateCertificacionesTipoActividad(Model model, @PathVariable String id) {
		model.addAttribute("certificacionActividad", certificacionesTipoActividadDao.getCertificacionesTipoActividad(id)); 
			return "certificacionestipoactividad/update";
		}

	@RequestMapping(value="/update/{id}", method = RequestMethod.POST) public String processUpdateSubmit(@PathVariable String id,
			@ModelAttribute("certificacionActividad") CertificacionesTipoActividad certificacionesTipoActividad, BindingResult bindingResult) { 
		if(bindingResult.hasErrors()) 
			return "preferenciascliente/update";
		certificacionesTipoActividadDao.updateCertificacionesTipoActividad(certificacionesTipoActividad);;
		return "redirect:../list"; }


	@RequestMapping(value="/delete/{id}") 
	public String deleteCertificacionesTipoActividad(Model
		model, @PathVariable String id) { 
			certificacionesTipoActividadDao.deleteCertificacionesTipoActividad(id);;
			return "redirect:../list";
		}

	@RequestMapping("/list") 
	public String listPreferenciasCliente(Model model) {
		model.addAttribute("certificacionActividad", certificacionesTipoActividadDao.getCertificacionesTipoActividad()); 
			return "certificacionestipoactividad/list"; 
		}



}
