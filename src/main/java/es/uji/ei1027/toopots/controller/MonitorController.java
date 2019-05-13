package es.uji.ei1027.toopots.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.service.MailService;

@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private MonitorDao monitorDao;
    private MailService mailService;
    
    @Value("${upload.file.directory}")
    private String uploadDirectory;


    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao=monitorDao;
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
    								@RequestParam("file") MultipartFile file,
    								BindingResult bindingResult) {
    	
    	
        if (bindingResult.hasErrors())
            return "monitor/add";
        
        System.out.println(uploadDirectory);
        try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "pdfs/" 
                                          + monitor.getId());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        monitorDao.addMonitor(monitor);
        return "redirect:list";
    }


    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public String updateMonitor(Model model, @PathVariable String id) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
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


    @RequestMapping(value="/delete/{id}")
    public String deleteMonitor(Model model, @PathVariable String id) {
        monitorDao.deleteMonitor(id);
        return "redirect:../list";
    }

    @RequestMapping("/list")
    public String listMonitores(Model model) {
        model.addAttribute("monitores", monitorDao.getMonitores());
        return "monitor/list";
    }
    
    @RequestMapping("/listSolicitudes")
    public String listSolicitudes(Model model) {
        model.addAttribute("monitores", monitorDao.getMonitores());
        return "monitor/list";
    }
    
    @RequestMapping("/lobby")
    public String lobby(Model model) {
        return "monitor/lobby";
    }
    
    
    
}
