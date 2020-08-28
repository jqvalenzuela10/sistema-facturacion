package com.proyect.main.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyect.main.models.dao.IProductoDao;
import com.proyect.main.models.entity.Cliente;
import com.proyect.main.models.entity.Producto;
import com.proyect.main.models.service.IClienteService;
import com.proyect.main.util.paginator.PageRender;

@Controller
public class ProductoController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IProductoDao productoDao;
	
	@GetMapping(value = "/productos")
	public String productos(@RequestParam(value = "page",defaultValue = "0")int page,Model model) {
		
		Pageable pageable=PageRequest.of(page, 8);
		
		Page<Producto> productos=productoDao.findAll(pageable);
		
		PageRender<Producto> pageRender=new PageRender<>("/productos",productos);
		
		model.addAttribute("productos",productoDao.findAll());
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Listado de productos");
		return "productos";
	}
	
	
	
	@GetMapping(value = "/formproductos")
	public String crearProducto(Model model) {

		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		model.addAttribute("titulo", "Formulario de Producto");
		model.addAttribute("boton","agregar Producto");
		return "formproductos";
	}
	
	
	@PostMapping(value = "/formproductos")
	public String guardarProducto(@Valid Producto producto, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Producto");
			model.addAttribute("boton","agregar Producto");
			return "formproductos";
		}
		String mensajeFlash = (producto.getId() != null) ? "Producto editado con éxito!" : "Producto creado con éxito!";
		
		productoDao.save(producto);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:productos";
	}
	
	
	@GetMapping(value = "/formproductos/{id}")
	public String editarProducto(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Optional<Producto> producto = null;

		if (id > 0) {
			producto = productoDao.findById(id);
			if (producto == null) {
				flash.addFlashAttribute("error", "El ID del Producto no existe en la BBDD!");
				return "redirect:/productos";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del Producto no puede ser cero!");
			return "redirect:/productos";
		}
		
		model.addAttribute("producto", producto);
		model.addAttribute("titulo", "Editar Producto");
		model.addAttribute("boton","editar Producto");
		return "formproductos";
	}
	
	
	@RequestMapping(value = "/eliminarProducto/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			productoDao.deleteById(id);
			flash.addFlashAttribute("success", "Producto eliminado con éxito!");
		}
		return "redirect:/productos";
	}
	
	
	@GetMapping(value = "/cargar-productos/{term}",produces = {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		System.out.println("term ::"+term);
		return clienteService.findByNombre(term);
	}
	
	
	
	
}
