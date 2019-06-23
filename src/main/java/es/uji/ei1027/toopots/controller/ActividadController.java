package es.uji.ei1027.toopots.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import es.uji.ei1027.toopots.dao.*;
import es.uji.ei1027.toopots.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/actividad")
public class ActividadController {
	
	private ActividadDao actividadDao;
	private TipoActividadDao tipoActividadDao;
	private ImgActDao imgActDao;
	private MonitoresActividadDao monitoresActividadDao;
	private MonitorDao monitorDao;
	
	@Value("${upload.file.directory}")
    private String uploadDirectory;

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

	@Autowired
	public void setMonitoresActividadDao(MonitoresActividadDao monitoresActividadDao){
		this.monitoresActividadDao=monitoresActividadDao;
	}

	@Autowired
	public void setMonitorDao(MonitorDao monitorDao){
		this.monitorDao=monitorDao;
	}

	@RequestMapping(value="/list", method=RequestMethod.GET) 
	public String listActivities(Model model) {
		model.addAttribute("actividades", actividadDao.getActividad());
		return "actividad/list"; 
	}
	
	//Nuevo
	
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
    public String addActividad(Model model, HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	String idMonitor = monitorDao.getMonitorEmail(user.getEmail()).getId();
    	
    	model.addAttribute("tipo", tipoActividadDao.getTiposActividadesCertificadas(idMonitor));
		model.addAttribute("actividad", new Actividad());
        return "actividad/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("actividad") Actividad actividad,
								   @ModelAttribute("tipo") String t,
								   @RequestParam("hora") String h,
								   BindingResult bindingResult,
								   @RequestParam("file") MultipartFile file,
								   Model model,
								   HttpSession session) {

        //if (bindingResult.hasErrors()){
		//	return "redirect:/singup";
       // }


    	try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "images/" + actividad.getId_actividad() + actividad.getNombre() + ".jpg");
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

		MonitoresActividad monitoresActividad = new MonitoresActividad();
		User user = (User)session.getAttribute("user");
		Monitor monitor = monitorDao.getMonitorEmail(user.getEmail());
    	String[] hour = h.split(":");
    	
    	int hora = Integer.parseInt(hour[0]);
    	int minuto = Integer.parseInt(hour[1]);
    	
    	java.sql.Time timeValue = new java.sql.Time(hora, minuto, 00);
    	
    	actividad.setHoraEncuentro(timeValue);
    	
    	int idActividad = 0;
		for(TipoActividad a: tipoActividadDao.getTiposActividades()) {
    		if(a.getNombre().equals(t)) {
        		idActividad = a.getId();
    		}
    	}	

		actividad.setId_tipoActividad(idActividad);
		actividad.setEstado("abierta");
		
        actividadDao.addActividad(actividad);


        monitoresActividad.setId_actividad(actividad.getId_actividad());
        monitoresActividad.setId_monitor(monitor.getId());
        monitoresActividadDao.addActividad(monitoresActividad);


        //AÑADIR SERIALIZABLE PARA LA IMAGEN
        
        String url = "/images/" + actividad.getId_actividad() + actividad.getNombre() + ".jpg";
        imgActDao.addImagen(actividad.getId_actividad(),actividad.getId_actividad(), url);

        return "redirect:/actividad/listActividades";
    }


    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public String updateActividad(Model model, @PathVariable String id) {
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
        return "redirect:/actividad/list";
    }


    //BORRAR ACTIVIDAD
    @RequestMapping(value="/delete/{id}")
    public String deleteActividad(Model model, @PathVariable String id) {
        actividadDao.deleteActividad(Integer.parseInt(id));
        return "/actividad/list";
    }
    
    //LISTAR ACTIVIDADES POR TIPO	
	@RequestMapping(value="/listaActividadesPorTipo/{tipo}", method=RequestMethod.GET)
	public String pageActividadesTipo(Model model, @PathVariable String tipo) {


	    List<Actividad> listaActividades = actividadDao.getActividadPorTipo(tipo);
	    List<ImgAct> listaImgAct = new ArrayList<>();

	    for(Actividad actividad : listaActividades){
	        ImgAct aux = imgActDao.getImageActividad(actividad.getId_actividad());

	        if(aux.getUrl().equals("")) {
                aux.setId_actividad(actividad.getId_actividad());
                aux.setUrl("/images/default.jpg");
            }
            listaImgAct.add(aux);
        }


		model.addAttribute("actividades", listaActividades);
		model.addAttribute("tipo", tipo);
        model.addAttribute("imagenes", listaImgAct);
		return "actividad/listaActividadesPorTipo";
	}

	@RequestMapping(value="/actividadinfo/{id}", method=RequestMethod.GET)
	public String pageActividad(Model model, @PathVariable int id) {
		//System.out.println(id);
		model.addAttribute("reserva", new Reserva());
		model.addAttribute("actividad", actividadDao.getActividad(id));
		model.addAttribute("imgurl", imgActDao.getImageActividad(id).getUrl());
		return "actividad/actividadinfo";
	}
	
	@RequestMapping(value="/actividadInfoReserva/{id}", method=RequestMethod.GET)
	public String pageActividadReserva(Model model, @PathVariable int id) {
		//System.out.println(id);

		model.addAttribute("actividad", actividadDao.getActividad(id));
		model.addAttribute("imgurl", imgActDao.getImageActividad(id).getUrl());
		return "actividad/actividadInfoReserva";
	}

	@RequestMapping(value="/listActividades", method=RequestMethod.GET)
	public String listActivitiesMonitor(Model model, HttpSession session) {
		//Añadir excepcion
		User user = (User) session.getAttribute("user");
		Monitor monitor = monitorDao.getMonitorEmail(user.getEmail());
		List<MonitoresActividad> ma =  monitoresActividadDao.getActividadesMonitor(monitor.getId());
		List<Actividad> actividades =  new ArrayList<>();
		for (MonitoresActividad aux : ma){
			actividades.add(actividadDao.getActividad(aux.getId_actividad()));
		}

		model.addAttribute("actividades", actividades);
		return "actividad/listActividades";
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
