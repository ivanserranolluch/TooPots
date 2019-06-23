package es.uji.ei1027.toopots.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import es.uji.ei1027.toopots.dao.CertificacionDao;
import es.uji.ei1027.toopots.dao.CertificacionMonitorDao;
import es.uji.ei1027.toopots.dao.CertificacionesTipoActividadDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.dao.SerialDao;
import es.uji.ei1027.toopots.model.Certificacion;
import es.uji.ei1027.toopots.model.CertificacionMonitor;
import es.uji.ei1027.toopots.model.CertificacionesTipoActividad;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.Serial;
import es.uji.ei1027.toopots.model.User;
import es.uji.ei1027.toopots.service.MailService;

@Controller
@RequestMapping("/certificacionMonitor")
public class CertificacionMonitorController {
	
	private MonitorDao monitorDao;
	private CertificacionDao certificacionDao;
	private CertificacionMonitorDao certificacionMonitorDao;
	private MailService mailService;
	private CertificacionesTipoActividadDao certificacionesTipoActividadDao;
	private SerialDao serialDao;
	
	@Value("${upload.file.directory}")
    private String uploadDirectory;
	
	
	 @Autowired
	    public void setMailService(MailService mailService) {
	        this.mailService=mailService;
	    }
	 @Autowired
    public void setCertificacionMonitorDao(CertificacionMonitorDao certificacionMonitorDao) {
        this.certificacionMonitorDao=certificacionMonitorDao;
    }
	 @Autowired
	    public void setSerialDao(SerialDao serialDao) {
	        this.serialDao=serialDao;
	    }
	 
	 @Autowired
	    public void setCertificacionesTipoActividadDao(CertificacionesTipoActividadDao certificacionesTipoActividadDao) {
	        this.certificacionesTipoActividadDao=certificacionesTipoActividadDao;
	    }
	 
	 @Autowired
	    public void setMonitorDao(MonitorDao monitorDao) {
	        this.monitorDao=monitorDao;
	    }
	 
	 @Autowired
	    public void setCertificacionDao(CertificacionDao certificacionDao) {
	        this.certificacionDao=certificacionDao;
	    } 
	 
	 @RequestMapping(value="/listAdmin", method=RequestMethod.GET) 
		public String listCertificaciones(Model model, HttpSession session) {
			User user= (User) session.getAttribute("user");
			model.addAttribute("certificacionesMonitor", certificacionMonitorDao.listCertificacionMonitor()); 
			return "certificacionMonitor/listAdmin";
		}
	 
	 @RequestMapping(value="/listAdminAceptadas", method=RequestMethod.GET) 
		public String listCertificacionesAceptasdas(Model model) {
			
			model.addAttribute("certificacionesMonitor", certificacionMonitorDao.listCertificacionMonitorAceptadas()); 
			return "certificacionMonitor/listAdminAceptadas";
		}
	 
	 @RequestMapping(value="/add/{id}")
	    public String addCertificacion(Model model, @PathVariable String id, HttpSession session) {
		 	model.addAttribute("id_tipoActividad", id);
	        model.addAttribute("certificacionMonitor", new Certificacion());
	        //model.addAttribute("certificacionTipoActividad", new CertificacionesTipoActividad());
	        return "certificacionMonitor/add";
	    }
	 
