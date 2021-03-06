package com.proyect.main.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyect.main.models.entity.Cliente;
import com.proyect.main.models.entity.Producto;
import com.proyect.main.models.service.IClienteService;
import com.proyect.main.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page",defaultValue = "0") int page, Model model) {
		
		Pageable pageRequest=PageRequest.of(page, 8);
		
		Page<Cliente> clientes=clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender=new PageRender<>("/clientes",clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes",clientes);
		model.addAttribute("page",pageRender);
		return "clientes";
	}

	@RequestMapping(value = "/formclientes")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "formclientes";
	}

	@RequestMapping(value = "/formclientes/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/clientes";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/clientes";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "formclientes";
	}

	@RequestMapping(value = "/formclientes", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "formclientes";
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";
	
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:clientes";
	}

	@RequestMapping(value = "/eliminarCliente/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		}
		return "redirect:/clientes";
	}
	
	
	
	
	@GetMapping(value = "/cargar-clientes/{term}",produces = {"application/json"})
	public @ResponseBody List<Cliente> cargarProductos(@PathVariable String term){
		System.out.println("term ::"+term);
		return clienteService.findByNombreCliente(term);
	}
	
	
	
}
