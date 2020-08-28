package com.proyect.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyect.main.models.dao.IFacturaDao;
import com.proyect.main.models.dao.IMesaDao;
import com.proyect.main.models.entity.Factura;
import com.proyect.main.models.entity.Mesa;

@Controller
public class MesaController {

	@Autowired 
	private IMesaDao mesadao;
	
	@Autowired IFacturaDao facturadao;
	
	@PostMapping(value = "/mesa")
	public String mesa(@ModelAttribute Mesa mesa,Model model) {
		
		mesadao.save(mesa);
		Factura newfactura=new Factura();
		newfactura.setMesa(mesa);
		newfactura.setEstado(false);
		
		facturadao.save(newfactura);
		
		return "redirect:/ventas";
	}
	
	
}
