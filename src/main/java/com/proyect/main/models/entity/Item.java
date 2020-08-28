package com.proyect.main.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "facturas_items")
public class Item implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id	")
	private Producto producto;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Factura factura;
	
	
	
	


	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * 
	 */
	public Double calcularImpote() {
		return cantidad.longValue()*producto.getPrecio();
	}


	
	@Override
	public String toString() {
		return "Item [id=" + id + ", cantidad=" + cantidad + ", producto=" + producto + ", factura=" + factura + "]";
	}

	private static final long serialVersionUID = 1L;
}
