package es.uji.ei1027.toopots.controller;

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
                                      @RequestParam(value="estado", required=true) String action) {
        if (bindingResult.hasErrors())
            return "monitor/update";

        if (action.equals("aceptada"))
            monitor.setEstado("aceptada");
        else if (action.equals("rechazada"))
            monitor.setEstado("rechazada");

        monitorDao.updateMonitor(monitor);
        
        if (monitor.getEstado().equals("aceptada")) {
       	mailService.sendMail("al342376@uji.es", monitor.getEmail(), "Aceptado como Monitor", "Su solicitud como monitor, ha sido aceptada.");
        	
		}
        return "redirect:../list";
    }


    @RequestMapping(value="/delete/{id}")
    public String deleteMonitor(Model model, @PathVariable String id) {
        monitorDao.deleteMonitor(id);
        return "redirect:../list";
    }

    @RequestMapping("/list")
    public String listMonitores(Model model, @RequestParam("pen") Optional<Integer> pen, @RequestParam("busca") Optional<String> busca) {

    	String buscar = busca.orElse("None");
    	
    	if (!buscar.isEmpty()) {
    		model.addAttribute("busca", buscar);
    	} else {
    		//No se como NO COLOREAR si se pone vacio
    		model.addAttribute("busca", "|");
    	}
    	
        if(pen.orElse(0) == 0) {
            model.addAttribute("monitores", monitorDao.getMonitoresRegistrados());
            model.addAttribute("pen", "1");
            
        }else if(pen.orElse(1) == 1) {
            model.addAttribute("monitores", monitorDao.getMonitoresPendientes());
            model.addAttribute("pen", "1");
            
        }else if(pen.orElse(2) == 2) {
        	model.addAttribute("pen", "2");
            model.addAttribute("monitores", monitorDao.getMonitoresRechazados());
        }
        
        return "monitor/list";
    }
    
    @RequestMapping("/lobby")
    public String lobby(Model model) {
        return "monitor/lobby";
    }
    
    @RequestMapping("/perfil")
    public String perfil(Model model) {
        return "monitor/perfil";
    }
    
    @RequestMapping("/success")
    public String suscribirse(Model model) {
        return "monitor/success";
    }
    
    
    
}
