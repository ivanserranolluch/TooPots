package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.dao.ClienteDao;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	private ClienteDao clienteDao;
	
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao=clienteDao;
	}
	
	@RequestMapping("/list")
	public String listCliente(Model model) {
		model.addAttribute("clientes",clienteDao.getClientes());
		return "cliente/list";
	}
}
