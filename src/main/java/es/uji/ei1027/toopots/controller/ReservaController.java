package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.User;
import es.uji.ei1027.toopots.service.Payment;
import es.uji.ei1027.toopots.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Reserva;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private ReservaDao reservaDao;
    private ClienteDao clienteDao;

    @Autowired
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    @Autowired
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @RequestMapping(value = "/add")
    public String addReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }


    @RequestMapping(value = "/pasarelapago", method=RequestMethod.GET)
    public String procesoPago(Model model,
            @RequestParam("numAsistentes") int numAsis,
            @RequestParam("precioPersona") double precio,
            @RequestParam("id_actividad") int id){

        Reserva reserva = new Reserva();
        reserva.setId_actividad(id);
        reserva.setPrecioPersona(precio);
        reserva.setNumAsistentes(numAsis);
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("aux", reserva);
        return "/reserva/pasarelapago";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva, HttpSession session,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/add";

        reserva.setEstadoPago("pendiente");
        reserva.setFecha(java.sql.Date.valueOf(LocalDate.now()));
        reserva.setPreciototal(reserva.getPrecioPersona() * reserva.getNumAsistentes());
        User user = (User) session.getAttribute("user");
        Cliente cliente = clienteDao.getClienteEmail(user.getEmail());
        reserva.setDni(cliente.getDni());

        System.out.println(".......................");
        System.out.println("Enviando datos a la PPS...");
        System.out.println(" ");
        PaymentService service = new Payment();
        reserva.setNumTransacciones(service.getConfirmacion());
        System.out.println("Datos Correctos, guardando numero transaccion");
        reserva.setEstadoPago("confirmada");

        reservaDao.addReserva(reserva);
        return "redirect:/reservaClienteActividad/listCliente";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateReserva(Model model, @PathVariable String id) {
        model.addAttribute("reserva", reservaDao.getReserva(id));
        return "reserva/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("reserva") Reserva reserva, BindingResult bindingResult,
                                      @RequestParam(value = "action", defaultValue = "vacio") String action) {
        if (bindingResult.hasErrors())
            return "reserva/update";

        if (action.equals("aceptar")) {
            reservaDao.updateEstado(Integer.valueOf(id), "aceptada");
            //enviar mail aceptacion
            return "redirect:../../reservaClienteActividad/list?mon=true";
        }else {
            reservaDao.updateReserva(reserva);
        }

        return "redirect:../list";
    }


    @RequestMapping(value = "/delete/{id}")
    public String deleteReserva(Model
                                        model, @PathVariable String id) {
        reservaDao.deleteReserva(id);
        return "redirect:../list";
    }

    @RequestMapping("/list")
    public String listReservas(Model model) {
        model.addAttribute("reservas", reservaDao.getReservas());
        return "reserva/list";
    }


}