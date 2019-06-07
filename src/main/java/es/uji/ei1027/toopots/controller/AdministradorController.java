package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.dao.ReservaDao;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	private MonitorDao monitorDao;
	private ReservaDao reservaDao;

    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao=monitorDao;
    }
    

	@Autowired
    public void serReservaDao(ReservaDao reservaDao) {
        this.reservaDao=reservaDao;
    }

	@RequestMapping("/lobby")
	public String administrador(Model model) {
        model.addAttribute("numReservas", reservaDao.getNumReservas());
        model.addAttribute("numClientes", reservaDao.getNumClientes());
        model.addAttribute("numActividades", reservaDao.getNumActividades());
        model.addAttribute( "numPendientes", monitorDao.getNumPendientes());
        model.addAttribute("numMonitores", monitorDao.getNumMonitores());
		return "admin/lobby";
	}
	
	
	
	
	

}
