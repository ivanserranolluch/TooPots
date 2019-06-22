package es.uji.ei1027.toopots.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.CertificacionDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Monitor;

@Controller
@RequestMapping("/certificacion")
public class CertificacionController {
	
	private CertificacionDao certificacionDao;
	private MonitorDao monitorDao;
	
	@Autowired
    public void setCertificacionDao(CertificacionDao certificacionDao) {
        this.certificacionDao=certificacionDao;
    }
	@Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao=monitorDao;
    }
	
	@RequestMapping(value="/listAdmin", method=RequestMethod.GET) 
	public String listCertificaciones(Model model) {
		List<Monitor> monitores= new ArrayList<Monitor>();
		for(Monitor m : monitorDao.getMonitoresRegistrados())
    			monitores.add(m);
		
		model.addAttribute("certificaciones", certificacionDao.getCertificacion()); 
		model.addAttribute("monitor", monitores);
		
		return "certificacion/listAdmin";
	}
	
	
	
}
