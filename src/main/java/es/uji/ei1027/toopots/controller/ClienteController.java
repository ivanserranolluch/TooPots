package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.UsuariosRegistradosDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	private ClienteDao clienteDao;
	private UsuariosRegistradosDao userDao;
	
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao=clienteDao;
	}
	
	@Autowired
	public void setUsuariosRegistradosDao(UsuariosRegistradosDao userDao) {
		this.userDao=userDao;
	}
	
	@RequestMapping("/list")
	public String listCliente(Model model) {
		model.addAttribute("clientes",clienteDao.getClientes());
		return "cliente/list";
	}
	
	@RequestMapping("/add")
	public String addCliente(@ModelAttribute("cliente") Cliente cliente,
            BindingResult bindingResult, Model model){
		if (bindingResult.hasErrors()){
			model.addAttribute("rol", "None");
			model.addAttribute("monitor", new Monitor());
			System.out.println(bindingResult);
			return "common/singup";
		}
		
		User user = new User();
		user.setEmail(cliente.getEmail());
		user.setPassword(cliente.getPasswd());
		user.setTipoUsuario("cliente");
		
        clienteDao.addCliente(cliente);
        userDao.addUsuario(user);
        
		return "common/success";
	}
}
