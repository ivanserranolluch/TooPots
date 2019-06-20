package es.uji.ei1027.toopots.controller;

import java.sql.Date;
import java.sql.Time;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import es.uji.ei1027.toopots.dao.ImgActDao;
import es.uji.ei1027.toopots.model.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;

@Controller
@RequestMapping("/actividad")
public class ActividadController {
	
	private ActividadDao actividadDao;
	private TipoActividadDao tipoActividadDao;
	private ImgActDao imgActDao;
	
	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao; 
	}
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao; 
	}

	@Autowired
	public void setImgActDao(ImgActDao imgActDao){
		this.imgActDao=imgActDao;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model) {
		model.addAttribute("actividades", actividadDao.getActividad()); 
		return "actividad/list"; 
	}
	
	//Nuevo
	@RequestMapping(value="/listActividades", method=RequestMethod.GET) 
	public String listActivitiesMonitor(Model model) {
		model.addAttribute("actividades", actividadDao.getActividad()); 
		return "monitor/listActividades"; 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getActividad(Model model, @PathVariable int id){
		model.addAttribute("actividad", actividadDao.getActividad(id));
		return "actividad/detail";
	}
	
	/*
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addActivitie(){
		// Create new activity type
		TipoActividad ta = new TipoActividad();
		ta.setId(1);
		ta.setNivel("Bajo");
		ta.setNombre("Aire libre");
		tipoActividadDao.addTipoActividad(ta);
		// Create new activity
		Actividad act = new Actividad();
		act.setDescripcion("Competición de tiro con arco al aire libre, con diferentes dianas, monitor" +
				" propio para enseñar a los más novatos y diferentes premios tanto para los mayores" + 
				" como para los más pequeños.");
		act.setDuracionDias(2);
		act.setEstado("D");
		act.setFecha(new Date(2, 6, 2019));
		act.setHoraEncuentro(new Time(10, 0, 0));
		act.setId_actividad(1);
		act.setLugar("Polideportivo de Segorbe");
		act.setMaxAsistentes(30);
		act.setMinAsistentes(5);
		act.setNombre("Tiro con arco");
		act.setPrecio(15);
		act.setPuntoEncuentro("Entrada del Polideportivo de Segorbe");
		act.setTextoCliente("Una experiencia única e irrepetible");
		act.setId_tipoActividad(1);
		actividadDao.addActividad(act);
		return "common/succes";
	}
	*/
	
	@RequestMapping(value="/add")
    public String addActividad(Model model) {

	 System.out.println("hola");
        model.addAttribute("actividad", new Actividad());
        return "actividad/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("actividad") Actividad actividad,
                                   BindingResult bindingResult, Model model) {

        //if (bindingResult.hasErrors()){
		//	return "redirect:/singup";
       // }

		actividad.setEstado("pendiente");
        	//actividad.setId_actividad(1);
        	System.out.println(actividad.getId_actividad()+" "+actividad.getId_tipoActividad());
        actividadDao.addActividad(actividad);

        return "redirect:listActividades";
    }


    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public String updateActividad(Model model, @PathVariable String id) {
    	System.out.println("HOA");
        model.addAttribute("actividad", actividadDao.getActividad(Integer.parseInt(id)));
        return "actividad/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("actividad") Actividad actividad,
                                      BindingResult bindingResult) {
        //if (bindingResult.hasErrors()) {
        	//System.out.println(bindingResult);
          //  return "actividad/update";
        //}

        actividadDao.updateActividad(actividad);

        //if (actividad.getEstado().equals("aceptada")) {
       	//mailService.sendMail("al342376@uji.es", monitor.getEmail(), "Aceptado como Monitor", "Su solicitud como monitor, ha sido aceptada.");
        	//System.out.println("Se ha enviado un correo al monitor");
		//}
        return "redirect:../listActividades";
    }



    @RequestMapping(value="/delete/{id}")
    public String deleteActividad(Model model, @PathVariable String id) {
        actividadDao.deleteActividad(Integer.parseInt(id));
        return "redirect:../listActividades";
    }

	@RequestMapping(value="/listaActividadesPorTipo/{tipo}", method=RequestMethod.GET)
	public String pageActividadesTipo(Model model, @PathVariable String tipo) {
		model.addAttribute("actividades", actividadDao.getActividadPorTipo(tipo));
		return "actividad/listaActividadesPorTipo";
	}

	@RequestMapping(value="/actividadinfo/{id}", method=RequestMethod.GET)
	public String pageActividad(Model model, @PathVariable int id) {
		//System.out.println(id);
		model.addAttribute("actividad", actividadDao.getActividad(id));
		model.addAttribute("imgurl", imgActDao.getImageActividad(id).getUrl());
		return "actividad/actividadinfo";
	}

	
	
	@RequestMapping(value="/kayak") 
	public String pageKayak(Model model) {
		return "actividad/kayak"; 
	}
	
	@RequestMapping(value="/rutasCaballo") 
	public String pageRutaCaballos(Model model) {
		return "actividad/rutasCaballo"; 
	}
	
	@RequestMapping(value="/paracaidismo") 
	public String pageParacaidismo(Model model) {
		return "actividad/paracaidismo"; 
	}
	
	@RequestMapping(value="/senderismo") 
	public String pageSenderismo(Model model) {
		return "actividad/senderismo"; 
	}
	
	@RequestMapping(value="/actividades") 
	public String pageActividades(Model model) {
		return "actividad/actividades"; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
