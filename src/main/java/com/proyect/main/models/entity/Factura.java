package com.proyect.main.models.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	private Boolean estado;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Mesa mesa;
	
	
	@OneToMany(mappedBy = "factura",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Item> items;
	
	
	/*@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	private List<Item> items;
	*/
	
	
	

	public List<Item> getItems() {
		return items;
	}





	public void setItems(List<Item> items) {
		this.items = items;
	}





	@PrePersist
	public void prePersist() {
		createAt=new Date();
	}
	
	
	
	
	
	public Factura() {
		this.items=new ArrayList<Item>();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
	public void addItemFactura(Item item) {
		this.items.add(item);
	}
	
	public Double getTotal() {
		Double total=0.0;
		
		for (Item itemFactura : items) {
			total+=itemFactura.calcularImpote();
			System.out.println(itemFactura.calcularImpote()+" "+itemFactura.getProducto().getNombre()+" precio: "+itemFactura.getProducto().getPrecio());
			System.out.println("------------------------------");
		}
		System.out.println(total);
		total=(double) Math.round(total * 100);
		total=total/100;
		System.out.println(total);
		return total;
	}
	private static final long serialVersionUID = 1L;
}
