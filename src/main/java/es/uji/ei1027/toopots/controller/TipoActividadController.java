package es.uji.ei1027.toopots.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import es.uji.ei1027.toopots.dao.TipoActividadDao;

import es.uji.ei1027.toopots.model.TipoActividad;


@Controller
@RequestMapping("/tipoActividad")
public class TipoActividadController {
		
	private TipoActividadDao tipoActividadDao;
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao; 
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model) {
		model.addAttribute("tipoActividad", tipoActividadDao.getTiposActividades()); 
		return "tipoActividad/list"; 
	}
	
	 @RequestMapping(value="/add")
	    public String addTipoActividad(Model model) {
	        model.addAttribute("tipoActividad", new TipoActividad());
	        return "tipoActividad/add";
	    }
	 @RequestMapping(value="/add", method=RequestMethod.POST)
	    public String processAddSubmit(@ModelAttribute("tipoActividad") TipoActividad tipoActividad,
	                                   BindingResult bindingResult, Model model) {
	       /* if (bindingResult.hasErrors()){
				return "tipoActividad/add";
	        }*/
	        
		
	        tipoActividadDao.addTipoActividad(tipoActividad);
	        
	        return "redirect:list";
	    }
	 
	 
	 @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	    public String updateActividad(Model model, @PathVariable String id) {
	    	System.out.println("HOA");
	        model.addAttribute("tipoActividad", tipoActividadDao.getTipoActividad(id));
	        return "tipoActividad/update";
	    }
	 
	 @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
	    public String processUpdateSubmit(@PathVariable String id,
	                                      @ModelAttribute("actividad") TipoActividad tipoActividad,
	                                      BindingResult bindingResult) {
	        //if (bindingResult.hasErrors()) {
	        	//System.out.println(bindingResult);
	          //  return "actividad/update";
	        //}

	        tipoActividadDao.updateTipoActividad(tipoActividad);

	        //if (actividad.getEstado().equals("aceptada")) {
	       	//mailService.sendMail("al342376@uji.es", monitor.getEmail(), "Aceptado como Monitor", "Su solicitud como monitor, ha sido aceptada.");
	        	//System.out.println("Se ha enviado un correo al monitor");
			//}
	        return "redirect:../list";
	    }
	 
	 @RequestMapping(value="/delete/{id}")
	    public String deleteActividad(Model model, @PathVariable String id) {
	        tipoActividadDao.deleteTipoActividad(id);
	        return "redirect:../list";
	    }
	 
}
