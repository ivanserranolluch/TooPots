package es.uji.ei1027.toopots.controller;

import java.util.List;

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
	
	@RequestMapping(value="/listMonitor", method=RequestMethod.GET) 
	public String listActivitiesMonitor(Model model) {
		model.addAttribute("tipoActividad", tipoActividadDao.getTiposActividades()); 
		return "tipoActividad/listMonitor"; 
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
	        List<TipoActividad> listaTa=tipoActividadDao.getTiposActividades();
	        
	        
	        for( TipoActividad ta : listaTa ) {
	        		
	        		if(ta.getNombre().equals(tipoActividad.getNombre()) && ta.getNivel().equals(tipoActividad.getNivel())) {
	        			return "/errores/tipoActividadAdd";
	        		}
	        		
	        }
		
	        tipoActividadDao.addTipoActividad(tipoActividad);
	        
	        return "redirect:list";
	    }
	 
	 
	 @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	    public String updateActividad(Model model, @PathVariable String id) {
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
		 	try {
		 		tipoActividadDao.deleteTipoActividad(id);
			} catch (Exception e) {
				// TODO: handle exception
				return "/tipoActividad/errorTipoActividad"; 
			}
	        return "redirect:../list";
	    }
	 
}
