package es.uji.ei1027.toopots.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.jasypt.util.password.BasicPasswordEncryptor;
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

import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.dao.UsuariosRegistradosDao;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.User;
import es.uji.ei1027.toopots.service.MailService;

@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private MonitorDao monitorDao;
    private MailService mailService;
    
    @Value("${upload.file.directory}")
    private String uploadDirectory;
    
    private UsuariosRegistradosDao userDao;
	private BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao=monitorDao;
    }

	@Autowired
	public void setUsuariosRegistradosDao(UsuariosRegistradosDao userDao) {
		this.userDao=userDao;
	}
    
    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService=mailService;
    }

    @RequestMapping(value="/add")
    public String addMonitor(Model model) {
        model.addAttribute("monitor", new Monitor());
        return "monitor/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("monitor") Monitor monitor,
                                   BindingResult bindingResult, Model model, 
                                   @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()){
			return "redirect:/singup";
        }
        
        try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "pdfs/" 
                                          + monitor.getId());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        User user = new User();
		user.setEmail(monitor.getEmail());
		user.setPassword(passwordEncryptor.encryptPassword(monitor.getPasswd()));
		user.setTipoUsuario("monitor");
		
		monitor.setEstado("pendiente");
        	
        monitorDao.addMonitor(monitor);
        userDao.addUsuario(user);
        
        return "monitor/success";
    }
    
    @RequestMapping(value="/pdf/{id}", method=RequestMethod.GET)
    public String pdfMonitor(Model model, @PathVariable String id) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        return "monitor/pdf";
    }

    
    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public String updateMonitor(Model model, @PathVariable String id) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        return "monitor/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("monitor") Monitor monitor,
                                      BindingResult bindingResult,
                                      @RequestParam(value="action", defaultValue="vacio") String action) {
        if (bindingResult.hasErrors())
            return "monitor/update";


        if (action.equals("vacio")) {
			action = monitor.getEstado();
		}
        

        if (action.equals("aceptada"))
            monitor.setEstado("aceptada");
        else if (action.equals("rechazada"))
            monitor.setEstado("rechazada");


        monitorDao.updateMonitor(monitor);
        
        if (monitor.getEstado().equals("aceptada")) {

        	mailService.sendMail("pruebasuji@gmail.es", monitor.getEmail(), "Aceptado como Monitor", "Su solicitud como monitor ha sido aceptada.");	
		} 
        

        if (monitor.getEstado().equals("rechazada")) {
        	mailService.sendMail("pruebasuji@gmail.es", monitor.getEmail(), "Rechazado como Monitor", "Su solicitud como monitor ha sido rechazada.");	
		} 
        
        if (action.equals("aceptada") || action.equals("rechazada")) {
        	 return "redirect:/monitor/list?pen=0";
  
		}
		
        return "redirect:/monitor/list?pen=1";
    }
	
    /*
    @RequestMapping(value="/perfil/{email}", method=RequestMethod.GET)
    public String updateMonitorEmail(Model model, @PathVariable String email) {
        model.addAttribute("monitor", monitorDao.getMonitorEmail(email));
        return "monitor/perfil";
    }

    @RequestMapping(value="/perfil/{email}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String email,
                                      @ModelAttribute("monitor") Monitor monitor,
                                      @RequestParam(value="file",required=false) MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "monitor/perfil";

        
        monitorDao.updateMonitor(monitor);
        

        return "monitor/lobby";
    }
    */
    
    @RequestMapping(value="/delete/{id}")
    public String deleteMonitor(Model model, @PathVariable String id) {
        monitorDao.deleteMonitor(id);
        return "redirect:../list";
    }

    @RequestMapping("/list")
    public String listMonitores(Model model, @RequestParam("pen") Optional<Integer> pen, 
    		@RequestParam("busca") Optional<String> busca, HttpSession session) {

    	String buscar = busca.orElse("None");
    	
    	if (!buscar.isEmpty()) {
    		model.addAttribute("busca", buscar);
    	} else {
    		//No se como NO COLOREAR si se pone vacio
    		model.addAttribute("busca", "|");
    	}
    	
        if(pen.orElse(0) == 0) {
            model.addAttribute("monitores", monitorDao.getMonitoresRegistrados());
            model.addAttribute("pen", "0");
            session.setAttribute("bus", 0);
            
            
        }else if(pen.orElse(1) == 1) {
            model.addAttribute("monitores", monitorDao.getMonitoresPendientes());
            model.addAttribute("pen", "1");
            session.setAttribute("pen", 1);

            
        }else if(pen.orElse(2) == 2) {
        	model.addAttribute("pen", "2");
            session.setAttribute("pen", 2);

            model.addAttribute("monitores", monitorDao.getMonitoresRechazados());
        }
        
        return "monitor/list";
    }
    
    @RequestMapping("/lobby")
    public String lobby(Model model) {
        return "monitor/lobby";
    }
    
    @RequestMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
    	model.addAttribute("monitor", monitorDao.getMonitor(id));
        return "monitor/perfil";
    }
    
    @RequestMapping(value="/perfil/update/{id}", method = RequestMethod.POST)
    public String processUpdatePerfil(@PathVariable String id,
                                      @ModelAttribute("monitor") Monitor monitor,
                                      BindingResult bindingResult,
                                      @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors())
            return "monitor/perfil";


        try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "fotosUsuarios/" + monitor.getId() + ".jpg");
            Files.write(path, bytes);
            monitor.setUrlImg("/fotosUsuarios/" + monitor.getId() + ".jpg");

        } catch (IOException e) {
            monitor.setUrlImg("/fotosUsuarios/default.jpg");
        }
        
        monitorDao.updateMonitor(monitor);


        return "monitor/modificarCorrecto";
    }

    @RequestMapping("/success")
    public String suscribirse(Model model) {
        return "monitor/success";
    }
    
    @RequestMapping("/modificarCorrecto")
    public String modificarCorrecto(Model model) {
        return "monitor/modificarCorrecto";
    }
    
    
    
}
