package com.proyect.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyect.main.models.dao.IFacturaDao;

import com.proyect.main.models.entity.Factura;
import com.proyect.main.models.service.IFacturaService;
import com.proyect.main.util.paginator.PageRender;

@Controller
public class FacturaController {

	@Autowired
	private IFacturaDao facturaDao;
	
	@GetMapping(value = "/facturas")
	public String facturas(@RequestParam(name = "page",defaultValue = "0") int page, Model model) {
		
		Pageable pageRequest=PageRequest.of(page, 4);
		
		Page<Factura> facturas=facturaDao.findAll(pageRequest);

		PageRender<Factura> pageRender=new PageRender<>("/facturas",facturas);
		
		
		model.addAttribute("facturas", facturas);
		model.addAttribute("page",pageRender);
		return "facturas";
	}
	
}
