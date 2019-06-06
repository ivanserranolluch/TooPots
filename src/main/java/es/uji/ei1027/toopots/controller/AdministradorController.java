package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	private MonitorDao monitorDao;
	private ActividadDao actividadDao;
	private TipoActividadDao tipoActividadDao;
	private ClienteDao clienteDao;
	private ReservaDao reservaDao;

    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao=monitorDao;
    }
    
	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao; 
	}
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao; 
	}

	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao=clienteDao;
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
