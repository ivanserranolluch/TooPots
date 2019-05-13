package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.service.MailService;

@Controller
@RequestMapping("/mailServer")
public class SendMailController {

    @Autowired
    private MailService mailService;
    
    @GetMapping("/")
    public String index(){
        return "send_mail_view";
    }

    @PostMapping("/sendMail")
    public void sendMail(String name,  String mail, String subject,  String body){

        //String message = body +"\n\n Datos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
        mailService.sendMail(name,mail,subject,body);
    }
}