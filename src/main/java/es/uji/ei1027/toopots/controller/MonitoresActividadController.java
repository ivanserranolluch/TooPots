package es.uji.ei1027.toopots.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.dao.MonitoresActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.MonitoresActividad;

@Controller
@RequestMapping("/monitorActividad")
public class MonitoresActividadController {
    private MonitoresActividadDao monitorActividad;
    private MonitorDao monitor;
    private ActividadDao actividad;
  
    @Autowired
    public void setMonitoresActividadDao(MonitoresActividadDao monitorActividad) {
        this.monitorActividad=monitorActividad;
    }
    
    @Autowired
    public void setMonitores(MonitorDao monitor) {
        this.monitor=monitor;
    }
    
    @Autowired
    public void setActividad(ActividadDao actividad) {
        this.actividad=actividad;
    }
    
    

    @RequestMapping(value="/add")
    public String Actividad(Model model) {
        model.addAttribute("monitorActividad", new MonitoresActividad());
        return "monitoresActividad/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("monitorActividad") MonitoresActividad monitoractividad,
                                   BindingResult bindingResult) {
    	monitorActividad.addActividad(monitoractividad);
        if (bindingResult.hasErrors())
            return "monitoresActividad/add";
       
        return "redirect:list";
    }
    

    @RequestMapping("/list")
    public String listMonitorActividad(Model model) {
    	Actividad mostrar = new Actividad();
    	List<MonitoresActividad> monitoresActividad = monitorActividad.getActividad();
    	for (MonitoresActividad monitoresActividad2 : monitoresActividad) {
    		mostrar.setEstado(monitor.getMonitor(monitoresActividad2.getId_monitor()).getNombre());
    		mostrar.setDescripcion(actividad.getActividad(monitoresActividad2.getId_actividad()).getNombre());
			
		}
    	
    	model.addAttribute("monitoresActividad", mostrar);
        return "monitoresActividad/list";
    }
    

/*
    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public String updateMonitorActividad(Model model, @PathVariable String id_monitor) {
        model.addAttribute("monitorActividad", monitorActividad.);
        return "monitor/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("monitor") Monitor monitor,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "monitor/update";
        monitorDao.updateMonitor(monitor);
        
        if (monitor.getEstado().equals("aceptada")) {
       	mailService.sendMail("al342376@uji.es", monitor.getEmail(), "Aceptado como Monitor", "Su solicitud como monitor, ha sido aceptada.");
        	
		}
        return "redirect:../list";
    }
*/

    

  

}