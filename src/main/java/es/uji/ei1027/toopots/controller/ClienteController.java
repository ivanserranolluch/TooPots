package es.uji.ei1027.toopots.controller;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.UsuariosRegistradosDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.User;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	private ClienteDao clienteDao;
	private ReservaDao reservasDao;
	private UsuariosRegistradosDao userDao;
	private BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
	
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao=clienteDao;
	}
	@Autowired
	public void setReservasDao(ReservaDao reservasDao) {
		this.reservasDao=reservasDao;
	}
	
	@Autowired
	public void setUsuariosRegistradosDao(UsuariosRegistradosDao userDao) {
		this.userDao=userDao;
	}
	
	@RequestMapping(value="/{email}", method=RequestMethod.GET)
	public String getActividad(Model model, @PathVariable String email){
		model.addAttribute("cliente", clienteDao.getCliente(email));
		return "cliente/details";
	}
	
	@RequestMapping("/list")
	public String listCliente(Model model) {
		model.addAttribute("clientes",clienteDao.getClientes());
		return "cliente/list";
	}
	
	@RequestMapping(value="/reservas/{email}", method=RequestMethod.GET)
	public String listReservas(Model model,@PathVariable String email) {
		model.addAttribute("reservas",reservasDao.getReservasEmail(email));
		return "cliente/reservas";
	}
	
	@RequestMapping(value="/updateReserva/{id}", method=RequestMethod.GET)
    public String updateReservaCliente(Model model, @PathVariable String id) {
		System.out.println(id);
        model.addAttribute("reserva", reservasDao.getReserva(id));
        return "cliente/updateReserva";
    }
	  @RequestMapping(value="/updateReserva/{id}", method = RequestMethod.POST)
	    public String processUpdateSubmitReserva(@PathVariable String id,
	                                      @ModelAttribute("reserva") Reserva reserva,
	                                      BindingResult bindingResult) {
	        //if (bindingResult.hasErrors())
	          //  return "cliente/update"

	        reservasDao.updateReservaCliente(reserva);
	       
	        return "cliente/lobby";
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
		user.setPassword(passwordEncryptor.encryptPassword(cliente.getPasswd()));
		user.setTipoUsuario("cliente");
		
        clienteDao.addCliente(cliente);
        userDao.addUsuario(user);
        
		return "common/success";
	}
	
	@RequestMapping(value="/update/{email}", method=RequestMethod.GET)
    public String updateCliente(Model model, @PathVariable String email) {
		System.out.println(email);
        model.addAttribute("cliente", clienteDao.getCliente(email));
        return "cliente/update";
    }
	

    @RequestMapping(value="/update/{dni}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String dni,
                                      @ModelAttribute("cliente") Cliente cliente,
                                      BindingResult bindingResult) {
        //if (bindingResult.hasErrors())
          //  return "cliente/update"

        clienteDao.updateCliente(cliente);
       
        return "cliente/confirmacionCambioDatos";
    }
}
