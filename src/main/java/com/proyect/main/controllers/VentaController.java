package com.proyect.main.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyect.main.models.dao.IFacturaDao;
import com.proyect.main.models.dao.IItemFacturaDao;
import com.proyect.main.models.dao.IMesaDao;
import com.proyect.main.models.dao.IProductoDao;
import com.proyect.main.models.entity.Factura;
import com.proyect.main.models.entity.Item;
import com.proyect.main.models.entity.Mesa;
import com.proyect.main.models.entity.Producto;
import com.proyect.main.models.service.IClienteService;

@Controller
public class VentaController {

	
	@Autowired 
	private IMesaDao mesaDao;
	
	@Autowired
	private IItemFacturaDao itemDao;
	
	@Autowired 
	IFacturaDao facturaDao;
	
	
	
	@GetMapping(value = {"ventas","/"})
	public String ventas(Model model) {
		
	
		System.out.println("-------------------------------------------------");
		
		model.addAttribute("mesa", mesaDao.listadoFacturas());
		
		
		model.addAttribute("factura", facturaDao.findAll());
		System.out.println("-------------------------------------------------");
		
	
		
		
	
	
	
		model.addAttribute("listadoMesa", mesaDao.findAll());
		return "ventas";
	}
	
	
	
	@PostMapping(value = "/grabarVenta")
	public String grabarVenta(@ModelAttribute("factura") Factura factura) {
		
		System.out.println(factura.getId()+" "+factura.getCliente().getId()+"  "+factura.getCliente().getNombre()+" numero de mesa es :"+factura.getMesa().getId());
		
		
		
		System.out.println(factura.getItems());
		
		facturaDao.actualizarFactura(factura.getCliente().getId(), factura.getId());
		
		Factura newfactura=new Factura();
		newfactura.setMesa(factura.getMesa());
		newfactura.setEstado(false);
		
		facturaDao.save(newfactura);
		
		return "redirect:/";
	}
	
	
	@GetMapping(value = "/factura/getOne")
	@ResponseBody
	public Optional<Factura> getOneFactura(Long id){
		return facturaDao.findById(id);
	}
	
	
	
	@PostMapping(value = "agregarProductoPedido")
	public String agregarProductoPedido(@ModelAttribute("item") Item item) {
		
		System.out.println(item.getCantidad()+"  "+item.getFactura().getId()+" "+item.getProducto().getId()+"   "+item.getProducto().getNombre());
		itemDao.save(item);
		
		
		
		return "redirect:/";
	}
	
	@GetMapping(value = "eliminarProductoPedido/{id}")
	public String eliminarProductoPedido(@PathVariable Long id) {
		
		System.out.println(id);
		itemDao.deleteById(id);
		
		
		
		return "redirect:/";
	}
	
	
	
	@GetMapping(value = "/factura/pdf")
	public String reporteFacturaFechas(@RequestParam String fecha1,@RequestParam String fecha2,Model model) {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fecha=formato.parse(fecha1);
			Date fechaTwo=formato.parse(fecha2);
			List<Factura> facturasBetweenDate=facturaDao.findBycreateAtBetween(fecha, fechaTwo);
			
			model.addAttribute("facturasBetweenDate", facturasBetweenDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "factura/pdf";
	}
	
}
