package com.proyect.main.models.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.proyect.main.models.entity.Factura;

public interface IFacturaDao extends PagingAndSortingRepository<Factura, Long>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Factura f set  f.estado=1, f.cliente.id=?1 where f.id=?2 ")
	public void actualizarFactura(Long idCliente ,Long idFactura );
	
	
	
	
	@Query("select f from Factura f where f.estado=1")
	Page<Factura> findAll(Pageable pageable);
	
	
	
	@Query("select f from Factura f where f.estado=1 and f.createAt between ?1 and ?2")
	List<Factura> findBycreateAtBetween(Date f1, Date f2);
	
	
}
