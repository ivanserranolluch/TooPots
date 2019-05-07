package es.uji.ei1027.toopots.controller;

import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.TipoActividad;

@Controller
@RequestMapping("/actividad")
public class ActividadController {
	
	private ActividadDao actividadDao;
	private TipoActividadDao tipoActividadDao;
	
	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao; 
	}
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao; 
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model) {
		model.addAttribute("actividades", actividadDao.getActividad()); 
		return "actividad/list"; 
	}
	
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

}
