package es.uji.ei1027.toopots.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.dao.DescuentoDao;
import es.uji.ei1027.toopots.model.Descuento;

@Controller
@RequestMapping("/descuento")
public class DescuentoController {
	
	private static DescuentoDao descuentoDao;
	
	@Autowired
	public void setDescuentoDao(DescuentoDao descuentoDao){
		DescuentoController.descuentoDao = descuentoDao;
	}
	
	public static List<Descuento> getDiscounts(){
		return descuentoDao.getClientes();
	}
	
	@RequestMapping(value="/all")
	public String getAllDescuentos(Model model){
		model.addAttribute("descuentos", getDiscounts());
		return "descuento/list";
	}
	
	@RequestMapping(value="/add")
	public String addDescuento(){
		Descuento d = new Descuento();
		d.setDiscountValue(0.5f);
		d.setField("Sexo");
		d.setValue("M");
		descuentoDao.addDescuento(d);
		return "common/succes";
	}

}