	 @RequestMapping(value="/add/{id}", method=RequestMethod.POST)
	    public String processAddSubmit(@ModelAttribute("certificacion") Certificacion certificacion,
	                                   BindingResult bindingResult, Model model, 
	                                   @RequestParam("file") MultipartFile file, HttpSession session, @PathVariable String id) {
	        if (bindingResult.hasErrors()){
				return "redirect:/";
	        }
	        Serial s= serialDao.obtenerIdCertUltimo();
	        User user= (User) session.getAttribute("user");
	        Monitor m= monitorDao.getMonitorEmail(user.getEmail());
	        
	        certificacion.setId_certificacion(s.getSec_certificacion()+1);
	        System.out.println(certificacion.getId_certificacion());
	        	        
	        try {
	            // Obtener el fichero y guardarlo
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(uploadDirectory + "pdfs/" 
	                                          +user.getEmail() +"" +certificacion.getId_certificacion());
	            Files.write(path, bytes);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        	   
	        certificacion.setId_monitor(Integer.parseInt(m.getId()));
	        certificacion.setEstado("pendiente");
	        certificacion.setRutaCertificado(""+uploadDirectory + "pdfs/" 
	        		+user.getEmail() +"/" +certificacion.getId_certificacion());
	        	System.out.println(certificacion.getRutaCertificado());
	        certificacionDao.insertCertificacion(certificacion);
	        
	        certificacionesTipoActividadDao.addCertificacionesTipoActividad(new CertificacionesTipoActividad(certificacion.getId_certificacion(),Integer.parseInt(id)));
	        
	        return "certificacionMonitor/success";
	    }
	 /*
	 @RequestMapping(value="/add")
	    public String addCertificacion(Model model) {
	        model.addAttribute("certificacionMonitor", new Certificacion());
	        return "certificacionMonitor/add";
	    }
	 
	 @RequestMapping(value="/add", method=RequestMethod.POST)
	    public String processAddSubmit(@ModelAttribute("certificacion") Certificacion certificacion,
	                                   BindingResult bindingResult, Model model, 
	                                   @RequestParam("file") MultipartFile file, HttpSession session) {
	        if (bindingResult.hasErrors()){
				return "redirect:/";
	        }
	        
	        User user= (User) session.getAttribute("user");
	        Monitor m= monitorDao.getMonitorEmail(user.getEmail());
	        	        
	        try {
	            // Obtener el fichero y guardarlo
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(uploadDirectory + "pdfs/" 
	                                          +user.getEmail() +"" +certificacion.getId_certificacion());
	            Files.write(path, bytes);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        	   
	        certificacion.setId_monitor(Integer.parseInt(m.getId()));
	        certificacion.setEstado("pendiente");
	        certificacion.setRutaCertificado(""+uploadDirectory + "pdfs/" 
	        		+user.getEmail() +"/" +certificacion.getId_certificacion());
	        	
	        certificacionDao.addCertificacion(certificacion);
	        
	        return "certificacionMonitor/success";
	    }*/
	    
	 
	   @RequestMapping(value="/pdf/{id}", method=RequestMethod.GET)
	    public String pdfMonitor(Model model, @PathVariable String id, HttpSession session) {
		   	CertificacionMonitor cm = certificacionMonitorDao.getCertificacionMonitor(Integer.parseInt(id));
		   	System.out.println(cm.getId_certificacion());
		   	Certificacion c=certificacionDao.getCertificacion(Integer.parseInt(id));
		   	Monitor m = monitorDao.getMonitor(String.valueOf(c.getId_monitor()));
		   	String email = m.getEmail();
		   	String ruta = email +cm.getId_certificacion();
		   	System.out.println(ruta);
		   	model.addAttribute("ruta", ruta);
	        model.addAttribute("certificacion", certificacionMonitorDao.getCertificacionMonitor(email,cm.getId_certificacion()));
	        
	        return "certificacionMonitor/pdf";
	    }
	   
	   @RequestMapping(value="/pdf2/{id}", method=RequestMethod.GET)
	    public String pdfMonitor2(Model model, @PathVariable String id, HttpSession session) {
		   	CertificacionMonitor cm = certificacionMonitorDao.getCertificacionMonitor(Integer.parseInt(id));
		   	System.out.println(cm.getId_certificacion());
		   	Certificacion c=certificacionDao.getCertificacion(Integer.parseInt(id));
		   	Monitor m = monitorDao.getMonitor(String.valueOf(c.getId_monitor()));
		   	String email = m.getEmail();
		   	String ruta = email +cm.getId_certificacion();
		   	System.out.println(ruta);
		   	model.addAttribute("ruta", ruta);
	        model.addAttribute("certificacion", certificacionMonitorDao.getCertificacionMonitor(email,cm.getId_certificacion()));
	        
	        return "certificacionMonitor/pdf2";
	    }
	   
	   /*
	   @RequestMapping(value="/update/{id}")
	    public String updateCertificacion(Model model, @PathVariable String id) {
	        model.addAttribute("certificacion",certificacionMonitorDao.getCertificacionMonitor(Integer.parseInt(id)));
	        return "certificacionMonitor/update";
	    }
*/
	   
	   @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
	    public String processUpdateSubmit(@PathVariable String id,
	                                      @ModelAttribute("certificacion") Certificacion certificacion,
	                                      BindingResult bindingResult,
	                                      @RequestParam(value="action", defaultValue="vacio") String action) {
	        if (bindingResult.hasErrors())
	            return "/administrador/lobby";
	        
	        if (action.equals("vacio")) {
				action = certificacion.getEstado();
			}
	        
	        if (action.equals("aceptada"))
	            certificacion.setEstado("aceptada");
	        else if (action.equals("rechazada"))
	        	certificacion.setEstado("rechazada");

	        certificacionDao.updateCertificacion(certificacion);
	        Monitor m = monitorDao.getMonitor(String.valueOf(certificacion.getId_monitor()));
	        
	        System.out.println(m.getEmail());
	        if (certificacion.getEstado().equals("aceptada")) {
	        
	        		mailService.sendMail("pruebasuji@gmail.es", "ivanserranolluch@gmail.com", "Aceptada su solicitud", "Su certificacion ha sido aceptada.");
			} 
	        
	        if (certificacion.getEstado().equals("rechazada")) {
	        	
	        		mailService.sendMail("pruebasuji@gmail.es", "ivanserranolluch@gmail.com", "Rechazado su  Certificación", "Su certificación ha sido rechazada.");
			} 
	        
	       
	        return "redirect:/certificacionMonitor/listAdmin";
	  
	       
	    }
	   
	   @RequestMapping(value="/update2/{id}", method = RequestMethod.POST)
	    public String processUpdateSubmit2(@PathVariable String id,
	                                      @ModelAttribute("certificacion") Certificacion certificacion,
	                                      BindingResult bindingResult,
	                                      @RequestParam(value="action", defaultValue="vacio") String action) {
	        if (bindingResult.hasErrors())
	            return "/administrador/lobby";
	        
	        if (action.equals("vacio")) {
				action = certificacion.getEstado();
			}
	        
	        if (action.equals("aceptada"))
	            certificacion.setEstado("aceptada");
	        else if (action.equals("rechazada"))
	        	certificacion.setEstado("rechazada");

	        certificacionDao.updateCertificacion(certificacion);
	        Monitor m = monitorDao.getMonitor(String.valueOf(certificacion.getId_monitor()));
	        
	        System.out.println(m.getEmail());
	        if (certificacion.getEstado().equals("aceptada")) {
	        
	        		mailService.sendMail("pruebasuji@gmail.es", "ivanserranolluch@gmail.com", "Aceptada su solicitud", "Su certificacion ha sido aceptada.");
	        		
			} 
	        
	        if (certificacion.getEstado().equals("rechazada")) {
	        	
	        		mailService.sendMail("pruebasuji@gmail.es", "ivanserranolluch@gmail.com", "Rechazado su  Certificación", "Su certificación ha sido rechazada.");
	        		
			} 
	        
	       
	        return "redirect:/certificacionMonitor/listAdminAceptadas";
	  
	       
	    }
	 
}
