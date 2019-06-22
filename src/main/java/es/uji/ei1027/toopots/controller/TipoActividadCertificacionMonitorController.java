package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.TipoActividadCertificacionMonitorDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;

@Controller
@RequestMapping("/tipoActividadCertificacionMonitor")
public class TipoActividadCertificacionMonitorController {

private TipoActividadCertificacionMonitorDao tipoActividadCertificacionMonitorDao;
	
	@Autowired
	public void setTipoActividadDao(TipoActividadCertificacionMonitorDao tipoActividadCertificacionMonitorDao) {
		this.tipoActividadCertificacionMonitorDao = tipoActividadCertificacionMonitorDao; 
	}
	
	@RequestMapping(value="/list/{id}", method=RequestMethod.GET) 
	public String listActivities(Model model, @PathVariable String id) {
		model.addAttribute("tipoActividadCertificacionMonitor", tipoActividadCertificacionMonitorDao.getTiposActividades(id)); 
		return "tipoActividadCertificacionMonitor/list"; 
	}
}
